package com.delicraft.api.event;

public interface EventBus {
  <T> void register(Class<T> eventType, EventHandler<T> handler, EventPriority priority);

  <T> void unregister(Class<T> eventType, EventHandler<T> handler);

  <T> void fire(T event);

  <T> void fireAsync(T event);

  <T> void fireSticky(T event);

  boolean isCancelled(Event event);
}
