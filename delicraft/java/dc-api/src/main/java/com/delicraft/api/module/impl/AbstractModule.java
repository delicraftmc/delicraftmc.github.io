package com.delicraft.api.module.impl;

import com.delicraft.api.module.*;

public abstract class AbstractModule implements LifecycleModule {
  private final String id;
  private final String name;
  private final String version;
  private final ModuleCategory category;

  protected AbstractModule(String id, String name, String version, ModuleCategory category) {
    this.id = id;
    this.name = name;
    this.version = version;
    this.category = category;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public String version() {
    return version;
  }

  @Override
  public ModuleCategory category() {
    return category;
  }

  @Override
  public void onLoad() {}

  @Override
  public void onEnable() {}

  @Override
  public void onDisable() {}

  @Override
  public void onReload() {}
}
