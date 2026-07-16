package dev.thornium.flatcore.registry

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet
import dev.thornium.flatcore.api.Initialized

object FTMaterialIconSet : Initialized {
    val ALL: MutableSet<MaterialIconSet> = mutableSetOf()

    val CONSTANTAN: MaterialIconSet = register(MaterialIconSet("constantan", MaterialIconSet.METALLIC))
    val PIG_IRON: MaterialIconSet = register(MaterialIconSet("pig_iron", MaterialIconSet.METALLIC))

    private fun register(iconSet: MaterialIconSet): MaterialIconSet {
        ALL.add(iconSet)
        return iconSet
    }
}
