package dev.thornium.flatcore.registry.event

import com.gregtechceu.gtceu.api.GTCEuAPI
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent
import dev.thornium.flatcore.FlatCore
import dev.thornium.flatcore.registry.FTMaterialIconSet
import dev.thornium.flatcore.registry.FTMaterials
import net.minecraftforge.eventbus.api.IEventBus

/**
 * Owns the full material lifecycle: registry creation, registration of FlatCore's
 * own materials, and post-registration modification of GregTech's materials.
 * Kept as one class because GT's material events are strictly ordered and the
 * three stages share no logic with any other registration concern.
 */
class MaterialEventHandler : ModEventListener {
    override fun registerTo(modEventBus: IEventBus) {
        modEventBus.addListener(::onRegistryCreation)
        modEventBus.addListener(::onMaterialRegistration)
        modEventBus.addListener(::onMaterialModification)
    }

    private fun onRegistryCreation(event: MaterialRegistryEvent) {
        GTCEuAPI.materialManager.createRegistry(FlatCore.MOD_ID)
    }

    private fun onMaterialRegistration(event: MaterialEvent) {
        FTMaterialIconSet.init()
        FTMaterials.init()
    }

    private fun onMaterialModification(event: PostMaterialEvent) {
        FTMaterials.modify()
    }
}
