package dev.thornium.flatcore.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;

import java.util.HashSet;
import java.util.Set;

public class FTMaterialIconSet {
    public static final Set<MaterialIconSet> ALL = new HashSet<>();

    public static final MaterialIconSet CONSTANTAN = register(new MaterialIconSet("constantan", MaterialIconSet.METALLIC));
    public static final MaterialIconSet PIG_IRON = register(new MaterialIconSet("pig_iron", MaterialIconSet.METALLIC));

    private static MaterialIconSet register(MaterialIconSet iconSet) {
        ALL.add(iconSet);
        return iconSet;
    }

    public static void init() {}
}
