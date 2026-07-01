package dev.thornium.flatcore.common.data.recipe

import com.gregtechceu.gtceu.api.GTValues.LV
import com.gregtechceu.gtceu.api.GTValues.MV
import com.gregtechceu.gtceu.common.data.GTBlocks.CASING_TEMPERED_GLASS
import com.gregtechceu.gtceu.common.data.GTItems.FLUID_REGULATOR_LV
import com.gregtechceu.gtceu.data.recipe.GTCraftingComponents.*
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper
import dev.thornium.flatcore.registry.machine.FTMachines.GREENHOUSE
import dev.thornium.flatcore.registry.machine.FTMachines.STONE_OREIFIER
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

object ControllerRecipes {
    fun init(provider: Consumer<FinishedRecipe>) {
        VanillaRecipeHelper.addShapedRecipe(
            provider, "greenhouse", GREENHOUSE.asStack(),
            "FCR", "THT", "tCt",
            'F', FLUID_REGULATOR_LV,
            'C', CIRCUIT.get(MV),
            'R', ROBOT_ARM.get(LV),
            'T', CASING_TEMPERED_GLASS,
            'H', HULL.get(LV),
            't', CABLE.get(LV),
        )

        VanillaRecipeHelper.addShapedRecipe(
            provider, "stone_oreifier", STONE_OREIFIER.asStack(),
            "FCR", "THT", "tCt",
            'F', FLUID_REGULATOR_LV,
            'C', BETTER_CIRCUIT.get(LV),
            'R', ROBOT_ARM.get(LV),
            'T', CASING_TEMPERED_GLASS,
            'H', HULL.get(LV),
            't', CABLE.get(LV),
        )
    }
}
