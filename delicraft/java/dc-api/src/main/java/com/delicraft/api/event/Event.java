package com.delicraft.api.event;

public interface Event {
  boolean isCancelled();

  void setCancelled(boolean cancelled);
}
