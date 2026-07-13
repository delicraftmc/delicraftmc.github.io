package com.delicraft.client.di;

import com.delicraft.api.event.EventBus;
import com.delicraft.api.event.impl.EventBusImpl;
import com.delicraft.api.module.ModuleManager;
import com.delicraft.api.module.impl.ModuleManagerImpl;
import com.delicraft.api.runtime.Injector;
import com.delicraft.api.runtime.impl.InjectorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientInjector {
  private static final Logger log = LoggerFactory.getLogger(ClientInjector.class);
  private final Injector injector;

  public ClientInjector() {
    this.injector = new InjectorImpl();

    injector.bind(EventBus.class, new EventBusImpl());
    injector.bind(ModuleManager.class, new ModuleManagerImpl());
    injector.bind(Injector.class, injector);

    log.info("Client services registered.");
  }

  public Injector injector() {
    return injector;
  }

  public void shutdown() {
    injector.shutdown();
  }
}
