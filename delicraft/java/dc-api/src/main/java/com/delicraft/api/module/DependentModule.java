package com.delicraft.api.module;

import java.util.Set;

public interface DependentModule extends DliModule {
  Set<String> requiredModules();

  Set<String> optionalModules();
}
