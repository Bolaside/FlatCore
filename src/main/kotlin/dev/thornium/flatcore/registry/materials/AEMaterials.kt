package dev.thornium.flatcore.registry.materials

import appeng.core.definitions.AEItems
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.block
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.dustSmall
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.dustTiny
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.gem
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.gemExquisite
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.gemFlawless
import com.gregtechceu.gtceu.common.data.GTMaterials.*
import dev.thornium.flatcore.FlatCore
import dev.thornium.flatcore.api.Initialized

object AEMaterials : Initialized {
    val ChargedCertusQuartz: Material = Material.Builder(FlatCore.id("charged_certus_quartz"))
        .gem(1)
        .color(0x36D0D6).iconSet(MaterialIconSet.CERTUS)
        .flags(NO_SMELTING, DISABLE_DECOMPOSITION)
        .components(Silicon, 1, Oxygen, 2)
        .buildAndRegister()

    val Fluix: Material = Material.Builder(FlatCore.id("fluix"))
        .dust().gem(1)
        .color(0x9C66E3).iconSet(MaterialIconSet.QUARTZ)
        .flags(CRYSTALLIZABLE, GENERATE_PLATE, NO_SMELTING, DISABLE_DECOMPOSITION)
        .components(CertusQuartz, 1, Redstone, 1, NetherQuartz, 1)
        .buildAndRegister()

    val SkyStone: Material = Material.Builder(FlatCore.id("sky_stone"))
        .dust(3)
        .color(0x957E7E).iconSet(MaterialIconSet.ROUGH)
        .flags(NO_SMELTING, MORTAR_GRINDABLE, DISABLE_DECOMPOSITION)
        .components(Obsidian, 4, CertusQuartz, 1)
        .buildAndRegister()

    override fun init() {
        gem.setIgnored(ChargedCertusQuartz, AEItems.CERTUS_QUARTZ_CRYSTAL_CHARGED)
        dust.setIgnored(ChargedCertusQuartz)
        dustSmall.setIgnored(ChargedCertusQuartz)
        dustTiny.setIgnored(ChargedCertusQuartz)
        gemFlawless.setIgnored(ChargedCertusQuartz)
        gemExquisite.setIgnored(ChargedCertusQuartz)
        block.setIgnored(ChargedCertusQuartz)

        gem.setIgnored(CertusQuartz, AEItems.CERTUS_QUARTZ_CRYSTAL)
        dust.setIgnored(CertusQuartz, AEItems.CERTUS_QUARTZ_DUST)

        gem.setIgnored(Fluix, AEItems.FLUIX_CRYSTAL)
        dust.setIgnored(Fluix, AEItems.FLUIX_DUST)

        dust.setIgnored(SkyStone, AEItems.SKY_DUST)
    }
}
