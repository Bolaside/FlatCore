package dev.thornium.flatcore.utils;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import dev.thornium.flatcore.registry.FTMaterialIconSet;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public final class FTIconSetUtils {
    public static String overridePipePath(String original, Material material) {
        return getOverrideIconSet(material)
                .map(iconSet -> replacePath(original, iconSet))
                .orElse(original);
    }

    public static ResourceLocation overridePipeLocation(ResourceLocation original, Material material) {
        return getOverrideIconSet(material)
                .map(iconSet -> GTCEu.id(replacePath(original.getPath(), iconSet)))
                .orElse(original);
    }

    public static Optional<MaterialIconSet> getOverrideIconSet(Material material) {
        MaterialIconSet iconSet = material.getMaterialIconSet();
        return FTMaterialIconSet.ALL.contains(iconSet) ? Optional.of(iconSet) : Optional.empty();
    }

    private static String replacePath(String original, MaterialIconSet iconSet) {
        return original.replace("block/pipe", "block/material_sets/" + iconSet.name);
    }
}
