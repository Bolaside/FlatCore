package dev.thornium.flatcore.registry.materials

import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.DISABLE_DECOMPOSITION
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.ROUGH
import com.gregtechceu.gtceu.common.data.GTMaterials.Calcium
import com.gregtechceu.gtceu.common.data.GTMaterials.Stone
import com.gregtechceu.gtceu.common.data.GTMaterials.Water
import dev.thornium.flatcore.FlatCore

/** Second-degree materials: composed from first-degree and vanilla/GT materials. */
object SecondDegreeMaterials {
    fun register(): Material =
        Material.Builder(FlatCore.id("stone_ore_mass"))
            .liquid(1200)
            .color(Stone.materialRGB).iconSet(ROUGH)
            .flags(DISABLE_DECOMPOSITION)
            .components(Stone, 4, Calcium, 1, Water, 1)
            .buildAndRegister()
}
