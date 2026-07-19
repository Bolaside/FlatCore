package dev.thornium.flatcore.common.data.recipe

import dev.thornium.flatcore.gtbridge.FTRecipeTypes.COMPOSTER_RECIPES
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.ComposterBlock
import net.minecraftforge.registries.ForgeRegistries
import java.util.function.Consumer

object ComposterRecipes {
    fun init(provider: Consumer<FinishedRecipe>) {
        for (compostable: ItemLike in ComposterBlock.COMPOSTABLES.keys) {
            val item = compostable.asItem()
            val itemId = ForgeRegistries.ITEMS.getKey(item)?.path ?: continue

            COMPOSTER_RECIPES.recipeBuilder("compost_$itemId")
                .inputItems(item)
                .save(provider)
        }
    }
}
