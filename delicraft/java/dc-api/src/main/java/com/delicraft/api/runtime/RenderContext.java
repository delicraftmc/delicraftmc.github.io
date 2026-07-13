package com.delicraft.api.runtime;

public interface RenderContext {
  int screenWidth();

  int screenHeight();

  float partialTick();

  void drawText(int x, int y, String text, float scale);

  void drawText(int x, int y, String text, float scale, TextAlign align);

  void drawRect(int x, int y, int width, int height, int color);

  void drawTexture(int x, int y, int width, int height, String path);

  void setColor(String hexColor);

  enum TextAlign {
    LEFT,
    CENTER,
    RIGHT
  }
}
