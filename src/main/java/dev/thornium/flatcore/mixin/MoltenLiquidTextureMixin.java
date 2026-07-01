package dev.thornium.flatcore.mixin;

import dev.thornium.flatcore.utils.FTIconSetUtils;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKey;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;

import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FluidBuilder.class, remap = false)
public class MoltenLiquidTextureMixin {
    @Shadow
    private ResourceLocation still;

    @Inject(method = "determineTextures", at = @At("RETURN"), remap = false)
    private void flatcore$overrideMoltenTexture(Material material, FluidStorageKey key, String modid, CallbackInfo ci) {
        if (!key.equals(FluidStorageKeys.MOLTEN)) return;

        FTIconSetUtils.getOverrideIconSet(material).ifPresent(iconSet -> {
            still = ResourceLocation.fromNamespaceAndPath(modid, "block/fluids/fluid.molten." + iconSet.name);
        });
    }
}
