package com.delicraft.server.di;

import com.delicraft.api.event.EventBus;
import com.delicraft.api.event.impl.EventBusImpl;
import com.delicraft.api.module.ModuleManager;
import com.delicraft.api.module.impl.ModuleManagerImpl;
import com.delicraft.api.runtime.Injector;
import com.delicraft.api.runtime.impl.InjectorImpl;
import com.delicraft.server.database.DatabaseManager;
import java.nio.file.Path;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInjector {
  private static final Logger log = LoggerFactory.getLogger(ServerInjector.class);
  private final Injector injector;

  public ServerInjector(Path dataDirectory) {
    this.injector = new InjectorImpl();

    DatabaseManager dbManager = new DatabaseManager(dataDirectory);
    injector.bind(DatabaseManager.class, dbManager);
    injector.bind(DataSource.class, dbManager.getDataSource());
    injector.bind(EventBus.class, new EventBusImpl());
    injector.bind(ModuleManager.class, new ModuleManagerImpl());
    injector.bind(Injector.class, injector);

    log.info("Server services registered.");
  }

  public Injector injector() {
    return injector;
  }

  public void shutdown() {
    DatabaseManager db = injector.find(DatabaseManager.class).orElse(null);
    if (db != null) db.close();
  }
}
