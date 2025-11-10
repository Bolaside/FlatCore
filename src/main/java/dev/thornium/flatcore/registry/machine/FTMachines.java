package dev.thornium.flatcore.registry.machine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.thornium.flatcore.common.data.FTCreativeTabs;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.*;
import static dev.thornium.flatcore.FlatCore.REGISTRATE;
import static dev.thornium.flatcore.gtbridge.FTRecipeTypes.*;

@SuppressWarnings("unused")
public class FTMachines {
  static {
    REGISTRATE.creativeModeTab(() -> FTCreativeTabs.FLATCORE);
  }

  public final static MultiblockMachineDefinition GREENHOUSE = REGISTRATE
    .multiblock("greenhouse", WorkableElectricMultiblockMachine::new)
    .rotationState(RotationState.ALL)
    .recipeType(GREENHOUSE_RECIPES)
    .recipeModifiers(PARALLEL_HATCH, OC_NON_PERFECT_SUBTICK, BATCH_MODE)
    .appearanceBlock(CASING_STEEL_SOLID)
    .pattern(definition -> FactoryBlockPattern.start()
      .aisle("SSSSS", "UDDDU", "UDDDU", "UUGUU", " UUU ")
      .aisle("SFFFS", "D   D", "D   D", "GO OG", " GEG ")
      .aisle("SFFFS", "D   D", "D   D", "GO OG", " GEG ")
      .aisle("SFFFS", "D   D", "D   D", "GO OG", " GEG ")
      .aisle("SFFFS", "D   D", "D   D", "GO OG", " GEG ")
      .aisle("SFFFS", "D   D", "D   D", "GO OG", " GEG ")
      .aisle("SS@SS", "UDDDU", "UDDDU", "UUGUU", " UUU ")
      .where("@", Predicates.controller(Predicates.blocks(definition.get())))
      .where("S", Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(8)
        .or(Predicates.autoAbilities(definition.getRecipeTypes())))
      .where("U", Predicates.blocks(CASING_STEEL_SOLID.get()))
      .where("G", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
      .where("D", Predicates.blocks(CASING_TEMPERED_GLASS.get())
        .or(Predicates.blockTag(BlockTags.DOORS).setMaxGlobalLimited(4)))
      .where("O", Predicates.blocks(CASING_TEMPERED_GLASS.get())
        .or(Predicates.any()))
      .where("F", Predicates.blockTag(BlockTags.DIRT)
        .or(Predicates.blocks(Blocks.FARMLAND))
        .or(Predicates.fluids(Fluids.WATER)))
      .where("E", Predicates.blocks(CASING_TEMPERED_GLASS.get())
        .or(Predicates.blocks(CASING_STEEL_SOLID.get()))
        .or(Predicates.blocks(Blocks.REDSTONE_LAMP)))
      .where(" ", Predicates.any())
      .build())
    .workableCasingModel(
      GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
      GTCEu.id("block/multiblock/implosion_compressor"))
    .register();

  public final static MultiblockMachineDefinition STONE_OREIFIER = REGISTRATE
    .multiblock("stone_oreifier", WorkableElectricMultiblockMachine::new)
    .rotationState(RotationState.NON_Y_AXIS)
    .recipeType(STONE_OREIFIER_RECIPES)
    .recipeModifiers(OC_NON_PERFECT, BATCH_MODE)
    .pattern(definition -> FactoryBlockPattern.start()
      .aisle("XXX", "F F", "F F", "XXX")
      .aisle("XXX", " P ", " P ", "XMX")
      .aisle("X!X", "F F", "F F", "XXX")
      .where("!", Predicates.controller(Predicates.blocks(definition.get())))
      .where("X", Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(9)
        .or(Predicates.autoAbilities(definition.getRecipeTypes()))
        .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
      .where("F", Predicates.blocks(getFrame(Steel).get()))
      .where("P", Predicates.blocks(CASING_STEEL_PIPE.get()))
      .where("M", Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1))
      .where(" ", Predicates.any())
      .build())
    .workableCasingModel(
      GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
      GTCEu.id("block/multiblock/implosion_compressor"))
    .register();

  private static BlockEntry<? extends Block> getFrame(Material material) {
    return GTMaterialBlocks.MATERIAL_BLOCKS.get(frameGt, material);
  }

  public static void init() {}
}
