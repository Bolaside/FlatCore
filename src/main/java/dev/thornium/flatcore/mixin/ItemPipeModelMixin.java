package dev.thornium.flatcore.mixin;

import dev.thornium.flatcore.utils.FTIconSetUtils;

import com.gregtechceu.gtceu.api.block.PipeBlock;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.registry.registrate.provider.GTBlockstateProvider;
import com.gregtechceu.gtceu.common.pipelike.item.ItemPipeType;

import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ItemPipeType.class, remap = false)
public class ItemPipeModelMixin {
    @ModifyVariable(method = "createPipeModel", at = @At(value = "STORE"), name = "sideTexture")
    private ResourceLocation flatcore$modifySide(ResourceLocation value, PipeBlock<?, ?, ?> block, Material material,
                                                 GTBlockstateProvider provider) {
        return FTIconSetUtils.overridePipeLocation(value, material);
    }

    @ModifyVariable(method = "createPipeModel", at = @At(value = "STORE"), name = "endTexture")
    private ResourceLocation flatcore$modifyEnd(ResourceLocation value, PipeBlock<?, ?, ?> block, Material material,
                                                GTBlockstateProvider provider) {
        return FTIconSetUtils.overridePipeLocation(value, material);
    }
}
