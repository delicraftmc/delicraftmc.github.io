package com.delicraft.api.event;

@FunctionalInterface
public interface EventHandler<T> {
  void handle(T event);
}
