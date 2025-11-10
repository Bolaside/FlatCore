package dev.thornium.flatcore.gtbridge;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;

import dev.thornium.flatcore.api.gui.FTTextures;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.MULTIBLOCK;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.register;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;

public class FTRecipeTypes {
  public final static GTRecipeType GREENHOUSE_RECIPES = register("greenhouse", MULTIBLOCK)
    .setMaxIOSize(3, 6, 1, 0)
    .setEUIO(IO.IN)
    .setSlotOverlay(false, false, GuiTextures.ARROW_INPUT_OVERLAY)
    .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
    .setSound(GTSoundEntries.TURBINE);

  public final static GTRecipeType STONE_OREIFIER_RECIPES = register("stone_oreifier", MULTIBLOCK)
    .setMaxIOSize(2, 6, 1, 0)
    .setEUIO(IO.IN)
    .setSlotOverlay(false, false, GuiTextures.ARROW_INPUT_OVERLAY)
    .setProgressBar(FTTextures.PROGRESS_BAR_STONE_OREIFIER, LEFT_TO_RIGHT)
    .setSound(GTSoundEntries.TURBINE);

  public static void init() {}
}
