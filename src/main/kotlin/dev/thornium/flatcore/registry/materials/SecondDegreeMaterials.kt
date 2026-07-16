package dev.thornium.flatcore.registry.materials

import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.DISABLE_DECOMPOSITION
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.ROUGH
import com.gregtechceu.gtceu.common.data.GTMaterials.*
import dev.thornium.flatcore.FlatCore
import dev.thornium.flatcore.api.Initialized

object SecondDegreeMaterials : Initialized {
    val StoneOreMass: Material = Material.Builder(FlatCore.id("stone_ore_mass"))
        .liquid(1200)
        .color(Stone.materialRGB).iconSet(ROUGH)
        .flags(DISABLE_DECOMPOSITION)
        .components(Stone, 4, Calcium, 1, Water, 1)
        .buildAndRegister()
}
