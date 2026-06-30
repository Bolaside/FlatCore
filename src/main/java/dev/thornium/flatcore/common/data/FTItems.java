package dev.thornium.flatcore.common.data;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static dev.thornium.flatcore.FlatCore.REGISTRATE;

public class FTItems {
    static {
        REGISTRATE.creativeModeTab(() -> FTCreativeTabs.FLATCORE);
    }

    public static ItemEntry<Item> GALLIUM_BOULE = REGISTRATE.item("gallium_iii_oxide_boule", Item::new)
            .lang("Gallium III Oxide Boule")
            .register();
    public static ItemEntry<Item> GALLIUM_WAFER = REGISTRATE.item("gallium_iii_oxide_wafer", Item::new)
            .lang("Gallium III Oxide Wafer")
            .register();

    public static void init() {}
}
