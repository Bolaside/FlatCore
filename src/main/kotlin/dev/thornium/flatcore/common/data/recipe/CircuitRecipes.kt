package dev.thornium.flatcore.common.data.recipe

import com.gregtechceu.gtceu.api.GTValues.*
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.dustSmall
import com.gregtechceu.gtceu.common.data.GTMaterials.Sapphire
import com.gregtechceu.gtceu.common.data.GTRecipeTypes.BLAST_RECIPES
import com.gregtechceu.gtceu.common.data.GTRecipeTypes.CUTTER_RECIPES
import dev.thornium.flatcore.common.data.FTItems.GALLIUM_BOULE
import dev.thornium.flatcore.common.data.FTItems.GALLIUM_WAFER
import dev.thornium.flatcore.registry.FTMaterials.Gallium3Oxide
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

object CircuitRecipes {
    fun init(provider: Consumer<FinishedRecipe>) {
        BLAST_RECIPES.recipeBuilder("gallium_iii_oxide_boule")
            .inputItems(dust, Sapphire, 32)
            .inputItems(dustSmall, Gallium3Oxide, 2)
            .outputItems(GALLIUM_BOULE)
            .blastFurnaceTemp(1200)
            .EUt(VA[MV].toLong()).duration(20 * 60 * 3).save(provider)

        CUTTER_RECIPES.recipeBuilder("cut_gallium_iii_oxide_boule")
            .inputItems(GALLIUM_BOULE)
            .outputItems(GALLIUM_WAFER, 24)
            .EUt(VH[MV].toLong()).duration(20 * 40).save(provider)
    }
}
