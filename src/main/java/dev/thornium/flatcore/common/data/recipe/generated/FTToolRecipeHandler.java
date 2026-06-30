package dev.thornium.flatcore.common.data.recipe.generated;

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.IElectricItem;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.data.GTMaterialItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import dev.thornium.flatcore.api.data.tag.FTTagPrefix;
import dev.thornium.flatcore.api.item.tool.FTToolType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_DENSE;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_PLATE;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate;
import static com.gregtechceu.gtceu.data.recipe.generated.ToolRecipeHandler.powerUnitItems;

public final class FTToolRecipeHandler {
    public static void run(Consumer<FinishedRecipe> provider, Material material) {
        ToolProperty property = material.getProperty(PropertyKey.TOOL);
        if (property == null) return;

        processElectricTool(provider, property, material);
    }

    private static void processElectricTool(Consumer<FinishedRecipe> provider, ToolProperty property, Material material) {
        if (!material.shouldGenerateRecipesFor(plate)) return;

        TagPrefix toolPrefix;

        if (material.hasFlag(GENERATE_PLATE) && material.hasFlag(GENERATE_DENSE)) {
            MaterialEntry plateDense = new MaterialEntry(TagPrefix.plateDense, material);
            MaterialEntry steelPlate = new MaterialEntry(TagPrefix.plate, GTMaterials.Steel);
            MaterialEntry steelRing = new MaterialEntry(TagPrefix.ring, GTMaterials.Steel);

            // drill
            if (property.hasType(FTToolType.MULTI_TOOL)) {
                toolPrefix = FTTagPrefix.toolHeadMultiTool;
                VanillaRecipeHelper.addShapedRecipe(provider, String.format("multi_tool_head_%s", material.getName()),
                        ChemicalHelper.get(toolPrefix, material),
                        "X X", "RhR", "SXS",
                        'X', plateDense,
                        'R', steelRing,
                        'S', steelPlate);

                addElectricToolRecipe(provider, FTTagPrefix.toolHeadMultiTool,
                        new GTToolType[]{FTToolType.MULTI_TOOL}, material);
            }
        }
    }

    // come on bruh
    private static void addElectricToolRecipe(Consumer<FinishedRecipe> provider, TagPrefix toolHead,
                                              GTToolType[] toolItems, Material material) {
        for (GTToolType toolType : toolItems) {
            if (!material.getProperty(PropertyKey.TOOL).hasType(toolType)) continue;

            int tier = toolType.electricTier;
            ItemStack powerUnitStack = powerUnitItems.get(tier).asStack();
            IElectricItem powerUnit = GTCapabilityHelper.getElectricItem(powerUnitStack);
            ItemStack tool = GTMaterialItems.TOOL_ITEMS.get(material, toolType).get().get(0, powerUnit.getMaxCharge());
            VanillaRecipeHelper.addShapedEnergyTransferRecipe(provider,
                    true, true, true,
                    String.format("%s_%s", material.getName(), toolType.name),
                    Ingredient.of(powerUnitStack),
                    tool,
                    "wHd", " U ",
                    'H', new MaterialEntry(toolHead, material),
                    'U', powerUnitStack);
        }
    }
}
