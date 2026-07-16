package dev.thornium.flatcore.common.data

import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags
import dev.thornium.flatcore.common.data.recipe.*
import dev.thornium.flatcore.common.data.recipe.generated.FTToolRecipeHandler
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

object FTRecipes {
    fun init(provider: Consumer<FinishedRecipe>) {
        CasingRecipes.init(provider)
        CircuitRecipes.init(provider)
        ComposterRecipes.init(provider)
        ControllerRecipes.init(provider)
        MixerRecipes.init(provider)

        for (material in GTCEuAPI.materialManager.registeredMaterials) {
            @Suppress("DEPRECATION") // why is this capital
            if (material.hasFlag(MaterialFlags.NO_UNIFICATION) ||
                material.hasFlag(MaterialFlags.DISABLE_MATERIAL_RECIPES)
            ) {
                continue
            }

            FTToolRecipeHandler.run(provider, material)
        }
    }
}
