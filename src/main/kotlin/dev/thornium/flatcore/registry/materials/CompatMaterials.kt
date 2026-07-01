package dev.thornium.flatcore.registry.materials

import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.DISABLE_DECOMPOSITION
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.SHINY
import com.gregtechceu.gtceu.common.data.GTMaterials.Glowstone
import com.gregtechceu.gtceu.common.data.GTMaterials.Redstone
import dev.thornium.flatcore.FlatCore

/** Materials bridging FlatCore content with other mods' materials. */
object CompatMaterials {
    fun register(): Material =
        Material.Builder(FlatCore.id("redstone_glowstone_mixture"))
            .liquid(850)
            .color(0xD9783F).iconSet(SHINY)
            .flags(DISABLE_DECOMPOSITION)
            .components(Redstone, 1, Glowstone, 1)
            .buildAndRegister()
}
