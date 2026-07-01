package dev.thornium.flatcore.common.data.recipe

import com.gregtechceu.gtceu.api.GTValues.*
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.common.data.GTMaterials
import com.gregtechceu.gtceu.common.data.GTRecipeTypes.MIXER_RECIPES
import dev.thornium.flatcore.registry.FTMaterials
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

object MixerRecipes {
    fun init(provider: Consumer<FinishedRecipe>) {
        MIXER_RECIPES.recipeBuilder("redstone_glowstone_mixture")
            .inputFluids(GTMaterials.Redstone.getFluid(L))
            .inputFluids(GTMaterials.Glowstone.getFluid(L))
            .outputFluids(FTMaterials.RedstoneGlowstoneMixture.getFluid(L * 2))
            .EUt(VA[LV].toLong())
            .duration(20 * 10)
            .save(provider)

        MIXER_RECIPES.recipeBuilder("red_brass")
            .inputItems(TagPrefix.dust, GTMaterials.Copper, 6)
            .inputItems(TagPrefix.dust, GTMaterials.Tin, 2)
            .inputItems(TagPrefix.dust, GTMaterials.Zinc)
            .outputItems(TagPrefix.dust, FTMaterials.RedBrass, 9)
            .EUt(VA[ULV].toLong())
            .duration(20 * 15)
            .save(provider)

        MIXER_RECIPES.recipeBuilder("azuron")
            .inputItems(TagPrefix.dust, GTMaterials.Lead, 2)
            .inputItems(TagPrefix.dust, GTMaterials.Copper, 2)
            .inputItems(TagPrefix.dust, GTMaterials.Cobalt)
            .inputItems(TagPrefix.dust, GTMaterials.Silicon, 2)
            .inputFluids(GTMaterials.Oxygen.getFluid(9000))
            .outputItems(TagPrefix.dust, FTMaterials.Azuron, 16)
            .EUt(VA[MV].toLong())
            .duration(20 * 45)
            .save(provider)

        MIXER_RECIPES.recipeBuilder("constantan")
            .inputItems(TagPrefix.dust, GTMaterials.Iron, 4)
            .inputItems(TagPrefix.dust, GTMaterials.Manganese)
            .inputItems(TagPrefix.dust, GTMaterials.Copper)
            .outputItems(TagPrefix.dust, FTMaterials.Constantan, 6)
            .EUt(VA[LV].toLong())
            .duration(20 * 20)
            .save(provider)
    }
}
