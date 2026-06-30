package dev.thornium.flatcore.common.data;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import dev.thornium.flatcore.common.data.recipe.*;

import dev.thornium.flatcore.common.data.recipe.generated.FTToolRecipeHandler;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class FTRecipes {
    @SuppressWarnings("deprecation")
    public static void init(Consumer<FinishedRecipe> provider) {
        CasingRecipes.init(provider);
        CircuitRecipes.init(provider);
        ComposterRecipes.init(provider);
        ControllerRecipes.init(provider);
        MixerRecipes.init(provider);

        for (Material material : GTCEuAPI.materialManager.getRegisteredMaterials()) {
            if (material.hasFlag(MaterialFlags.NO_UNIFICATION) ||
                    material.hasFlag(MaterialFlags.DISABLE_MATERIAL_RECIPES)) continue;

            FTToolRecipeHandler.run(provider, material);
        }
    }
}
