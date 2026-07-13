package com.delicraft.server.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.nio.file.Path;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseManager implements AutoCloseable {
  private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
  private final HikariDataSource dataSource;

  public DatabaseManager(Path dataDirectory) {
    Path dbFile = dataDirectory.resolve("delicraft.db");
    dataDirectory.toFile().mkdirs();

    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:sqlite:" + dbFile.toAbsolutePath());
    config.setMaximumPoolSize(4);
    config.setMinimumIdle(1);
    config.setConnectionTimeout(5000);
    config.setPoolName("DeliCraft-Pool");

    this.dataSource = new HikariDataSource(config);

    Flyway flyway =
        Flyway.configure().dataSource(dataSource).locations("classpath:db/migration").load();
    flyway.migrate();

    log.info("Database initialized: {}", dbFile.toAbsolutePath());
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  @Override
  public void close() {
    if (dataSource != null && !dataSource.isClosed()) {
      dataSource.close();
      log.info("Database connection pool closed.");
    }
  }
}
