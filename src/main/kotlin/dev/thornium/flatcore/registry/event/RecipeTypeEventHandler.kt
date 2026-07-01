package dev.thornium.flatcore.registry.event

import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.recipe.GTRecipeType
import dev.thornium.flatcore.gtbridge.FTRecipeTypes
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.eventbus.api.IEventBus

class RecipeTypeEventHandler : ModEventListener {
    override fun registerTo(modEventBus: IEventBus) {
        modEventBus.addGenericListener(GTRecipeType::class.java, ::onRegister)
    }

    private fun onRegister(event: GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType>) {
        FTRecipeTypes.init()
    }
}
