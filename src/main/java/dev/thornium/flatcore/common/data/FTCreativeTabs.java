package dev.thornium.flatcore.common.data;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.thornium.flatcore.FlatCore;
import dev.thornium.flatcore.registry.FTMaterials;
import net.minecraft.world.item.CreativeModeTab;

import static dev.thornium.flatcore.FlatCore.REGISTRATE;

public class FTCreativeTabs {
    public static RegistryEntry<CreativeModeTab> FLATCORE = REGISTRATE.defaultCreativeTab(FlatCore.MOD_ID,
                    builder -> builder.displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(FlatCore.MOD_ID, REGISTRATE))
                            .icon(() -> ChemicalHelper.get(TagPrefix.block, FTMaterials.RedBrass))
                            .title(REGISTRATE.addLang("itemGroup", FlatCore.id(FlatCore.MOD_ID), FlatCore.NAME))
                            .build())
            .register();

    public static void init() {}
}
