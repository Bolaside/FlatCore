package dev.thornium.flatcore.common.data.recipe.processing_lines;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.ore;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.DISTILLATION_RECIPES;
import static dev.thornium.flatcore.gtbridge.FTRecipeTypes.STONE_OREIFIER_RECIPES;
import static dev.thornium.flatcore.registry.FTMaterials.StoneOreMass;

public class StoneOreMassRecipes {
  private StoneOreMassRecipes() {}

  private static final OreifierVein[] oreList = {
    new OreifierVein("iron", LV, new OreDrop[] {
      new OreDrop(Magnetite, 6000),
      new OreDrop(VanadiumMagnetite, 4500),
      new OreDrop(Chromite, 3000),
      new OreDrop(Lapis, 2000),
    }),
    new OreifierVein("copper", LV, new OreDrop[] {
      new OreDrop(Tetrahedrite, 5000),
      new OreDrop(Copper, 3500),
      new OreDrop(Stibnite, 3000),
      new OreDrop(Chalcocite, 1000),
    }),
    new OreifierVein("carbon", LV, new OreDrop[] {
      new OreDrop(Coal, 6500),
      new OreDrop(Graphite, 4500),
      new OreDrop(Diamond, 2000),
    }),
    new OreifierVein("silica", MV, new OreDrop[] {
      new OreDrop(NetherQuartz, 6500),
      new OreDrop(Quartzite, 5500),
      new OreDrop(CertusQuartz, 3500),
      new OreDrop(Barite, 1000),
    }),
    new OreifierVein("nickel", MV, new OreDrop[] {
      new OreDrop(Nickel, 5000),
      new OreDrop(Garnierite, 4500),
      new OreDrop(Cobaltite, 3000),
      new OreDrop(Pentlandite, 1000),
    }),
    new OreifierVein("salt", MV, new OreDrop[] {
      new OreDrop(RockSalt, 7500),
      new OreDrop(Salt, 2500),
      new OreDrop(Lepidolite, 2500),
      new OreDrop(Spodumene, 1500),
    }),
    new OreifierVein("manganese", HV, new OreDrop[] {
      new OreDrop(Grossular, 8000),
      new OreDrop(Spessartine, 6000),
      new OreDrop(Pyrolusite, 5500),
      new OreDrop(Tantalite, 1000),
    })
  };

  private record OreDrop(Material material, int chance) {}

  private record OreifierVein(String type, int voltage, OreDrop[] oreDrops) {}

  public static void init(Consumer<FinishedRecipe> provider) {
    DISTILLATION_RECIPES.recipeBuilder("distill_stone_ore_mass")
      .inputFluids(StoneOreMass.getFluid(1000))
      .outputFluids(Lava.getFluid(1000))
      .duration(20 * 10).EUt(VA[MV]).save(provider);

    for (int i = 0; i < oreList.length; i++) {
      OreifierVein vein = oreList[i];
      long totalMaterialMass = Arrays.stream(vein.oreDrops).reduce(
        0L, (total, drop) -> total + drop.material.getMass(), Long::sum);
      int stoneOreMassAmount = 100 * (int) Math.pow(2, vein.voltage - 1);

      var recipeBuilder = STONE_OREIFIER_RECIPES.recipeBuilder(String.format("%s_ores", vein.type))
        .inputFluids(StoneOreMass.getFluid(stoneOreMassAmount))
        .inputItems(Items.STONE)
        .chancedItemOutputLogic(ChanceLogic.XOR)
        .circuitMeta(i)
        .EUt(VA[vein.voltage])
        .duration((int) totalMaterialMass);

      for (OreDrop drop : vein.oreDrops) {
        recipeBuilder.chancedOutput(ChemicalHelper.get(ore, drop.material), drop.chance, 0);
      }

      recipeBuilder.save(provider);
    }
  }
}
