package dev.thornium.flatcore.utils

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet
import dev.thornium.flatcore.registry.FTMaterialIconSet
import net.minecraft.resources.ResourceLocation
import java.util.*

object FTIconSetUtils {
    @JvmStatic
    fun overridePipePath(original: String, material: Material): String =
        getOverrideIconSet(material)
            .map { iconSet -> replacePath(original, iconSet) }
            .orElse(original)

    @JvmStatic
    fun overridePipeLocation(original: ResourceLocation, material: Material): ResourceLocation =
        getOverrideIconSet(material)
            .map { iconSet -> GTCEu.id(replacePath(original.path, iconSet)) }
            .orElse(original)

    @JvmStatic
    fun getOverrideIconSet(material: Material): Optional<MaterialIconSet> {
        val iconSet = material.materialIconSet
        return if (FTMaterialIconSet.ALL.contains(iconSet)) Optional.of(iconSet) else Optional.empty()
    }

    private fun replacePath(original: String, iconSet: MaterialIconSet): String =
        original.replace("block/pipe", "block/material_sets/${iconSet.name}")
}
