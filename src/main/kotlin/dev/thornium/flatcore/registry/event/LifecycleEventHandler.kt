package dev.thornium.flatcore.registry.event

import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

/** Extension point for common/client setup. Both stages are currently unused. */
class LifecycleEventHandler : ModEventListener {
    override fun registerTo(modEventBus: IEventBus) {
        modEventBus.addListener(::onCommonSetup)
        modEventBus.addListener(::onClientSetup)
    }

    private fun onCommonSetup(event: FMLCommonSetupEvent) {}

    private fun onClientSetup(event: FMLClientSetupEvent) {}
}
