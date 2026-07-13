package com.delicraft.api.event.impl;

import com.delicraft.api.event.Event;
import com.delicraft.api.event.EventBus;
import com.delicraft.api.event.EventHandler;
import com.delicraft.api.event.EventPriority;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBusImpl implements EventBus {
  private final Map<Class<?>, List<HandlerEntry>> handlers = new ConcurrentHashMap<>();
  private final Map<Class<?>, Event> stickyEvents = new ConcurrentHashMap<>();

  @Override
  public <T> void register(Class<T> eventType, EventHandler<T> handler, EventPriority priority) {
    handlers
        .computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
        .add(new HandlerEntry<>(handler, priority));
    handlers.get(eventType).sort(Comparator.comparingInt(e -> e.priority.ordinal()));

    Event sticky = stickyEvents.get(eventType);
    if (sticky != null) {
      handler.handle(eventType.cast(sticky));
    }
  }

  @Override
  public <T> void unregister(Class<T> eventType, EventHandler<T> handler) {
    List<HandlerEntry> entries = handlers.get(eventType);
    if (entries != null) {
      entries.removeIf(e -> e.handler.equals(handler));
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> void fire(T event) {
    List<HandlerEntry> entries = handlers.get(event.getClass());
    if (entries == null) return;

    for (HandlerEntry entry : entries) {
      if (event instanceof Event cancellable
          && cancellable.isCancelled()
          && entry.priority != EventPriority.MONITOR) continue;
      ((EventHandler<T>) entry.handler).handle(event);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> void fireAsync(T event) {
    CompletableFuture.runAsync(() -> fire(event));
  }

  @Override
  public <T> void fireSticky(T event) {
    stickyEvents.put(event.getClass(), (Event) event);
    fire(event);
  }

  @Override
  public boolean isCancelled(Event event) {
    return event.isCancelled();
  }

  private record HandlerEntry<T>(EventHandler<T> handler, EventPriority priority) {}
}
