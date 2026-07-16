package dev.thornium.flatcore.common.data

import com.tterrag.registrate.util.entry.ItemEntry
import dev.thornium.flatcore.FlatCore.Companion.REGISTRATE
import dev.thornium.flatcore.api.Initialized
import net.minecraft.world.item.Item

object FTItems : Initialized {
    init {
        REGISTRATE.creativeModeTab { FTCreativeTabs.FLATCORE }
    }

    val GALLIUM_BOULE: ItemEntry<Item> = REGISTRATE.item("gallium_iii_oxide_boule", ::Item)
        .lang("Gallium III Oxide Boule")
        .register()

    val GALLIUM_WAFER: ItemEntry<Item> = REGISTRATE.item("gallium_iii_oxide_wafer", ::Item)
        .lang("Gallium III Oxide Wafer")
        .register()
}
