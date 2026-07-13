package com.delicraft.api.module;

public interface LifecycleModule extends DliModule {
  void onLoad();

  void onEnable();

  void onDisable();

  void onReload();
}
