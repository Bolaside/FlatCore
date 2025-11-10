package dev.thornium.flatcore.common.data;

import net.minecraft.data.recipes.FinishedRecipe;

import dev.thornium.flatcore.common.data.recipe.ControllerRecipes;
import dev.thornium.flatcore.common.data.recipe.processing_lines.StoneOreMassRecipes;

import java.util.function.Consumer;

public class FTRecipes {
  public static void init(Consumer<FinishedRecipe> provider) {
    ControllerRecipes.init(provider);

    // processing lines
    StoneOreMassRecipes.init(provider);
  }
}
