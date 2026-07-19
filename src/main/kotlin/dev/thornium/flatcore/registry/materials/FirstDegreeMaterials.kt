package dev.thornium.flatcore.registry.materials

import com.gregtechceu.gtceu.api.GTValues.*
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.METALLIC
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty.GasTier
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty
import com.gregtechceu.gtceu.api.fluids.FluidBuilder
import com.gregtechceu.gtceu.common.data.GTElements
import com.gregtechceu.gtceu.common.data.GTMaterials.*
import dev.thornium.flatcore.FlatCore
import dev.thornium.flatcore.api.Initialized
import dev.thornium.flatcore.api.item.tool.FTToolType
import dev.thornium.flatcore.registry.FTMaterialIconSet.CONSTANTAN
import dev.thornium.flatcore.registry.FTMaterialIconSet.PIG_IRON

object FirstDegreeMaterials : Initialized {
    private val FULL_METAL = buildList {
        addAll(EXT2_METAL)
        add(GENERATE_FOIL)
        add(GENERATE_FRAME)
        add(GENERATE_GEAR)
        add(GENERATE_SMALL_GEAR)
        add(GENERATE_RING)
        add(GENERATE_ROTOR)
        add(GENERATE_ROUND)
        add(GENERATE_SPRING)
        add(GENERATE_SPRING_SMALL)
    }

    val RedBrass: Material = Material.Builder(FlatCore.id("red_brass"))
        .dust().ingot().liquid(1025)
        .color(0xC45649).iconSet(METALLIC)
        .flags(GENERATE_PLATE)
        .components(Copper, 6, Tin, 2, Zinc, 1)
        .cableProperties(V[ULV], 1, 0, true)
        .buildAndRegister()

    val Azuron: Material = Material.Builder(FlatCore.id("azuron"))
        .dust().ingot().liquid(1700)
        .color(0x4A6B7F).iconSet(METALLIC)
        .flags(GENERATE_PLATE, DECOMPOSITION_BY_CENTRIFUGING)
        .components(Lead, 2, Copper, 2, Cobalt, 1, Silicon, 2, Oxygen, 9)
        .cableProperties(V[LV], 4, 0, true)
        .blastTemp(1700, GasTier.LOW)
        .buildAndRegister()

    val Constantan: Material = Material.Builder(FlatCore.id("constantan"))
        .dust().ingot().liquid(FluidBuilder().customStill().temperature(1455))
        .color(0xFFFFFF).iconSet(CONSTANTAN)
        .appendFlags(FULL_METAL, DECOMPOSITION_BY_CENTRIFUGING)
        .components(Iron, 4, Manganese, 1, Copper, 1)
        .blastTemp(1553, GasTier.LOW)
        .buildAndRegister()

    val PigIron: Material = Material.Builder(FlatCore.id("pig_iron"))
        .dust().ingot().liquid(FluidBuilder().customStill().temperature(1600))
        .color(0xFFFFFF).iconSet(PIG_IRON)
        .appendFlags(FULL_METAL, GENERATE_FINE_WIRE, DISABLE_DECOMPOSITION)
        .element(GTElements.Fe)
        .itemPipeProperties(768, 3F)
        .buildAndRegister()

    val Vitreloy105: Material = Material.Builder(FlatCore.id("vitreloy_105"))
        .dust().ingot(4).fluid()
        .color(0xBE609D).iconSet(METALLIC)
        .appendFlags(EXT2_METAL, GENERATE_DENSE, GENERATE_ROUND)
        .toolStats(ToolProperty.Builder.of(16.0f, 4.0f, 1920, 4, FTToolType.MULTI_TOOL).build())
        .components(Zirconium, 8, Titanium, 1, Copper, 3, Nickel, 3, Aluminium, 2)
        .blast { b -> b.temp(3722, GasTier.MID).blastStats(VA[IV], 20 * 50) }
        .buildAndRegister()

    val GalliumNitrate: Material = Material.Builder(FlatCore.id("gallium_nitrate"))
        .dust()
        .color(0x4A878C).secondaryColor(0x236167)
        .flags(DECOMPOSITION_BY_ELECTROLYZING)
        .components(Gallium, 1, Nitrogen, 3, Oxygen, 9).formula("Ga(NO3)3")
        .buildAndRegister()

    val Gallium3Oxide: Material = Material.Builder(FlatCore.id("gallium_iii_oxide"))
        .langValue("Gallium III Oxide")
        .dust()
        .color(0x7A84CA).secondaryColor(0x13132E).iconSet(METALLIC)
        .flags(DECOMPOSITION_BY_ELECTROLYZING)
        .components(Gallium, 2, Oxygen, 3)
        .buildAndRegister()
}
