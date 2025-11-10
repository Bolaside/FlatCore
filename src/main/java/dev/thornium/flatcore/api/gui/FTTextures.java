package dev.thornium.flatcore.api.gui;

import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;

public class FTTextures {
  public static final ResourceTexture PROGRESS_BAR_STONE_OREIFIER = texture("gui/progress_bar/progress_bar_stone_oreifier");

  private static ResourceTexture texture(String path) {
    return new ResourceTexture(String.format("flatcore:textures/%s.png", path));
  }
}
