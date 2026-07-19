package dev.thornium.flatcore.gtbridge

import com.gregtechceu.gtceu.api.GTValues.ULV
import com.gregtechceu.gtceu.api.GTValues.VHA
import com.gregtechceu.gtceu.api.capability.recipe.IO
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability
import com.gregtechceu.gtceu.api.gui.GuiTextures
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient
import com.gregtechceu.gtceu.common.data.GTRecipeTypes.*
import com.gregtechceu.gtceu.common.data.GTSoundEntries
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT
import dev.thornium.flatcore.api.Initialized
import dev.thornium.flatcore.api.gui.FTTextures
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.ComposterBlock

object FTRecipeTypes : Initialized {
    val GREENHOUSE_RECIPES = register("greenhouse", MULTIBLOCK)
        .setMaxIOSize(3, 6, 1, 0)
        .setEUIO(IO.IN)
        .setSlotOverlay(false, false, GuiTextures.ARROW_INPUT_OVERLAY)
        .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
        .setSound(GTSoundEntries.TURBINE)

    val STONE_OREIFIER_RECIPES = register("stone_oreifier", MULTIBLOCK)
        .setMaxIOSize(2, 6, 1, 0)
        .setEUIO(IO.IN)
        .setSlotOverlay(false, false, GuiTextures.ARROW_INPUT_OVERLAY)
        .setProgressBar(FTTextures.PROGRESS_BAR_STONE_OREIFIER, LEFT_TO_RIGHT)
        .setSound(GTSoundEntries.TURBINE)

    val COMPOSTER_RECIPES = register("electric_composter", ELECTRIC)
        .setMaxIOSize(1, 1, 0, 0)
        .prepareBuilder { recipeBuilder -> recipeBuilder.EUt(VHA[ULV].toLong()).duration(20) }
        .onRecipeBuild { recipeBuilder, _ ->
            // There can only be one.
            val ingredient = recipeBuilder.input[ItemRecipeCapability.CAP]
                ?.get(0)
                ?.content as SizedIngredient
            val inputItem = ingredient.items[0].item

            val chance = (ComposterBlock.COMPOSTABLES.getFloat(inputItem) * 10000).toInt()
            recipeBuilder.chancedOutput(Items.BONE_MEAL.defaultInstance, chance, 0)
        }
        .setProgressBar(GuiTextures.PROGRESS_BAR_RECYCLER, LEFT_TO_RIGHT)
        .setSound(GTSoundEntries.MORTAR_TOOL)
}
