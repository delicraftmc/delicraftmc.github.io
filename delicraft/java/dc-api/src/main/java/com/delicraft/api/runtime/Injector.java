package com.delicraft.api.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

public interface Injector {
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @interface Inject {
    String name() default "";
  }

  <T> void bind(Class<T> type, T instance);

  <T> void bindNamed(String name, Object instance);

  <T> T get(Class<T> type);

  <T> Optional<T> find(Class<T> type);

  void injectMembers(Object target);

  <T> T create(Class<T> type);

  void initialize();

  void shutdown();
}
