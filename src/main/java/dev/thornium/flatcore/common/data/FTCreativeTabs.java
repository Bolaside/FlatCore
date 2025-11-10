package dev.thornium.flatcore.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;

import net.minecraft.world.item.CreativeModeTab;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.thornium.flatcore.FlatCore;
import dev.thornium.flatcore.registry.FTMaterials;

import static dev.thornium.flatcore.FlatCore.REGISTRATE;

public class FTCreativeTabs {
  public static RegistryEntry<CreativeModeTab> FLATCORE = REGISTRATE.defaultCreativeTab(
    FlatCore.MOD_ID,
    builder -> builder
      .displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(FlatCore.MOD_ID, REGISTRATE))
      .title(REGISTRATE.addLang("itemGroup", FlatCore.id("creative_tab"), "FlatCore"))
      .icon(() -> ChemicalHelper.getIngot(FTMaterials.RedBrass, GTValues.M))
      .build())
    .register();

  public static void init() {}
}
