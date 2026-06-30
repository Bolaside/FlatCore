package dev.thornium.flatcore.registry.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlag;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty.GasTier;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.common.data.GTElements;
import dev.thornium.flatcore.FlatCore;
import dev.thornium.flatcore.api.item.tool.FTToolType;

import java.util.ArrayList;
import java.util.List;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.METALLIC;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static dev.thornium.flatcore.registry.FTMaterialIconSet.CONSTANTAN;
import static dev.thornium.flatcore.registry.FTMaterialIconSet.PIG_IRON;
import static dev.thornium.flatcore.registry.FTMaterials.*;

public class FirstDegreeMaterials {
    public static final List<MaterialFlag> FULL_METAL = new ArrayList<>();

    static {
        FULL_METAL.addAll(EXT2_METAL);
        FULL_METAL.add(GENERATE_FOIL);
        FULL_METAL.add(GENERATE_FRAME);
        FULL_METAL.add(GENERATE_GEAR);
        FULL_METAL.add(GENERATE_SMALL_GEAR);
        FULL_METAL.add(GENERATE_RING);
        FULL_METAL.add(GENERATE_ROTOR);
        FULL_METAL.add(GENERATE_ROUND);
        FULL_METAL.add(GENERATE_SPRING);
        FULL_METAL.add(GENERATE_SPRING_SMALL);
    }

    public static void register() {
        RedBrass = new Material.Builder(FlatCore.id("red_brass"))
                .dust().ingot().liquid(1025)
                .color(0xC45649).iconSet(METALLIC)
                .flags(GENERATE_PLATE)
                .components(Copper, 6, Tin, 2, Zinc, 1)
                .cableProperties(V[ULV], 1, 0, true)
                .buildAndRegister();

        Azuron = new Material.Builder(FlatCore.id("azuron"))
                .dust().ingot().liquid(1700)
                .color(0x4A6B7F).iconSet(METALLIC)
                .flags(GENERATE_PLATE, DECOMPOSITION_BY_CENTRIFUGING)
                .components(Lead, 2, Copper, 2, Cobalt, 1, Silicon, 2, Oxygen, 9)
                .cableProperties(V[LV], 4, 0, true)
                .blastTemp(1700, GasTier.LOW)
                .buildAndRegister();

        Constantan = new Material.Builder(FlatCore.id("constantan"))
                .dust().ingot().liquid(new FluidBuilder().customStill().temperature(1455))
                .color(0xFFFFFF).iconSet(CONSTANTAN)
                .appendFlags(FULL_METAL, DECOMPOSITION_BY_CENTRIFUGING)
                .components(Iron, 4, Manganese, 1, Copper, 1)
                .blastTemp(1553, GasTier.LOW)
                .buildAndRegister();

        PigIron = new Material.Builder(FlatCore.id("pig_iron"))
                .dust().ingot().liquid(new FluidBuilder().customStill().temperature(1600))
                .color(0xFFFFFF).iconSet(PIG_IRON)
                .appendFlags(FULL_METAL, GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
                .element(GTElements.Fe)
                .itemPipeProperties(768, 3)
                .buildAndRegister();

        Vitreloy105 = new Material.Builder(FlatCore.id("vitreloy_105"))
                .dust().ingot(4).fluid()
                .color(0xBE609D).iconSet(METALLIC)
                .appendFlags(EXT2_METAL, GENERATE_DENSE, GENERATE_ROUND)
                .toolStats(ToolProperty.Builder.of(16.0F, 4.0F, 1920, 4, FTToolType.MULTI_TOOL).build())
                .components(Zirconium, 8, Titanium, 1, Copper, 3, Nickel, 3, Aluminium, 2)
                .blast(b -> b.temp(3722, GasTier.MID).blastStats(VA[IV], 20 * 50))
                .buildAndRegister();

        GalliumNitrate = new Material.Builder(FlatCore.id("gallium_nitrate"))
                .dust()
                .color(0x4A878C).secondaryColor(0x236167)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .components(Gallium, 1, Nitrogen, 3, Oxygen, 9).formula("Ga(NO3)3")
                .buildAndRegister();

        Gallium3Oxide = new Material.Builder(FlatCore.id("gallium_iii_oxide"))
                .langValue("Gallium III Oxide")
                .dust()
                .color(0x7A84CA).secondaryColor(0x13132E).iconSet(METALLIC)
                .flags(DECOMPOSITION_BY_ELECTROLYZING)
                .components(Gallium, 2, Oxygen, 3)
                .buildAndRegister();
    }
}
