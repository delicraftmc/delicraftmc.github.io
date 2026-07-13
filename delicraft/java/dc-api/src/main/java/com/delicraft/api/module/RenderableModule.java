package com.delicraft.api.module;

import com.delicraft.api.runtime.RenderContext;
import com.delicraft.api.runtime.RenderPriority;

public interface RenderableModule extends LifecycleModule {
  RenderPriority renderPriority();

  void render(RenderContext ctx);
}
