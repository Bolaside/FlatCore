package dev.thornium.flatcore.common.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dustSmall;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Sapphire;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.BLAST_RECIPES;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.CUTTER_RECIPES;
import static dev.thornium.flatcore.common.data.FTItems.GALLIUM_BOULE;
import static dev.thornium.flatcore.common.data.FTItems.GALLIUM_WAFER;
import static dev.thornium.flatcore.registry.FTMaterials.Gallium3Oxide;

public class CircuitRecipes {
    private CircuitRecipes() {}

    public static void init(Consumer<FinishedRecipe> provider) {
        BLAST_RECIPES.recipeBuilder("gallium_iii_oxide_boule")
                .inputItems(dust, Sapphire, 32)
                .inputItems(dustSmall, Gallium3Oxide, 2)
                .outputItems(GALLIUM_BOULE)
                .blastFurnaceTemp(1200)
                .EUt(VA[MV]).duration(20 * 60 * 3).save(provider);

        CUTTER_RECIPES.recipeBuilder("cut_gallium_iii_oxide_boule")
                .inputItems(GALLIUM_BOULE)
                .outputItems(GALLIUM_WAFER, 24)
                .EUt(VH[MV]).duration(20 * 40).save(provider);
    }
}
