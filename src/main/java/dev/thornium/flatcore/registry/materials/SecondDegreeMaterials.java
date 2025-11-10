package dev.thornium.flatcore.registry.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import dev.thornium.flatcore.FlatCore;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static dev.thornium.flatcore.registry.FTMaterials.*;

public class SecondDegreeMaterials {
  public static void register() {
    StoneOreMass = new Material.Builder(FlatCore.id("stone_ore_mass"))
      .liquid(1200)
      .color(Stone.getMaterialRGB()).iconSet(ROUGH)
      .flags(DISABLE_DECOMPOSITION)
      .components(Stone, 4, Calcium, 1, Water, 1)
      .buildAndRegister();
  }
}
