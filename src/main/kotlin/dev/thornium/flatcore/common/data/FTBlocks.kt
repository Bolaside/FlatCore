package dev.thornium.flatcore.common.data

import com.gregtechceu.gtceu.common.data.models.GTModels
import com.gregtechceu.gtceu.data.recipe.CustomTags
import com.tterrag.registrate.util.entry.BlockEntry
import com.tterrag.registrate.util.nullness.NonNullFunction
import com.tterrag.registrate.util.nullness.NonNullSupplier
import dev.thornium.flatcore.FlatCore
import dev.thornium.flatcore.api.Initialized
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import java.util.function.Supplier

object FTBlocks : Initialized {
    val CASING_WROUGHT_IRON_ROUGH: BlockEntry<Block> = createCasingBlock(
        "rough_machine_casing",
        FlatCore.id("block/casings/machine_casing_rough"),
    )

    val CASING_CAST_MANIFOLD: BlockEntry<Block> = createCasingBlock(
        "cast_manifold_casing",
        FlatCore.id("block/casings/cast_manifold_casing"),
    )

    val CASING_PRESSURE_VESSEL: BlockEntry<Block> = createCasingBlock(
        "pressure_vessel_casing",
        FlatCore.id("block/casings/pressure_vessel_casing"),
    )

    val CASING_BLANKING_DIE: BlockEntry<Block> = createCasingBlock(
        "blanking_die_casing",
        FlatCore.id("block/casings/blanking_die_casing"),
    )

    private fun createCasingBlock(name: String, texture: ResourceLocation): BlockEntry<Block> =
        createCasingBlock(name, ::Block, texture, { Blocks.IRON_BLOCK }, { Supplier { RenderType.solid() } })

    private fun createCasingBlock(
        name: String,
        blockSupplier: NonNullFunction<BlockBehaviour.Properties, Block>,
        texture: ResourceLocation,
        properties: NonNullSupplier<out Block>,
        type: Supplier<Supplier<RenderType>>,
    ): BlockEntry<Block> =
        FlatCore.REGISTRATE.block(name, blockSupplier)
            .initialProperties(properties)
            .properties { it.isValidSpawn { _, _, _, _ -> false } }
            .addLayer(type)
            .exBlockstate(GTModels.cubeAllModel(texture))
            .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
            .item(::BlockItem)
            .build()
            .register()
}
