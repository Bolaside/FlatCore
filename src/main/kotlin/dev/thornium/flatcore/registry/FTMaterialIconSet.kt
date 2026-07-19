package dev.thornium.flatcore.registry

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet
import dev.thornium.flatcore.api.Initialized

object FTMaterialIconSet : Initialized {
    val ALL = mutableSetOf<MaterialIconSet>()

    val CONSTANTAN = register(MaterialIconSet("constantan", MaterialIconSet.METALLIC))
    val PIG_IRON = register(MaterialIconSet("pig_iron", MaterialIconSet.METALLIC))

    private fun register(iconSet: MaterialIconSet): MaterialIconSet {
        ALL.add(iconSet)
        return iconSet
    }
}
