package dev.thornium.flatcore.registry.machine

import com.gregtechceu.gtceu.GTCEu
import com.gregtechceu.gtceu.api.data.RotationState
import com.gregtechceu.gtceu.api.data.chemical.material.Material
import com.gregtechceu.gtceu.api.data.tag.TagPrefix.frameGt
import com.gregtechceu.gtceu.api.machine.MachineDefinition
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern
import com.gregtechceu.gtceu.api.pattern.Predicates
import com.gregtechceu.gtceu.common.data.GTBlocks.*
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks
import com.gregtechceu.gtceu.common.data.GTMaterials.Steel
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers.*
import com.tterrag.registrate.util.entry.BlockEntry
import dev.thornium.flatcore.FlatCore
import dev.thornium.flatcore.FlatCore.Companion.REGISTRATE
import dev.thornium.flatcore.api.Initialized
import dev.thornium.flatcore.common.data.FTBlocks.CASING_BLANKING_DIE
import dev.thornium.flatcore.common.data.FTBlocks.CASING_CAST_MANIFOLD
import dev.thornium.flatcore.common.data.FTBlocks.CASING_PRESSURE_VESSEL
import dev.thornium.flatcore.common.data.FTCreativeTabs
import dev.thornium.flatcore.common.machine.TierlessWorkableMachine
import dev.thornium.flatcore.gtbridge.FTRecipeTypes.GREENHOUSE_RECIPES
import dev.thornium.flatcore.gtbridge.FTRecipeTypes.IMPACT_FORMER_RECIPES
import dev.thornium.flatcore.gtbridge.FTRecipeTypes.ORE_SEPARATOR_RECIPES
import dev.thornium.flatcore.gtbridge.FTRecipeTypes.PIPE_FORMER_RECIPES
import dev.thornium.flatcore.gtbridge.FTRecipeTypes.ROTARY_FORMER_RECIPES
import dev.thornium.flatcore.gtbridge.FTRecipeTypes.STONE_OREIFIER_RECIPES
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.fluids.FluidType
import net.minecraft.world.level.material.Fluids

@Suppress("unused")
object FTMachines : Initialized {
    init {
        REGISTRATE.creativeModeTab { FTCreativeTabs.FLATCORE }
        REGISTRATE.addLang("machine", FlatCore.id("tierless.tooltip"), "Runs without electricity")
    }

    val ORE_SEPARATOR: MachineDefinition = REGISTRATE
        .machine("ore_separator") { holder -> TierlessWorkableMachine(holder, 32 * FluidType.BUCKET_VOLUME) }
        .langValue("Ore Separator")
        .rotationState(RotationState.NON_Y_AXIS)
        .recipeType(ORE_SEPARATOR_RECIPES)
        .editableUI(TierlessWorkableMachine.EDITABLE_UI_CREATOR.apply(FlatCore.id("ore_separator"), ORE_SEPARATOR_RECIPES))
        .workableCasingModel(
            ResourceLocation.fromNamespaceAndPath("minecraft", "block/furnace_top"),
            GTCEu.id("block/machines/centrifuge"),
        )
        .tooltips(Component.translatable("machine.flatcore.tierless.tooltip"))
        .register()

    val GREENHOUSE: MultiblockMachineDefinition = REGISTRATE
        .multiblock("greenhouse", ::WorkableElectricMultiblockMachine)
        .rotationState(RotationState.ALL)
        .recipeType(GREENHOUSE_RECIPES)
        .recipeModifiers(PARALLEL_HATCH, OC_NON_PERFECT_SUBTICK, BATCH_MODE)
        .appearanceBlock(CASING_STEEL_SOLID)
        .pattern { definition ->
            FactoryBlockPattern.start()
                .aisle("SSSSS", "UDDDU", "UDDDU", "UUGUU", " UUU ")
                .aisle("SFFFS", "D   D", "D   D", "GO OG", " GEG ")
                .aisle("SFFFS", "D   D", "D   D", "GO OG", " GEG ")
                .aisle("SFFFS", "D   D", "D   D", "GO OG", " GEG ")
                .aisle("SFFFS", "D   D", "D   D", "GO OG", " GEG ")
                .aisle("SFFFS", "D   D", "D   D", "GO OG", " GEG ")
                .aisle("SS!SS", "UDDDU", "UDDDU", "UUGUU", " UUU ")
                .where("!", Predicates.controller(Predicates.blocks(definition.get())))
                .where(
                    "S",
                    Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(8)
                        .or(Predicates.autoAbilities(*definition.recipeTypes)),
                )
                .where("U", Predicates.blocks(CASING_STEEL_SOLID.get()))
                .where("G", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                .where(
                    "D",
                    Predicates.blocks(CASING_TEMPERED_GLASS.get())
                        .or(Predicates.blockTag(BlockTags.DOORS).setMaxGlobalLimited(4)),
                )
                .where(
                    "O",
                    Predicates.blocks(CASING_TEMPERED_GLASS.get())
                        .or(Predicates.any()),
                )
                .where(
                    "F",
                    Predicates.blockTag(BlockTags.DIRT)
                        .or(Predicates.blocks(Blocks.FARMLAND))
                        .or(Predicates.fluids(Fluids.WATER)),
                )
                .where(
                    "E",
                    Predicates.blocks(CASING_TEMPERED_GLASS.get())
                        .or(Predicates.blocks(CASING_STEEL_SOLID.get()))
                        .or(Predicates.blocks(Blocks.REDSTONE_LAMP)),
                )
                .where(" ", Predicates.any())
                .build()
        }
        .workableCasingModel(
            GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
            GTCEu.id("block/multiblock/implosion_compressor"),
        )
        .register()

    val STONE_OREIFIER: MultiblockMachineDefinition = REGISTRATE
        .multiblock("stone_oreifier", ::WorkableElectricMultiblockMachine)
        .rotationState(RotationState.NON_Y_AXIS)
        .recipeType(STONE_OREIFIER_RECIPES)
        .recipeModifiers(OC_NON_PERFECT, BATCH_MODE)
        .pattern { definition ->
            FactoryBlockPattern.start()
                .aisle("XXX", "F F", "F F", "XXX")
                .aisle("XXX", " P ", " P ", "XMX")
                .aisle("X!X", "F F", "F F", "XXX")
                .where("!", Predicates.controller(Predicates.blocks(definition.get())))
                .where(
                    "X",
                    Predicates.blocks(CASING_STEEL_SOLID.get()).setMinGlobalLimited(9)
                        .or(Predicates.autoAbilities(*definition.recipeTypes))
                        .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)),
                )
                .where("F", Predicates.blocks(getFrame(Steel).get()))
                .where("P", Predicates.blocks(CASING_STEEL_PIPE.get()))
                .where("M", Predicates.abilities(PartAbility.MUFFLER).setExactLimit(1))
                .where(" ", Predicates.any())
                .build()
        }
        .workableCasingModel(
            GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
            GTCEu.id("block/multiblock/implosion_compressor"),
        )
        .register()

    val PIPE_FORMER: MultiblockMachineDefinition = REGISTRATE
        .multiblock("pipe_former", ::WorkableElectricMultiblockMachine)
        .langValue("Pipe Extrusion Array")
        .rotationState(RotationState.NON_Y_AXIS)
        .recipeType(PIPE_FORMER_RECIPES)
        .recipeModifiers(PARALLEL_HATCH, OC_NON_PERFECT_SUBTICK, BATCH_MODE)
        .appearanceBlock(CASING_CAST_MANIFOLD)
        .pattern { definition ->
            FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "FPF", "GGG")
                .aisle("XXX", "FPF", "GGG")
                .aisle("XXX", "FPF", "GGG")
                .aisle("XXX", "FPF", "GGG")
                .aisle("XXX", "X!X", "XXX")
                .where("!", Predicates.controller(Predicates.blocks(definition.get())))
                .where(
                    "X",
                    Predicates.blocks(CASING_CAST_MANIFOLD.get()).setMinGlobalLimited(8)
                        .or(Predicates.autoAbilities(*definition.recipeTypes))
                        .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)),
                )
                .where("F", Predicates.blocks(getFrame(Steel).get()))
                .where("P", Predicates.blocks(CASING_STEEL_PIPE.get()))
                .where("G", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                .where(" ", Predicates.any())
                .build()
        }
        .workableCasingModel(
            FlatCore.id("block/casings/cast_manifold_casing"),
            GTCEu.id("block/multiblock/implosion_compressor"),
        )
        .register()

    val IMPACT_FORMER: MultiblockMachineDefinition = REGISTRATE
        .multiblock("impact_former", ::WorkableElectricMultiblockMachine)
        .langValue("Impact Former")
        .rotationState(RotationState.NON_Y_AXIS)
        .recipeType(IMPACT_FORMER_RECIPES)
        .recipeModifiers(PARALLEL_HATCH, OC_NON_PERFECT_SUBTICK, BATCH_MODE)
        .appearanceBlock(CASING_PRESSURE_VESSEL)
        .pattern { definition ->
            FactoryBlockPattern.start()
                .aisle("XXX", "XGX", "XXX")
                .aisle("XGX", "G G", "XGX")
                .aisle("XXX", "X!X", "XXX")
                .where("!", Predicates.controller(Predicates.blocks(definition.get())))
                .where(
                    "X",
                    Predicates.blocks(CASING_PRESSURE_VESSEL.get()).setMinGlobalLimited(8)
                        .or(Predicates.autoAbilities(*definition.recipeTypes))
                        .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)),
                )
                .where("G", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                .where(" ", Predicates.any())
                .build()
        }
        .workableCasingModel(
            FlatCore.id("block/casings/pressure_vessel_casing"),
            GTCEu.id("block/multiblock/implosion_compressor"),
        )
        .register()

    val ROTARY_FORMER: MultiblockMachineDefinition = REGISTRATE
        .multiblock("rotary_former", ::WorkableElectricMultiblockMachine)
        .langValue("Rotary Blanking Press")
        .rotationState(RotationState.NON_Y_AXIS)
        .recipeType(ROTARY_FORMER_RECIPES)
        .recipeModifiers(PARALLEL_HATCH, OC_NON_PERFECT_SUBTICK, BATCH_MODE)
        .appearanceBlock(CASING_BLANKING_DIE)
        .pattern { definition ->
            FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("LGL", "L L", "LXL")
                .aisle("LGL", "L L", "LXL")
                .aisle("XXX", "X!X", "XXX")
                .where("!", Predicates.controller(Predicates.blocks(definition.get())))
                .where(
                    "X",
                    Predicates.blocks(CASING_BLANKING_DIE.get()).setMinGlobalLimited(8)
                        .or(Predicates.autoAbilities(*definition.recipeTypes))
                        .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)),
                )
                .where("L", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                .where("G", Predicates.blocks(CASING_GRATE.get()))
                .where(" ", Predicates.any())
                .build()
        }
        .workableCasingModel(
            FlatCore.id("block/casings/blanking_die_casing"),
            GTCEu.id("block/multiblock/implosion_compressor"),
        )
        .register()

    private fun getFrame(material: Material): BlockEntry<out Block> =
        GTMaterialBlocks.MATERIAL_BLOCKS.get(frameGt, material)
            ?: error("No frame block registered for $material")
}
