package dev.thornium.flatcore.common.data.recipe.generated

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_DENSE
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_PLATE
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate
import com.gregtechceu.gtceu.api.item.tool.GTToolType
import com.gregtechceu.gtceu.common.data.GTMaterialItems
import com.gregtechceu.gtceu.common.data.GTMaterials
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper
import com.gregtechceu.gtceu.data.recipe.generated.ToolRecipeHandler.powerUnitItems
import dev.thornium.flatcore.api.data.tag.FTTagPrefix
import dev.thornium.flatcore.api.item.tool.FTToolType
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import java.util.function.Consumer

object FTToolRecipeHandler {
    fun run(provider: Consumer<FinishedRecipe>, material: Material) {
        val property = material.getProperty(PropertyKey.TOOL) ?: return
        processElectricTool(provider, property, material)
    }

    private fun processElectricTool(provider: Consumer<FinishedRecipe>, property: ToolProperty, material: Material) {
        if (!material.shouldGenerateRecipesFor(plate)) return
        if (!(material.hasFlag(GENERATE_PLATE) && material.hasFlag(GENERATE_DENSE))) return

        val plateDense = MaterialEntry(TagPrefix.plateDense, material)
        val steelPlate = MaterialEntry(TagPrefix.plate, GTMaterials.Steel)
        val steelRing = MaterialEntry(TagPrefix.ring, GTMaterials.Steel)

        if (!property.hasType(FTToolType.MULTI_TOOL)) return

        VanillaRecipeHelper.addShapedRecipe(
            provider, "multi_tool_head_${material.name}",
            ChemicalHelper.get(FTTagPrefix.toolHeadMultiTool, material),
            "X X", "RhR", "SXS",
            'X', plateDense,
            'R', steelRing,
            'S', steelPlate,
        )

        addElectricToolRecipe(
            provider,
            FTTagPrefix.toolHeadMultiTool,
            arrayOf(FTToolType.MULTI_TOOL),
            property,
            material
        )
    }

    private fun addElectricToolRecipe(
        provider: Consumer<FinishedRecipe>,
        toolHead: TagPrefix,
        toolItems: Array<GTToolType>,
        property: ToolProperty,
        material: Material,
    ) {
        for (toolType in toolItems) {
            if (!property.hasType(toolType)) continue

            val powerUnitStack = powerUnitItems[toolType.electricTier].asStack()
            val powerUnit = GTCapabilityHelper.getElectricItem(powerUnitStack)!!
            val tool: ItemStack = GTMaterialItems.TOOL_ITEMS.get(material, toolType)!!
                .get()
                .get(0, powerUnit.maxCharge)

            VanillaRecipeHelper.addShapedEnergyTransferRecipe(
                provider,
                true, true, true,
                "${material.name}_${toolType.name}",
                Ingredient.of(powerUnitStack),
                tool,
                "wHd", " U ",
                'H', MaterialEntry(toolHead, material),
                'U', powerUnitStack,
            )
        }
    }
}
