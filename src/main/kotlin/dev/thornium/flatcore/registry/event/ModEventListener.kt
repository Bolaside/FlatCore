package dev.thornium.flatcore.registry.event

import net.minecraftforge.eventbus.api.IEventBus

fun interface ModEventListener {
    fun registerTo(modEventBus: IEventBus)
}
