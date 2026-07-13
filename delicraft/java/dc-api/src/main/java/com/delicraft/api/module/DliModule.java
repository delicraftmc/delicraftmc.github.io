package com.delicraft.api.module;

public interface DliModule {
  String id();

  String name();

  String version();

  ModuleCategory category();
}
