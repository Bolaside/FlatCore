package dev.thornium.flatcore.common.data.recipe.generated

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.NO_SMASHING
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.*
import com.gregtechceu.gtceu.common.data.GTItems.*
import com.gregtechceu.gtceu.common.data.GTMaterials
import com.gregtechceu.gtceu.api.GTValues.LV
import com.gregtechceu.gtceu.api.GTValues.MV
import com.gregtechceu.gtceu.api.GTValues.ULV
import com.gregtechceu.gtceu.api.GTValues.VA
import dev.thornium.flatcore.gtbridge.FTRecipeTypes
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import java.util.function.Consumer

object FTShapeRecipeHandler {
    fun run(provider: Consumer<FinishedRecipe>, material: Material) {
        processPipes(provider, material)
        processRings(provider, material)
        processBolts(provider, material)
        processGears(provider, gear, material)
        processGears(provider, gearSmall, material)
        processRotors(provider, material)
    }

    fun init(provider: Consumer<FinishedRecipe>) {
        with(FTRecipeTypes.IMPACT_FORMER_RECIPES) {
            recipeBuilder("fluid_cell_tin")
                .inputItems(ingot, GTMaterials.Tin, 2)
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .outputItems(FLUID_CELL)
                .duration(128).EUt(VA[LV].toLong())
                .save(provider)

            recipeBuilder("fluid_cell_steel")
                .inputItems(ingot, GTMaterials.Steel)
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .outputItems(FLUID_CELL)
                .duration(128).EUt(VA[LV].toLong())
                .save(provider)

            recipeBuilder("fluid_cell_ptfe")
                .inputItems(ingot, GTMaterials.Polytetrafluoroethylene)
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .outputItems(FLUID_CELL, 4)
                .duration(128).EUt(VA[LV].toLong())
                .save(provider)

            recipeBuilder("fluid_cell_pbi")
                .inputItems(ingot, GTMaterials.Polybenzimidazole)
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .outputItems(FLUID_CELL, 16)
                .duration(128).EUt(VA[LV].toLong())
                .save(provider)

            recipeBuilder("glass_vial")
                .inputItems(dust, GTMaterials.Glass)
                .notConsumable(SHAPE_EXTRUDER_CELL)
                .outputItems(FLUID_CELL_GLASS_VIAL, 4)
                .duration(128).EUt(VA[LV].toLong())
                .addMaterialInfo(true)
                .save(provider)

            recipeBuilder("glass_bottle")
                .inputItems(dust, GTMaterials.Glass)
                .notConsumable(SHAPE_EXTRUDER_BOTTLE)
                .outputItems(ItemStack(Items.GLASS_BOTTLE))
                .duration(32).EUt(16)
                .save(provider)
        }
    }

    private fun processPipes(provider: Consumer<FinishedRecipe>, material: Material) {
        if (material.hasProperty(PropertyKey.WOOD)) return

        processPipe(provider, PropertyKey.FLUID_PIPE, pipeTinyFluid, material, ingots = 1, outputs = 2, duration = 1)
        processPipe(provider, PropertyKey.FLUID_PIPE, pipeSmallFluid, material, ingots = 1, outputs = 1, duration = 1)
        processPipe(provider, PropertyKey.FLUID_PIPE, pipeNormalFluid, material, ingots = 3, outputs = 1, duration = 3)
        processPipe(provider, PropertyKey.FLUID_PIPE, pipeLargeFluid, material, ingots = 6, outputs = 1, duration = 6)
        processPipe(provider, PropertyKey.FLUID_PIPE, pipeHugeFluid, material, ingots = 12, outputs = 1, duration = 24)
        processPipe(provider, PropertyKey.ITEM_PIPE, pipeSmallItem, material, ingots = 1, outputs = 1, duration = 1)
        processPipe(provider, PropertyKey.ITEM_PIPE, pipeNormalItem, material, ingots = 3, outputs = 1, duration = 3)
        processPipe(provider, PropertyKey.ITEM_PIPE, pipeLargeItem, material, ingots = 6, outputs = 1, duration = 6)
        processPipe(provider, PropertyKey.ITEM_PIPE, pipeHugeItem, material, ingots = 12, outputs = 1, duration = 24)
    }

    private fun processPipe(
        provider: Consumer<FinishedRecipe>,
        propertyKey: PropertyKey<*>,
        prefix: TagPrefix,
        material: Material,
        ingots: Int,
        outputs: Int,
        duration: Int,
    ) {
        if (!material.shouldGenerateRecipesFor(prefix) || !material.hasProperty(propertyKey)) return
        val pipeStack = ChemicalHelper.get(prefix, material)

        if (pipeStack.isEmpty) return
        val voltMultiplier = getVoltageMultiplier(material)

        FTRecipeTypes.PIPE_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_$prefix")
            .inputItems(ingot, material, ingots)
            .notConsumable(shapeFor(prefix))
            .outputItems(pipeStack.copyWithCount(outputs))
            .duration((material.mass * duration).toInt())
            .EUt(6L * voltMultiplier)
            .save(provider)

        if (material.hasFlag(NO_SMASHING)) {
            FTRecipeTypes.PIPE_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_${prefix}_dust")
                .inputItems(dust, material, ingots)
                .notConsumable(shapeFor(prefix))
                .outputItems(pipeStack.copyWithCount(outputs))
                .duration((material.mass * duration).toInt())
                .EUt(6L * voltMultiplier)
                .save(provider)
        }
    }

    private fun processRings(provider: Consumer<FinishedRecipe>, material: Material) {
        if (!material.shouldGenerateRecipesFor(ring) || !material.hasProperty(PropertyKey.INGOT)) return
        val ringStack = ChemicalHelper.get(ring, material)
        val voltMultiplier = getVoltageMultiplier(material)

        FTRecipeTypes.IMPACT_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_ingot_to_ring")
            .inputItems(ingot, material)
            .notConsumable(SHAPE_EXTRUDER_RING)
            .outputItems(ringStack, 4)
            .duration((material.mass * 2).toInt())
            .EUt(6L * voltMultiplier)
            .save(provider)

        if (material.hasFlag(NO_SMASHING)) {
            FTRecipeTypes.IMPACT_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_dust_to_ring")
                .inputItems(dust, material)
                .notConsumable(SHAPE_EXTRUDER_RING)
                .outputItems(ringStack, 4)
                .duration((material.mass * 2).toInt())
                .EUt(6L * voltMultiplier)
                .save(provider)
        }
    }

    private fun processBolts(provider: Consumer<FinishedRecipe>, material: Material) {
        if (!material.shouldGenerateRecipesFor(bolt) || !material.hasProperty(PropertyKey.DUST)) return
        val boltStack = ChemicalHelper.get(bolt, material)
        if (boltStack.isEmpty || !material.hasProperty(PropertyKey.INGOT)) return

        FTRecipeTypes.IMPACT_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_ingot_to_bolt")
            .inputItems(ingot, material)
            .notConsumable(SHAPE_EXTRUDER_BOLT)
            .outputItems(boltStack.copyWithCount(8))
            .duration(15)
            .EUt(VA[MV].toLong())
            .save(provider)

        if (material.hasFlag(NO_SMASHING)) {
            FTRecipeTypes.IMPACT_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_dust_to_bolt")
                .inputItems(dust, material)
                .notConsumable(SHAPE_EXTRUDER_BOLT)
                .outputItems(boltStack.copyWithCount(8))
                .duration(15)
                .EUt(VA[MV].toLong())
                .save(provider)
        }
    }

    private fun processGears(provider: Consumer<FinishedRecipe>, prefix: TagPrefix, material: Material) {
        if (!material.shouldGenerateRecipesFor(prefix) || !material.hasProperty(PropertyKey.DUST)) return
        val isSmall = prefix == gearSmall
        if (!isSmall && !material.hasProperty(PropertyKey.INGOT)) return

        val stack = ChemicalHelper.get(prefix, material)
        val voltMultiplier = getVoltageMultiplier(material)

        if (isSmall) {
            val euPerTick = if (material.blastTemperature >= 2800) 256L else 64L
            val duration = material.mass.toInt()

            FTRecipeTypes.ROTARY_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_ingot_to_small_gear")
                .inputItems(ingot, material)
                .notConsumable(SHAPE_EXTRUDER_GEAR_SMALL)
                .outputItems(stack)
                .duration(duration)
                .EUt(euPerTick)
                .save(provider)
            if (material.hasFlag(NO_SMASHING)) {
                FTRecipeTypes.ROTARY_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_dust_to_small_gear")
                    .inputItems(dust, material)
                    .notConsumable(SHAPE_EXTRUDER_GEAR_SMALL)
                    .outputItems(stack)
                    .duration(duration)
                    .EUt(euPerTick)
                    .save(provider)
            }
        } else {
            FTRecipeTypes.ROTARY_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_ingot_to_gear")
                .inputItems(ingot, material, 4)
                .notConsumable(SHAPE_EXTRUDER_GEAR)
                .outputItems(stack)
                .duration((material.mass * 5).toInt())
                .EUt(8L * voltMultiplier)
                .save(provider)
            if (material.hasFlag(NO_SMASHING)) {
                FTRecipeTypes.ROTARY_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_dust_to_gear")
                    .inputItems(dust, material, 4)
                    .notConsumable(SHAPE_EXTRUDER_GEAR)
                    .outputItems(stack)
                    .duration((material.mass * 5).toInt())
                    .EUt(8L * voltMultiplier)
                    .save(provider)
            }
        }
    }

    private fun processRotors(provider: Consumer<FinishedRecipe>, material: Material) {
        if (!material.shouldGenerateRecipesFor(rotor) || !material.hasProperty(PropertyKey.INGOT)) return
        val rotorStack = ChemicalHelper.get(rotor, material)

        FTRecipeTypes.ROTARY_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_ingot_to_rotor")
            .inputItems(ingot, material, 4)
            .notConsumable(SHAPE_EXTRUDER_ROTOR)
            .outputItems(rotorStack)
            .duration((material.mass * 4).toInt())
            .EUt(if (material.blastTemperature >= 2800) 256L else 64L)
            .save(provider)

        if (material.hasFlag(NO_SMASHING)) {
            FTRecipeTypes.ROTARY_FORMER_RECIPES.recipeBuilder("extrude_${material.name}_dust_to_rotor")
                .inputItems(dust, material, 4)
                .notConsumable(SHAPE_EXTRUDER_ROTOR)
                .outputItems(rotorStack)
                .duration((material.mass * 4).toInt())
                .EUt(if (material.blastTemperature >= 2800) 256L else 64L)
                .save(provider)
        }
    }

    private fun getVoltageMultiplier(material: Material): Int = when {
        material.hasProperty(PropertyKey.POLYMER) -> 4
        material.blastTemperature >= 2800 -> VA[LV]
        else -> VA[ULV]
    }

    private fun shapeFor(prefix: TagPrefix) = when (prefix) {
        pipeTinyFluid -> SHAPE_EXTRUDER_PIPE_TINY.asStack()
        pipeSmallFluid, pipeSmallItem -> SHAPE_EXTRUDER_PIPE_SMALL.asStack()
        pipeNormalFluid, pipeNormalItem -> SHAPE_EXTRUDER_PIPE_NORMAL.asStack()
        pipeLargeFluid, pipeLargeItem -> SHAPE_EXTRUDER_PIPE_LARGE.asStack()
        pipeHugeFluid, pipeHugeItem -> SHAPE_EXTRUDER_PIPE_HUGE.asStack()
        else -> ItemStack.EMPTY
    }
}
