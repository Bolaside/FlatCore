package dev.thornium.flatcore.registry.event

import dev.thornium.flatcore.common.data.model.FTMaterialModelProvider
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.IEventBus

class DataGenEventHandler : ModEventListener {
    override fun registerTo(modEventBus: IEventBus) {
        modEventBus.addListener(::onGatherData)
    }

    private fun onGatherData(event: GatherDataEvent) {
        if (!event.includeClient()) return
        val generator = event.generator
        generator.addProvider(
            true,
            FTMaterialModelProvider(generator.packOutput, event.existingFileHelper),
        )
    }
}
