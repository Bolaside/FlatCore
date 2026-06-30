package dev.thornium.flatcore.common.data;

import com.gregtechceu.gtceu.common.data.models.GTModels;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.thornium.flatcore.FlatCore;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class FTBlocks {
    public static void init() {}

    public static final BlockEntry<Block> CASING_WROUGHT_IRON_ROUGH = createCasingBlock(
            "rough_machine_casing",
            FlatCore.id("block/casings/machine_casing_rough"));

    public static BlockEntry<Block> createCasingBlock(String name, ResourceLocation texture) {
        return createCasingBlock(name, Block::new, texture, () -> Blocks.IRON_BLOCK, () -> RenderType::solid);
    }

    public static BlockEntry<Block> createCasingBlock(String name,
                                                      NonNullFunction<BlockBehaviour.Properties, Block> blockSupplier,
                                                      ResourceLocation texture,
                                                      NonNullSupplier<? extends Block> properties,
                                                      Supplier<Supplier<RenderType>> type) {
        return FlatCore.REGISTRATE.block(name, blockSupplier)
                .initialProperties(properties)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(type)
                .exBlockstate(GTModels.cubeAllModel(texture))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .item(BlockItem::new)
                .build()
                .register();
    }
}
