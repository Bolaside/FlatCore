package dev.thornium.flatcore.api.gui

import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture

object FTTextures {
    val PROGRESS_BAR_STONE_OREIFIER = texture("gui/progress_bar/progress_bar_stone_oreifier")

    private fun texture(path: String) = ResourceTexture("flatcore:textures/$path.png")
}
