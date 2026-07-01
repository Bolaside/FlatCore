package dev.thornium.flatcore.registry.event

import net.minecraftforge.eventbus.api.IEventBus

/**
 * A self-contained unit of mod-bus registration. Each implementation owns one
 * registration concern (materials, machines, sounds, ...) and attaches its own
 * listeners; FlatCore only needs to know how to iterate this list, so adding a
 * new concern never requires editing the mod's entry point.
 */
fun interface ModEventListener {
    fun registerTo(modEventBus: IEventBus)
}
