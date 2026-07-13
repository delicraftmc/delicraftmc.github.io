package com.delicraft.api.runtime.impl;

import com.delicraft.api.runtime.Injector;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InjectorImpl implements Injector {
  private final Map<Class<?>, Object> services = new ConcurrentHashMap<>();
  private final Map<String, Object> namedServices = new ConcurrentHashMap<>();
  private boolean initialized;

  @Override
  public <T> void bind(Class<T> type, T instance) {
    Objects.requireNonNull(type, "type must not be null");
    Objects.requireNonNull(instance, "instance must not be null");
    if (services.containsKey(type)) {
      throw new IllegalStateException("Service already registered: " + type.getName());
    }
    services.put(type, instance);
  }

  @Override
  public <T> void bindNamed(String name, Object instance) {
    Objects.requireNonNull(name, "name must not be null");
    namedServices.put(name, instance);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T get(Class<T> type) {
    T instance = (T) services.get(type);
    if (instance == null) {
      throw new IllegalArgumentException("No service registered for: " + type.getName());
    }
    return instance;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> Optional<T> find(Class<T> type) {
    return Optional.ofNullable((T) services.get(type));
  }

  @Override
  public void injectMembers(Object target) {
    for (Field field : getAllFields(target.getClass())) {
      Inject annotation = field.getAnnotation(Inject.class);
      if (annotation == null) continue;

      Object dependency;
      String name = annotation.name();
      if (!name.isEmpty()) {
        dependency = namedServices.get(name);
        if (dependency == null) {
          throw new IllegalStateException(
              "No named service '"
                  + name
                  + "' for field: "
                  + field.getName()
                  + " in "
                  + target.getClass().getName());
        }
      } else {
        if (services.containsKey(field.getType())) {
          dependency = services.get(field.getType());
        } else {
          throw new IllegalStateException(
              "No service for field: "
                  + field.getName()
                  + " of type "
                  + field.getType().getName()
                  + " in "
                  + target.getClass().getName());
        }
      }

      field.setAccessible(true);
      try {
        field.set(target, dependency);
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Failed to inject field: " + field.getName(), e);
      }
    }
  }

  @Override
  public <T> T create(Class<T> type) {
    try {
      T instance = type.getDeclaredConstructor().newInstance();
      injectMembers(instance);
      return instance;
    } catch (Exception e) {
      throw new RuntimeException("Failed to create instance of: " + type.getName(), e);
    }
  }

  @Override
  public void initialize() {
    if (initialized) return;
    services
        .values()
        .forEach(
            instance -> {
              if (instance instanceof Initializable) {
                ((Initializable) instance).initialize();
              }
            });
    initialized = true;
  }

  @Override
  public void shutdown() {
    if (!initialized) return;
    services
        .values()
        .forEach(
            instance -> {
              if (instance instanceof Disposable) {
                ((Disposable) instance).dispose();
              }
            });
    services.clear();
    namedServices.clear();
    initialized = false;
  }

  private List<Field> getAllFields(Class<?> type) {
    List<Field> fields = new ArrayList<>();
    Class<?> current = type;
    while (current != null && current != Object.class) {
      fields.addAll(Arrays.asList(current.getDeclaredFields()));
      current = current.getSuperclass();
    }
    return fields;
  }

  public interface Initializable {
    void initialize();
  }

  public interface Disposable {
    void dispose();
  }
}
