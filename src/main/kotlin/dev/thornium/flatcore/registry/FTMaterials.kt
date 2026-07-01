package dev.thornium.flatcore.registry

import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty
import com.gregtechceu.gtceu.api.data.chemical.material.properties.DustProperty
import com.gregtechceu.gtceu.api.data.chemical.material.properties.FluidProperty
import com.gregtechceu.gtceu.api.data.chemical.material.properties.IngotProperty
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty
import com.gregtechceu.gtceu.api.data.chemical.material.properties.WireProperties
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.plate
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.ring
import com.gregtechceu.gtceu.api.fluids.FluidBuilder
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys
import com.gregtechceu.gtceu.api.item.tool.GTToolType
import com.gregtechceu.gtceu.api.GTValues.IV
import com.gregtechceu.gtceu.api.GTValues.LV
import com.gregtechceu.gtceu.api.GTValues.M
import com.gregtechceu.gtceu.api.GTValues.ULV
import com.gregtechceu.gtceu.api.GTValues.V
import com.gregtechceu.gtceu.api.GTValues.VA
import com.gregtechceu.gtceu.common.data.GTMaterials.Carbon
import com.gregtechceu.gtceu.common.data.GTMaterials.Glowstone
import com.gregtechceu.gtceu.common.data.GTMaterials.Gold
import com.gregtechceu.gtceu.common.data.GTMaterials.STD_METAL
import com.gregtechceu.gtceu.common.data.GTMaterials.Steel
import com.gregtechceu.gtceu.common.data.GTMaterials.Stone
import com.gregtechceu.gtceu.common.data.GTMaterials.TinAlloy
import com.gregtechceu.gtceu.common.data.GTMaterials.Titanium
import com.gregtechceu.gtceu.common.data.GTMaterials.TricalciumPhosphate
import com.gregtechceu.gtceu.common.data.GTMaterials.Wood
import com.gregtechceu.gtceu.common.data.GTMaterials.WroughtIron
import com.gregtechceu.gtceu.common.data.GTMaterials.Zirconium
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
object FTMaterials {
    // compat materials
    lateinit var RedstoneGlowstoneMixture: Material
        private set

    // first degree materials
    lateinit var RedBrass: Material
        private set
    lateinit var Azuron: Material
        private set
    lateinit var Constantan: Material
        private set
    lateinit var PigIron: Material
        private set
    lateinit var Vitreloy105: Material
        private set
    lateinit var GalliumNitrate: Material
        private set
    lateinit var Gallium3Oxide: Material
        private set

    // second degree materials
    lateinit var StoneOreMass: Material
        private set

    fun init() {
        RedstoneGlowstoneMixture = CompatMaterials.register()

        val firstDegree = FirstDegreeMaterials.register()
        RedBrass = firstDegree.redBrass
        Azuron = firstDegree.azuron
        Constantan = firstDegree.constantan
        PigIron = firstDegree.pigIron
        Vitreloy105 = firstDegree.vitreloy105
        GalliumNitrate = firstDegree.galliumNitrate
        Gallium3Oxide = firstDegree.gallium3Oxide

        StoneOreMass = SecondDegreeMaterials.register()

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
            ToolProperty(1f, 1f, 128, 1, arrayOf(GTToolType.SAW, GTToolType.FILE, GTToolType.WRENCH, GTToolType.SOFT_MALLET)),
        )
        Stone.setProperty(
            PropertyKey.TOOL,
            ToolProperty(1f, 1f, 128, 1, arrayOf(GTToolType.SAW, GTToolType.FILE, GTToolType.WRENCH, GTToolType.HARD_HAMMER, GTToolType.DRILL_LV)),
        )
        TinAlloy.setProperty(PropertyKey.WIRE, WireProperties(V[ULV], 1, 3))
        Steel.getProperty(PropertyKey.BLAST).setEUtOverride(VA[LV])

        Glowstone.setComponents(MaterialStack(Gold, M), MaterialStack(TricalciumPhosphate, M))
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
