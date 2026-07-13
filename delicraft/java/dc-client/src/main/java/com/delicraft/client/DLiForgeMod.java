package com.delicraft.client;

import com.delicraft.api.runtime.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DLiForgeMod {
  private static final Logger log = LoggerFactory.getLogger(DLiForgeMod.class);
  private final Injector injector;
  private boolean enabled;

  public DLiForgeMod(Injector injector) {
    this.injector = injector;
  }

  public void onLoad() {
    log.info("DeliCraft Client loading...");
  }

  public void onEnable() {
    this.enabled = true;
    injector.initialize();
    log.info("DeliCraft Client enabled.");
  }

  public void onDisable() {
    this.enabled = false;
    injector.shutdown();
    log.info("DeliCraft Client disabled.");
  }

  public boolean isEnabled() {
    return enabled;
  }

  public Injector injector() {
    return injector;
  }
}
