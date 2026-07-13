package com.delicraft.api.module.impl;

import com.delicraft.api.module.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ModuleManagerImpl implements ModuleManager {
  private final Map<String, DliModule> modules = new ConcurrentHashMap<>();
  private final Map<String, ModuleState> states = new ConcurrentHashMap<>();
  private final Map<String, Set<String>> adjacency = new HashMap<>();

  public enum ModuleState {
    REGISTERED,
    LOADING,
    LOADED,
    ENABLING,
    ENABLED,
    DISABLING,
    DISABLED,
    ERROR
  }

  @Override
  public void register(DliModule module) {
    String id = module.id();
    if (modules.containsKey(id)) {
      throw new IllegalArgumentException("Module already registered: " + id);
    }
    modules.put(id, module);
    states.put(id, ModuleState.REGISTERED);
    if (module instanceof DependentModule dep) {
      dep.requiredModules()
          .forEach(req -> adjacency.computeIfAbsent(id, k -> new HashSet<>()).add(req));
    }
  }

  @Override
  public void unregister(String moduleId) {
    modules.remove(moduleId);
    states.remove(moduleId);
    adjacency.remove(moduleId);
  }

  @Override
  public boolean isRegistered(String moduleId) {
    return modules.containsKey(moduleId);
  }

  @Override
  public void enableModule(String moduleId) {
    DliModule module = modules.get(moduleId);
    if (module == null) throw new IllegalArgumentException("Unknown module: " + moduleId);

    if (!resolveDependencies(moduleId)) {
      states.put(moduleId, ModuleState.ERROR);
      return;
    }

    states.put(moduleId, ModuleState.ENABLING);
    if (module instanceof LifecycleModule lifecycle) {
      try {
        lifecycle.onEnable();
      } catch (Exception e) {
        states.put(moduleId, ModuleState.ERROR);
        return;
      }
    }
    states.put(moduleId, ModuleState.ENABLED);
  }

  @Override
  public void disableModule(String moduleId) {
    DliModule module = modules.get(moduleId);
    if (module == null) return;

    states.put(moduleId, ModuleState.DISABLING);
    if (module instanceof LifecycleModule lifecycle) {
      try {
        lifecycle.onDisable();
      } catch (Exception ignored) {
      }
    }
    states.put(moduleId, ModuleState.DISABLED);
  }

  @Override
  public void reloadModule(String moduleId) {
    DliModule module = modules.get(moduleId);
    if (module == null) return;

    if (module instanceof LifecycleModule lifecycle) {
      lifecycle.onReload();
    }
  }

  @Override
  public void enableAll() {
    List<String> sorted = topologicalSort();
    if (sorted == null) {
      throw new IllegalStateException("Circular dependency detected in modules");
    }
    sorted.forEach(this::enableModule);
  }

  @Override
  public void disableAll() {
    modules.keySet().forEach(this::disableModule);
  }

  @Override
  public void reloadAll() {
    modules
        .values()
        .forEach(
            m -> {
              if (m instanceof LifecycleModule lifecycle) {
                lifecycle.onReload();
              }
            });
  }

  @Override
  public Optional<DliModule> getModule(String moduleId) {
    return Optional.ofNullable(modules.get(moduleId));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DliModule> Optional<T> getModule(Class<T> type) {
    return modules.values().stream().filter(type::isInstance).map(m -> (T) m).findFirst();
  }

  @Override
  public Collection<DliModule> getModules(ModuleCategory category) {
    return modules.values().stream()
        .filter(m -> m.category() == category)
        .collect(Collectors.toList());
  }

  @Override
  public Collection<DliModule> getAllModules() {
    return List.copyOf(modules.values());
  }

  @Override
  public Collection<DliModule> getActiveModules() {
    return modules.entrySet().stream()
        .filter(e -> states.get(e.getKey()) == ModuleState.ENABLED)
        .map(Map.Entry::getValue)
        .collect(Collectors.toList());
  }

  @Override
  public boolean resolveDependencies(String moduleId) {
    DliModule module = modules.get(moduleId);
    if (!(module instanceof DependentModule dep)) return true;

    for (String required : dep.requiredModules()) {
      if (!modules.containsKey(required)) return false;
    }
    return true;
  }

  private List<String> topologicalSort() {
    Map<String, Integer> visited = new HashMap<>();
    LinkedList<String> order = new LinkedList<>();

    for (String id : modules.keySet()) {
      if (dfs(id, visited, order)) return null;
    }
    return order;
  }

  private boolean dfs(String id, Map<String, Integer> visited, LinkedList<String> order) {
    if (visited.containsKey(id)) {
      return visited.get(id) == 1;
    }
    visited.put(id, 1);
    Set<String> deps = adjacency.get(id);
    if (deps != null) {
      for (String dep : deps) {
        if (dfs(dep, visited, order)) return true;
      }
    }
    visited.put(id, 2);
    order.addFirst(id);
    return false;
  }
}
