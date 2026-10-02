package dev.thornium.flatcore.common.data.recipe

import com.gregtechceu.gtceu.api.GTValues.LV
import com.gregtechceu.gtceu.api.GTValues.VH
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.frameGt
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.gear
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate
import com.gregtechceu.gtceu.common.data.GTMaterials.Invar
import com.gregtechceu.gtceu.common.data.GTMaterials.StainlessSteel
import com.gregtechceu.gtceu.common.data.GTMaterials.Steel
import com.gregtechceu.gtceu.common.data.GTMaterials.WroughtIron
import com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES
import com.gregtechceu.gtceu.config.ConfigHolder
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper
import com.tterrag.registrate.util.entry.BlockEntry
import dev.thornium.flatcore.common.data.FTBlocks.CASING_BLANKING_DIE
import dev.thornium.flatcore.common.data.FTBlocks.CASING_CAST_MANIFOLD
import dev.thornium.flatcore.common.data.FTBlocks.CASING_PRESSURE_VESSEL
import dev.thornium.flatcore.common.data.FTBlocks.CASING_WROUGHT_IRON_ROUGH
import dev.thornium.flatcore.registry.FTMaterials.PigIron
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.level.block.Block
import java.util.function.Consumer

object CasingRecipes {
    fun init(provider: Consumer<FinishedRecipe>) {
        makeBasicCasingRecipe("casing_wrought_iron_rough", WroughtIron, CASING_WROUGHT_IRON_ROUGH).save(provider)
        makeBasicCasingRecipe("casing_cast_manifold", PigIron, CASING_CAST_MANIFOLD).save(provider)
        makeBasicCasingRecipe("casing_pressure_vessel", Invar, Steel, CASING_PRESSURE_VESSEL).save(provider)
        makeGearboxStyleCasingRecipe("casing_blanking_die", StainlessSteel, Steel, CASING_BLANKING_DIE).save(provider)

        makeShapedCasingRecipe(provider, "casing_wrought_iron_rough", WroughtIron, WroughtIron, CASING_WROUGHT_IRON_ROUGH)
        makeShapedCasingRecipe(provider, "casing_cast_manifold", PigIron, PigIron, CASING_CAST_MANIFOLD)
        makeShapedCasingRecipe(provider, "casing_pressure_vessel", Invar, Steel, CASING_PRESSURE_VESSEL)
        makeShapedGearboxCasingRecipe(
            provider, "casing_blanking_die",
            StainlessSteel, Steel, StainlessSteel, CASING_BLANKING_DIE
        )
    }

    private fun makeBasicCasingRecipe(recipeId: String, material: Material, blockEntry: BlockEntry<Block>) =
        makeBasicCasingRecipe(recipeId, material, material, blockEntry)

    private fun makeBasicCasingRecipe(
        recipeId: String,
        material: Material,
        frameMaterial: Material,
        blockEntry: BlockEntry<Block>,
    ) = ASSEMBLER_RECIPES.recipeBuilder(recipeId)
        .inputItems(plate, material, 6)
        .inputItems(frameGt, frameMaterial)
        .outputItems(blockEntry.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
        .circuitMeta(6)
        .addMaterialInfo(true)
        .EUt(VH[LV].toLong()).duration(50)

    private fun makeGearboxStyleCasingRecipe(
        recipeId: String,
        material: Material,
        gearMaterial: Material,
        blockEntry: BlockEntry<Block>,
    ) = ASSEMBLER_RECIPES.recipeBuilder(recipeId)
        .inputItems(plate, material, 4)
        .inputItems(gear, gearMaterial, 2)
        .inputItems(frameGt, material)
        .outputItems(blockEntry.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
        .circuitMeta(4)
        .EUt(VH[LV].toLong()).duration(50)
        .addMaterialInfo(true)

    private fun makeShapedCasingRecipe(
        provider: Consumer<FinishedRecipe>,
        recipeId: String,
        material: Material,
        frameMaterial: Material,
        blockEntry: BlockEntry<Block>,
    ) {
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, recipeId,
            blockEntry.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft),
            "PhP", "PFP", "PwP",
            'P', MaterialEntry(plate, material),
            'F', MaterialEntry(frameGt, frameMaterial),
        )
    }

    private fun makeShapedGearboxCasingRecipe(
        provider: Consumer<FinishedRecipe>,
        recipeId: String,
        material: Material,
        gearMaterial: Material,
        frameMaterial: Material,
        blockEntry: BlockEntry<Block>,
    ) {
        VanillaRecipeHelper.addShapedRecipe(
            provider, true, recipeId,
            blockEntry.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft),
            "PhP", "GFG", "PwP",
            'P', MaterialEntry(plate, material),
            'G', MaterialEntry(gear, gearMaterial),
            'F', MaterialEntry(frameGt, frameMaterial),
        )
    }
}
