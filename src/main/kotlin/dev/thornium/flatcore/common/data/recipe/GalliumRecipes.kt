package dev.thornium.flatcore.common.data.recipe

import com.gregtechceu.gtceu.api.GTValues.*
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.common.data.GTMaterials
import com.gregtechceu.gtceu.common.data.GTRecipeTypes.CHEMICAL_RECIPES
import dev.thornium.flatcore.registry.FTMaterials
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

object GalliumRecipes {
    fun init(provider: Consumer<FinishedRecipe>) {
        CHEMICAL_RECIPES.recipeBuilder("dissolve_gallium_in_nitric_acid")
            .inputItems(TagPrefix.dust, GTMaterials.Gallium)
            .inputFluids(GTMaterials.NitricAcid.getFluid(4000))
            .outputItems(TagPrefix.dust, FTMaterials.GalliumNitrate)
            .outputFluids(GTMaterials.NitrogenDioxide.getFluid(1000))
            .outputFluids(GTMaterials.Water.getFluid(2000))
            .EUt(VA[LV].toLong())
            .duration(20 * 20)
            .save(provider)

        CHEMICAL_RECIPES.recipeBuilder("calcinate_gallium_nitrate")
            .inputItems(TagPrefix.dust, FTMaterials.GalliumNitrate, 4)
            .outputItems(TagPrefix.dust, FTMaterials.Gallium3Oxide, 2)
            .outputFluids(GTMaterials.NitrogenDioxide.getFluid(12000))
            .outputFluids(GTMaterials.Oxygen.getFluid(3000))
            .EUt(VA[LV].toLong())
            .duration(20 * 12)
            .save(provider)
    }
}