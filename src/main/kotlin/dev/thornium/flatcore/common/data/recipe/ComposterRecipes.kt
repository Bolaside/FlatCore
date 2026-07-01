package dev.thornium.flatcore.common.data.recipe

import dev.thornium.flatcore.gtbridge.FTRecipeTypes.COMPOSTER_RECIPES
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.ComposterBlock
import java.util.function.Consumer

object ComposterRecipes {
    fun init(provider: Consumer<FinishedRecipe>) {
        for (compostable: ItemLike in ComposterBlock.COMPOSTABLES.keys) {
            val item = compostable.asItem()

            COMPOSTER_RECIPES.recipeBuilder("compost_" + BuiltInRegistries.ITEM.getKey(item).path)
                .inputItems(item)
                .save(provider)
        }
    }
}
