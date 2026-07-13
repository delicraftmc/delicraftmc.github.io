package com.delicraft.client;

import com.delicraft.api.module.ModuleManager;
import com.delicraft.api.runtime.Injector;
import com.delicraft.client.di.ClientInjector;
import com.delicraft.client.module.hud.FpsModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DLiClient {
  private static final Logger log = LoggerFactory.getLogger(DLiClient.class);
  private final ClientInjector clientInjector;
  private final Injector injector;
  private boolean enabled;

  public DLiClient() {
    this.clientInjector = new ClientInjector();
    this.injector = clientInjector.injector();
  }

  public void onLoad() {
    log.info("DeliCraft Client loading...");
    injector.get(ModuleManager.class).register(new FpsModule());
  }

  public void onEnable() {
    this.enabled = true;
    injector.initialize();
    injector.get(ModuleManager.class).enableAll();
    log.info("DeliCraft Client enabled.");
  }

  public void onDisable() {
    this.enabled = false;
    injector.get(ModuleManager.class).disableAll();
    injector.shutdown();
    clientInjector.shutdown();
    log.info("DeliCraft Client disabled.");
  }

  public boolean isEnabled() {
    return enabled;
  }

  public Injector injector() {
    return injector;
  }
}
