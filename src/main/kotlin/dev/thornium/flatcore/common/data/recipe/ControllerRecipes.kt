package dev.thornium.flatcore.common.data.recipe

import com.gregtechceu.gtceu.api.GTValues.LV
import com.gregtechceu.gtceu.api.GTValues.MV
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.gear
import com.gregtechceu.gtceu.common.data.GTBlocks.CASING_TEMPERED_GLASS
import com.gregtechceu.gtceu.common.data.GTItems.FLUID_REGULATOR_LV
import com.gregtechceu.gtceu.common.data.GTMaterials.Steel
import com.gregtechceu.gtceu.data.recipe.GTCraftingComponents.*
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper
import dev.thornium.flatcore.common.data.FTBlocks
import dev.thornium.flatcore.registry.machine.FTMachines.GREENHOUSE
import dev.thornium.flatcore.registry.machine.FTMachines.IMPACT_FORMER
import dev.thornium.flatcore.registry.machine.FTMachines.PIPE_FORMER
import dev.thornium.flatcore.registry.machine.FTMachines.ROTARY_FORMER
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

        VanillaRecipeHelper.addShapedRecipe(
            provider, "pipe_former", PIPE_FORMER.asStack(),
            "PCP", "RHR", "pCp",
            'P', PUMP.get(LV),
            'C', BETTER_CIRCUIT.get(LV),
            'R', ROBOT_ARM.get(LV),
            'H', HULL.get(LV),
            'p', FTBlocks.CASING_CAST_MANIFOLD.asStack(),
        )

        VanillaRecipeHelper.addShapedRecipe(
            provider, "impact_former", IMPACT_FORMER.asStack(),
            "FCR", "THT", "pCp",
            'F', FLUID_REGULATOR_LV,
            'C', BETTER_CIRCUIT.get(MV),
            'R', ROBOT_ARM.get(MV),
            'T', CASING_TEMPERED_GLASS,
            'H', HULL.get(MV),
            'p', FTBlocks.CASING_PRESSURE_VESSEL.asStack(),
        )

        VanillaRecipeHelper.addShapedRecipe(
            provider, "rotary_former", ROTARY_FORMER.asStack(),
            "gCg", "RHR", "pCp",
            'g', MaterialEntry(gear, Steel),
            'C', BETTER_CIRCUIT.get(MV),
            'R', ROBOT_ARM.get(MV),
            'H', HULL.get(MV),
            'p', FTBlocks.CASING_BLANKING_DIE.asStack(),
        )
    }
}
