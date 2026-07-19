package dev.thornium.flatcore.common.data.recipe

import com.gregtechceu.gtceu.api.GTValues.LV
import com.gregtechceu.gtceu.api.GTValues.VH
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.frameGt
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate
import com.gregtechceu.gtceu.common.data.GTMaterials.WroughtIron
import com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES
import com.gregtechceu.gtceu.config.ConfigHolder
import com.tterrag.registrate.util.entry.BlockEntry
import dev.thornium.flatcore.common.data.FTBlocks
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.level.block.Block
import java.util.function.Consumer

object CasingRecipes {
    fun init(provider: Consumer<FinishedRecipe>) {
        makeBasicCasingRecipe("casing_wrought_iron_rough", WroughtIron, FTBlocks.CASING_WROUGHT_IRON_ROUGH)
            .save(provider)
    }

    private fun makeBasicCasingRecipe(recipeId: String, material: Material, blockEntry: BlockEntry<Block>) =
        ASSEMBLER_RECIPES.recipeBuilder(recipeId)
            .inputItems(plate, material, 6)
            .inputItems(frameGt, material)
            .outputItems(blockEntry.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
            .circuitMeta(6)
            .addMaterialInfo(true)
            .EUt(VH[LV].toLong()).duration((20 * 2.5).toInt())
}
