package dev.thornium.flatcore.mixin;

import com.gregtechceu.gtceu.api.block.PipeBlock;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.registry.registrate.provider.GTBlockstateProvider;
import com.gregtechceu.gtceu.common.pipelike.fluidpipe.FluidPipeType;
import dev.thornium.flatcore.utils.FTIconSetUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = FluidPipeType.class, remap = false)
public abstract class FluidPipeModelMixin {
    @ModifyVariable(method = "createPipeModel", at = @At(value = "STORE"), name = "side")
    private String flatcore$modifySide(String value, PipeBlock<?, ?, ?> block, Material material,
                                       GTBlockstateProvider provider) {
        return FTIconSetUtils.overridePipePath(value, material);
    }

    @ModifyVariable(method = "createPipeModel", at = @At(value = "STORE"), name = "end")
    private String flatcore$modifyEnd(String value, PipeBlock<?, ?, ?> block, Material material,
                                      GTBlockstateProvider provider) {
        return FTIconSetUtils.overridePipePath(value, material);
    }
}
