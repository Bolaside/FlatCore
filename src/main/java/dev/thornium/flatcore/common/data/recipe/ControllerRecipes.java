package dev.thornium.flatcore.common.data.recipe;

import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.data.recipe.GTCraftingComponents.*;
import static dev.thornium.flatcore.registry.machine.FTMachines.GREENHOUSE;
import static dev.thornium.flatcore.registry.machine.FTMachines.STONE_OREIFIER;

public class ControllerRecipes {
  private ControllerRecipes() {}

  public static void init(Consumer<FinishedRecipe> provider) {
    VanillaRecipeHelper.addShapedRecipe(
      provider, "greenhouse", GREENHOUSE.asStack(),
      "FCR", "THT", "tCt",
      'F', FLUID_REGULATOR_LV,
      'C', CIRCUIT.get(MV),
      'R', ROBOT_ARM.get(LV),
      'T', CASING_TEMPERED_GLASS,
      'H', HULL.get(LV),
      't', CABLE.get(LV));

    VanillaRecipeHelper.addShapedRecipe(
      provider, "stone_oreifier", STONE_OREIFIER.asStack(),
      "FCR", "THT", "tCt",
      'F', FLUID_REGULATOR_LV,
      'C', BETTER_CIRCUIT.get(LV),
      'R', ROBOT_ARM.get(LV),
      'T', CASING_TEMPERED_GLASS,
      'H', HULL.get(LV),
      't', CABLE.get(LV));
  }
}
