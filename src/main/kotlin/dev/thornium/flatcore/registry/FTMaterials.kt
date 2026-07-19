package dev.thornium.flatcore.registry

import com.gregtechceu.gtceu.api.GTValues.*
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags
import com.gregtechceu.gtceu.api.data.chemical.material.properties.*
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.ring
import com.gregtechceu.gtceu.api.fluids.FluidBuilder
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys
import com.gregtechceu.gtceu.api.item.tool.GTToolType
import com.gregtechceu.gtceu.common.data.GTMaterials.*
import dev.thornium.flatcore.api.data.tag.FTTagPrefix.toolHeadMultiTool
import dev.thornium.flatcore.registry.materials.CompatMaterials
import dev.thornium.flatcore.registry.materials.FirstDegreeMaterials
import dev.thornium.flatcore.registry.materials.SecondDegreeMaterials

/**
 * material builder method order (to be consistent):
 *
 * ```
 * Material.Builder(FlatCore.id("material_id"))
 *   .ingot().fluid().ore() // types
 *   .color().iconSet() // appearance
 *   .flags() // special registration logic
 *   .element() / .components() // composition
 *   .toolStats()
 *   .oreByProducts() // additional properties
 *   ...
 *   .blastTemp()
 *   .buildAndRegister()
 * ```
 *
 * some defaults:
 * - iconSet = `DULL` (idk don't omit this one)
 * - color = `0xffffff` (use when custom texture)
 *
 * Single owner of the public Material references: the `materials` subpackage only
 * builds and returns Material instances, it never reaches back in to assign here.
 */
@Suppress("unused")
object FTMaterials {
    // compat materials
    val RedstoneGlowstoneMixture get() = CompatMaterials.RedstoneGlowstoneMixture

    // first degree materials
    val PigIron get() = FirstDegreeMaterials.PigIron

    val Azuron get() = FirstDegreeMaterials.Azuron
    val Ferrolite get() = FirstDegreeMaterials.Ferrolite
    val Constantan get() = FirstDegreeMaterials.Constantan
    val RedBrass get() = FirstDegreeMaterials.RedBrass
    val Vitreloy105 get() = FirstDegreeMaterials.Vitreloy105

    val Gallium3Oxide get() = FirstDegreeMaterials.Gallium3Oxide
    val GalliumNitrate get() = FirstDegreeMaterials.GalliumNitrate

    // second degree materials
    val StoneOreMass get() = SecondDegreeMaterials.StoneOreMass

    fun init() {
        val materialGroups = listOf(CompatMaterials, FirstDegreeMaterials, SecondDegreeMaterials)
        materialGroups.forEach { it.init() }

        toolHeadMultiTool.addSecondaryMaterial(
            MaterialStack(Steel, plate.materialAmount() * 2 + ring.materialAmount() * 2),
        )
    }

    fun modify() {
        Wood.addFlags(MaterialFlags.GENERATE_SPRING, MaterialFlags.GENERATE_SPRING_SMALL)
        Stone.addFlags(MaterialFlags.GENERATE_SPRING, MaterialFlags.GENERATE_GEAR)
        Carbon.addFlags(MaterialFlags.GENERATE_PLATE)
        Titanium.addFlags(MaterialFlags.GENERATE_FINE_WIRE)
        WroughtIron.addFlags(MaterialFlags.GENERATE_FRAME)

        if (Wood.hasProperty(PropertyKey.TOOL)) {
            Wood.properties.removeProperty(PropertyKey.TOOL)
        }

        Wood.setProperty(
            PropertyKey.TOOL,
            ToolProperty(
                1f,
                1f,
                128,
                1,
                arrayOf(GTToolType.SAW, GTToolType.FILE, GTToolType.WRENCH, GTToolType.SOFT_MALLET)
            ),
        )
        Stone.setProperty(
            PropertyKey.TOOL,
            ToolProperty(
                1f,
                1f,
                128,
                1,
                arrayOf(GTToolType.SAW, GTToolType.FILE, GTToolType.WRENCH, GTToolType.HARD_HAMMER, GTToolType.DRILL_LV)
            ),
        )
        TinAlloy.setProperty(PropertyKey.WIRE, WireProperties(V[ULV], 1, 3))
        Steel.getProperty(PropertyKey.BLAST).eUtOverride = VA[LV]

        Glowstone.setComponents(MaterialStack(Gold, 1), MaterialStack(TricalciumPhosphate, 1))
        Glowstone.setFormula("AuCa3(PO4)2")

        Zirconium.setProperty(PropertyKey.DUST, DustProperty(4, 0))
        Zirconium.setProperty(PropertyKey.INGOT, IngotProperty())
        Zirconium.setProperty(
            PropertyKey.BLAST,
            BlastProperty.Builder()
                .temp(2128, BlastProperty.GasTier.MID)
                .blastStats(VA[IV])
                .build(),
        )
        Zirconium.setProperty(
            PropertyKey.FLUID,
            FluidProperty(FluidStorageKeys.LIQUID, FluidBuilder().temperature(2128)),
        )

        for (flag in STD_METAL) {
            Zirconium.addFlags(flag)
        }
    }
}
