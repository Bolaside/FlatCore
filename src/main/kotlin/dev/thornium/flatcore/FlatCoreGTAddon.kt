package dev.thornium.flatcore

import com.gregtechceu.gtceu.api.addon.GTAddon
import com.gregtechceu.gtceu.api.addon.IGTAddon
import dev.thornium.flatcore.api.chemical.material.info.FTMaterialIconType
import dev.thornium.flatcore.api.data.tag.FTTagPrefix
import dev.thornium.flatcore.common.data.FTRecipes
import net.minecraft.data.recipes.FinishedRecipe
import java.util.function.Consumer

@Suppress("unused")
@GTAddon
class FlatCoreGTAddon : IGTAddon {
    override fun getRegistrate() = FlatCore.REGISTRATE

    override fun initializeAddon() {}

    override fun addonModId() = FlatCore.MOD_ID

    override fun registerTagPrefixes() {
        FTMaterialIconType.init()
        FTTagPrefix.init()
    }

    override fun addRecipes(provider: Consumer<FinishedRecipe>) {
        FTRecipes.init(provider)
    }
}
