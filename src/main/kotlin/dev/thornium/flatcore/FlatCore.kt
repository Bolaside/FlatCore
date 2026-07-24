package dev.thornium.flatcore

import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate
import dev.thornium.flatcore.registry.StaticContentInitializer
import dev.thornium.flatcore.registry.event.*
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(FlatCore.MOD_ID)
class FlatCore {
    init {
        @Suppress("removal")
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus

        StaticContentInitializer.initialize()

        val eventListeners: List<ModEventListener> = listOf(
            LifecycleEventHandler(),
            MaterialEventHandler(),
            RecipeTypeEventHandler(),
            MachineEventHandler(),
            SoundEventHandler(),
            DataGenEventHandler(),
        )
        eventListeners.forEach { it.registerTo(modEventBus) }

        // most other events are fired on Forge's bus, not the mod bus. registering
        // this instance lets `@SubscribeEvent` listeners work too
        MinecraftForge.EVENT_BUS.register(this)

        REGISTRATE.registerRegistrate()
    }

    companion object {
        const val MOD_ID = "flatcore"
        const val NAME = "FlatCore"
        val LOGGER: Logger = LogManager.getLogger()
        val REGISTRATE: GTRegistrate = GTRegistrate.create(MOD_ID)

        fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
    }
}
