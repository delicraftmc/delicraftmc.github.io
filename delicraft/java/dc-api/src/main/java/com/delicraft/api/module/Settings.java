package com.delicraft.api.module;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Settings<T extends Record> {
  private final Class<T> type;
  private T values;
  private final Map<String, Object> defaults = new HashMap<>();

  public Settings(Class<T> type) {
    this.type = type;
  }

  public void setDefaults(Map<String, Object> defaults) {
    this.defaults.putAll(defaults);
  }

  @SuppressWarnings("unchecked")
  public <V> V get(String key, Class<V> valueType) {
    if (values != null) {
      try {
        var field = type.getDeclaredField(key);
        field.setAccessible(true);
        return valueType.cast(field.get(values));
      } catch (Exception ignored) {
      }
    }
    return valueType.cast(defaults.get(key));
  }

  public void set(T values) {
    this.values = values;
  }

  public void reset() {
    this.values = null;
  }

  public void load(Path file) {}

  public void save(Path file) {}
}
