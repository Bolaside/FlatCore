package dev.thornium.flatcore.registry.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty.GasTier;

import dev.thornium.flatcore.FlatCore;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static dev.thornium.flatcore.registry.FTMaterials.*;

public class FirstDegreeMaterials {
  public static void register() {
    RedBrass = new Material.Builder(FlatCore.id("red_brass"))
      .dust().ingot().liquid(1025)
      .color(0xC45649).iconSet(METALLIC)
      .flags(GENERATE_PLATE)
      .components(Copper, 6, Tin, 2, Zinc, 1)
      .cableProperties(V[ULV], 1, 0, true)
      .buildAndRegister();

    Azuron = new Material.Builder(FlatCore.id("azuron"))
      .dust().ingot().liquid(1700)
      .color(0x4A6B7F).iconSet(METALLIC)
      .flags(GENERATE_PLATE, DECOMPOSITION_BY_CENTRIFUGING)
      .components(Lead, 2, Copper, 2, Cobalt, 1, Silicon, 2, Oxygen, 9)
      .cableProperties(V[LV], 4, 0, true)
      .blastTemp(1700, GasTier.LOW)
      .buildAndRegister();
  }
}
