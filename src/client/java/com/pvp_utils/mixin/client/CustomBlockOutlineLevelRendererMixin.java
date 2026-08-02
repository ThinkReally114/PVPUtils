package com.pvp_utils.mixin.client;

import com.pvp_utils.Config;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class CustomBlockOutlineLevelRendererMixin {
    @Inject(method = "renderBlockOutline", at = @At("HEAD"), cancellable = true)
    private void pvp_utils$hideVanillaBlockOutline(CallbackInfo ci) {
        if (Config.customBlockOutline) {
            ci.cancel();
        }
    }
}