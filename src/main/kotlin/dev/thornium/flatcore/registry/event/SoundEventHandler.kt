package dev.thornium.flatcore.registry.event

import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.sound.SoundEntry
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.eventbus.api.IEventBus

class SoundEventHandler : ModEventListener {
    override fun registerTo(modEventBus: IEventBus) {
        modEventBus.addGenericListener(SoundEntry::class.java, ::onRegister)
    }

    private fun onRegister(event: GTCEuAPI.RegisterEvent<ResourceLocation, SoundEntry>) {}
}
