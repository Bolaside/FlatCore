package dev.thornium.flatcore.registry.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import dev.thornium.flatcore.FlatCore;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static dev.thornium.flatcore.registry.FTMaterials.*;

public class CompatMaterials {
  public static void register() {
    RedstoneGlowstoneMixture = new Material.Builder(FlatCore.id("redstone_glowstone_mixture"))
      .liquid(850)
      .color(0xD9783F).iconSet(SHINY)
      .flags(DISABLE_DECOMPOSITION)
      .components(Redstone, 1, Glowstone, 1)
      .buildAndRegister();
  }
}
