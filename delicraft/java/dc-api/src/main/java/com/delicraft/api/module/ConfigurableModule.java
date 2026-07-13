package com.delicraft.api.module;

public interface ConfigurableModule extends DliModule {
  Settings<?> settings();

  void applySettings(Settings<?> settings);
}
