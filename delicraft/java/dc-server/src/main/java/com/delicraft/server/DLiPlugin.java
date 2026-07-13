package com.delicraft.server;

import com.delicraft.api.module.ModuleManager;
import com.delicraft.api.runtime.Injector;
import com.delicraft.server.di.ServerInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DLiPlugin {
    private static final Logger log = LoggerFactory.getLogger(DLiPlugin.class);
    private final ServerInjector serverInjector;
    private final Injector injector;
    private boolean enabled;

    public DLiPlugin(ServerInjector serverInjector) {
        this.serverInjector = serverInjector;
        this.injector = serverInjector.injector();
    }

    public void onLoad() {
        log.info("DeliCraft Server loading...");
    }

    public void onEnable() {
        this.enabled = true;
        injector.initialize();
        injector.get(ModuleManager.class).enableAll();
        log.info("DeliCraft Server enabled.");
    }

    public void onDisable() {
        this.enabled = false;
        injector.get(ModuleManager.class).disableAll();
        injector.shutdown();
        serverInjector.shutdown();
        log.info("DeliCraft Server disabled.");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Injector injector() {
        return injector;
    }
}
