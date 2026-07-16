package dev.thornium.flatcore.common.data

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper
import com.gregtechceu.gtceu.api.data.tag.TagPrefix
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs
import com.tterrag.registrate.util.entry.RegistryEntry
import dev.thornium.flatcore.FlatCore
import dev.thornium.flatcore.FlatCore.Companion.REGISTRATE
import dev.thornium.flatcore.api.Initialized
import dev.thornium.flatcore.registry.FTMaterials
import net.minecraft.world.item.CreativeModeTab

object FTCreativeTabs : Initialized {
    val FLATCORE: RegistryEntry<CreativeModeTab> = REGISTRATE.defaultCreativeTab(
        FlatCore.MOD_ID,
    ) { builder ->
        builder.displayItems(GTCreativeModeTabs.RegistrateDisplayItemsGenerator(FlatCore.MOD_ID, REGISTRATE))
            .icon { ChemicalHelper.get(TagPrefix.block, FTMaterials.RedBrass) }
            .title(REGISTRATE.addLang("itemGroup", FlatCore.id(FlatCore.MOD_ID), FlatCore.NAME))
            .build()
    }.register()
}
