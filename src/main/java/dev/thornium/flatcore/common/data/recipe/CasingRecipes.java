package dev.thornium.flatcore.common.data.recipe;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.thornium.flatcore.common.data.FTBlocks;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.LV;
import static com.gregtechceu.gtceu.api.GTValues.VH;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.frameGt;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate;
import static com.gregtechceu.gtceu.common.data.GTMaterials.WroughtIron;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;

public class CasingRecipes {
    private CasingRecipes() {}

    public static void init(Consumer<FinishedRecipe> provider) {
        makeBasicCasingRecipe("casing_wrought_iron_rough", WroughtIron, FTBlocks.CASING_WROUGHT_IRON_ROUGH)
                .save(provider);
    }

    private static GTRecipeBuilder makeBasicCasingRecipe(String recipeId, Material material,
                                                         BlockEntry<Block> blockEntry) {
        return ASSEMBLER_RECIPES.recipeBuilder(recipeId)
                .inputItems(plate, material, 6)
                .inputItems(frameGt, material)
                .outputItems(blockEntry.asStack(ConfigHolder.INSTANCE.recipes.casingsPerCraft))
                .circuitMeta(6)
                .addMaterialInfo(true)
                .EUt(VH[LV]).duration((int) (20 * 2.5));
    }
}
