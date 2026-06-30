package dev.thornium.flatcore.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlag;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.*;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import dev.thornium.flatcore.registry.materials.CompatMaterials;
import dev.thornium.flatcore.registry.materials.FirstDegreeMaterials;
import dev.thornium.flatcore.registry.materials.SecondDegreeMaterials;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.ring;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static dev.thornium.flatcore.api.data.tag.FTTagPrefix.toolHeadMultiTool;

/**
 * material builder method order (to be consistent):
 *
 * <pre>
 * {@code
 * new Material.Builder(FlatCore.id("material_id"))
 *   .ingot().fluid().ore() // types
 *   .color().iconSet() // appearance
 *   .flags() // special registration logic
 *   .element() / .components() // composition
 *   .toolStats()
 *   .oreByProducts() // additional properties
 *   ...
 *   .blastTemp()
 *   .buildAndRegister();
 * }
 * </pre>
 * <p>
 * some defaults:
 * <ul>
 * <li>iconSet = `DULL` (idk don't omit this one)</li>
 * <li>color = `0xffffff` (use when custom texture)</li>
 * </ul>
 */
public final class FTMaterials {
    private FTMaterials() {}

    public static void init() {
        CompatMaterials.register();
        FirstDegreeMaterials.register();
        SecondDegreeMaterials.register();

        toolHeadMultiTool.addSecondaryMaterial(new MaterialStack(Steel,
                plate.materialAmount() * 2 + ring.materialAmount() * 2));
    }

    public static void modify() {
        Wood.addFlags(MaterialFlags.GENERATE_SPRING, MaterialFlags.GENERATE_SPRING_SMALL);
        Stone.addFlags(MaterialFlags.GENERATE_SPRING, MaterialFlags.GENERATE_GEAR);
        Carbon.addFlags(MaterialFlags.GENERATE_PLATE);
        Titanium.addFlags(MaterialFlags.GENERATE_FINE_WIRE);
        WroughtIron.addFlags(MaterialFlags.GENERATE_FRAME);

        if (Wood.hasProperty(PropertyKey.TOOL)) {
            Wood.getProperties().removeProperty(PropertyKey.TOOL);
        }

        Wood.setProperty(PropertyKey.TOOL, new ToolProperty(1f, 1f, 128, 1, new GTToolType[]{GTToolType.SAW,
                GTToolType.FILE, GTToolType.WRENCH, GTToolType.SOFT_MALLET}));
        Stone.setProperty(PropertyKey.TOOL, new ToolProperty(1f, 1f, 128, 1, new GTToolType[]{GTToolType.SAW,
                GTToolType.FILE, GTToolType.WRENCH, GTToolType.HARD_HAMMER, GTToolType.DRILL_LV}));
        TinAlloy.setProperty(PropertyKey.WIRE, new WireProperties(V[ULV], 1, 3));
        Steel.getProperty(PropertyKey.BLAST).setEUtOverride(VA[LV]);

        Glowstone.setComponents(new MaterialStack(Gold, M), new MaterialStack(TricalciumPhosphate, M));
        Glowstone.setFormula("AuCa3(PO4)2");

        Zirconium.setProperty(PropertyKey.DUST, new DustProperty(4, 0));
        Zirconium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Zirconium.setProperty(PropertyKey.BLAST, new BlastProperty.Builder()
                .temp(2128, BlastProperty.GasTier.MID)
                .blastStats(VA[IV])
                .build());
        Zirconium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()
                .temperature(2128)));

        for (MaterialFlag flag : STD_METAL) {
            Zirconium.addFlags(flag);
        }
    }

    // compat materials
    public static Material RedstoneGlowstoneMixture;

    // first degree materials
    public static Material RedBrass;
    public static Material Azuron;
    public static Material Constantan;
    public static Material PigIron;
    public static Material Vitreloy105;

    public static Material GalliumNitrate;
    public static Material Gallium3Oxide;

    // second degree materials
    public static Material StoneOreMass;
}
