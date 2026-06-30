package dev.thornium.flatcore.common.data.recipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

import java.util.function.Consumer;

import static dev.thornium.flatcore.gtbridge.FTRecipeTypes.COMPOSTER_RECIPES;

public class ComposterRecipes {
    private ComposterRecipes() {}

    public static void init(Consumer<FinishedRecipe> provider) {
        for (ItemLike compostable : ComposterBlock.COMPOSTABLES.keySet()) {
            Item item = compostable.asItem();

            COMPOSTER_RECIPES.recipeBuilder("compost_" + BuiltInRegistries.ITEM.getKey(item).getPath())
                    .inputItems(item)
                    .save(provider);
        }
    }
}
