package dev.thornium.flatcore.registry.event

import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.machine.MachineDefinition
import dev.thornium.flatcore.registry.machine.FTMachines
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.eventbus.api.IEventBus

class MachineEventHandler : ModEventListener {
    override fun registerTo(modEventBus: IEventBus) {
        modEventBus.addGenericListener(MachineDefinition::class.java, ::onRegister)
    }

    private fun onRegister(event: GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition>) {
        FTMachines.init()
    }
}
