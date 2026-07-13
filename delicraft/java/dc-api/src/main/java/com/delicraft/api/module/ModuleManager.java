package com.delicraft.api.module;

import java.util.Collection;
import java.util.Optional;

public interface ModuleManager {
  void register(DliModule module);

  void unregister(String moduleId);

  boolean isRegistered(String moduleId);

  void enableModule(String moduleId);

  void disableModule(String moduleId);

  void reloadModule(String moduleId);

  void enableAll();

  void disableAll();

  void reloadAll();

  Optional<DliModule> getModule(String moduleId);

  <T extends DliModule> Optional<T> getModule(Class<T> type);

  Collection<DliModule> getModules(ModuleCategory category);

  Collection<DliModule> getAllModules();

  Collection<DliModule> getActiveModules();

  boolean resolveDependencies(String moduleId);
}
