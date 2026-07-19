package dev.thornium.flatcore.utils

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet
import dev.thornium.flatcore.registry.FTMaterialIconSet
import net.minecraft.resources.ResourceLocation

object FTIconSetUtils {
    @JvmStatic
    fun overridePipePath(original: String, material: Material) =
        getOverrideIconSet(material)
            ?.let { iconSet -> replacePath(original, iconSet) }
            ?: original

    @JvmStatic
    fun overridePipeLocation(original: ResourceLocation, material: Material) =
        getOverrideIconSet(material)
            ?.let { iconSet -> GTCEu.id(replacePath(original.path, iconSet)) }
            ?: original

    @JvmStatic
    fun getOverrideIconSet(material: Material) =
        FTMaterialIconSet.ALL.firstOrNull { it.name == material.materialIconSet.name }

    private fun replacePath(original: String, iconSet: MaterialIconSet) =
        original.replace("block/pipe", "block/material_sets/${iconSet.name}")
}
