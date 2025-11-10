package dev.thornium.flatcore.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;

import dev.thornium.flatcore.registry.materials.CompatMaterials;
import dev.thornium.flatcore.registry.materials.FirstDegreeMaterials;
import dev.thornium.flatcore.registry.materials.SecondDegreeMaterials;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

/**
 * material builder method order (to be consistent):
 * 
 * <pre>
 * {@code
 * new Material.Builder(FlatCore.id("material_id"))
 *   .ingot().fluid().ore() // types
 *   .color().iconSet() // appearance
 *   .flags() // special registration logic
 *   .element() / .components() // composition
 *   .toolStats()
 *   .oreByProducts() // additional properties
 *   ...
 *   .blastTemp()
 *   .buildAndRegister();
 * }
 * </pre>
 * 
 * some defaults:
 * <ul>
 * <li>iconSet = `DULL` (idk don't omit this one)</li>
 * <li>color = `0xffffff` (use when custom texture)</li>
 * </ul>
 */
public final class FTMaterials {
  private FTMaterials() {}

  public static void init() {
    CompatMaterials.register();
    FirstDegreeMaterials.register();
    SecondDegreeMaterials.register();
  }

  public static void modify() {
    Glowstone.setComponents(new MaterialStack(Gold, M), new MaterialStack(TricalciumPhosphate, M));
    Glowstone.setFormula("AuCa3(PO4)2");
  }

  // compat materials
  public static Material RedstoneGlowstoneMixture;

  // first degree materials
  public static Material RedBrass;
  public static Material Azuron;

  // second degree materials
  public static Material StoneOreMass;
}
