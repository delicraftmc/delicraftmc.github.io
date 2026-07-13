package com.delicraft.client.module.hud;

import com.delicraft.api.module.ModuleCategory;
import com.delicraft.api.module.RenderableModule;
import com.delicraft.api.module.impl.AbstractModule;
import com.delicraft.api.runtime.RenderContext;
import com.delicraft.api.runtime.RenderPriority;

public class FpsModule extends AbstractModule implements RenderableModule {
  private int fps;
  private boolean enabled = true;

  public FpsModule() {
    super("hud.fps", "FPS Display", "1.0.0", ModuleCategory.HUD);
  }

  public void updateFps(int fps) {
    this.fps = fps;
  }

  @Override
  public RenderPriority renderPriority() {
    return RenderPriority.HIGH;
  }

  @Override
  public void render(RenderContext ctx) {
    if (!enabled) return;
    ctx.drawText(2, 2, "FPS: " + fps, 0.8f);
  }

  @Override
  public void onEnable() {
    this.enabled = true;
  }

  @Override
  public void onDisable() {
    this.enabled = false;
  }
}
