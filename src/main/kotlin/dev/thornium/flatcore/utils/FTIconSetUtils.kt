package dev.thornium.flatcore.utils

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet
import dev.thornium.flatcore.registry.FTMaterialIconSet
import net.minecraft.resources.ResourceLocation

object FTIconSetUtils {
    @JvmStatic
    fun overridePipePath(original: String, material: Material): String =
        getOverrideIconSet(material)
            ?.let { iconSet -> replacePath(original, iconSet) }
            ?: original

    @JvmStatic
    fun overridePipeLocation(original: ResourceLocation, material: Material): ResourceLocation =
        getOverrideIconSet(material)
            ?.let { iconSet -> GTCEu.id(replacePath(original.path, iconSet)) }
            ?: original

    @JvmStatic
    fun getOverrideIconSet(material: Material): MaterialIconSet? =
        FTMaterialIconSet.ALL.firstOrNull { it.id == material.materialIconSet.id }

    private fun replacePath(original: String, iconSet: MaterialIconSet): String =
        original.replace("block/pipe", "block/material_sets/${iconSet.name}")
}
