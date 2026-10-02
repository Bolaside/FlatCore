package dev.thornium.flatcore.registry.event

import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

class LifecycleEventHandler : ModEventListener {
    override fun registerTo(modEventBus: IEventBus) {
        modEventBus.addListener(::onCommonSetup)
        modEventBus.addListener(::onClientSetup)
    }

    @Suppress("EmptyMethod")
    private fun onCommonSetup(@Suppress("unused") event: FMLCommonSetupEvent) {}

    @Suppress("EmptyMethod")
    private fun onClientSetup(@Suppress("unused") event: FMLClientSetupEvent) {}
}
