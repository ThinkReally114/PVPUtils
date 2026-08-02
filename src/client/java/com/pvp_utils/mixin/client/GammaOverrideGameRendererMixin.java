package com.pvp_utils.mixin.client;

import com.pvp_utils.Config;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GammaOverrideGameRendererMixin {
    private static boolean pvp_utils$gammaOverrideActive = false;
    private static double pvp_utils$originalGamma = 0.5;

    @Inject(method = "renderLevel", at = @At("HEAD"))
    private void pvp_utils$applyGammaOverride(DeltaTracker deltaTracker, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        if (Config.gammaOverride) {
            if (!pvp_utils$gammaOverrideActive) {
                pvp_utils$originalGamma = client.options.gamma().get();
                pvp_utils$gammaOverrideActive = true;
            }
            double target = Config.gammaValue / 15.0;
            client.options.gamma().set(target);
        } else {
            if (pvp_utils$gammaOverrideActive) {
                client.options.gamma().set(pvp_utils$originalGamma);
                pvp_utils$gammaOverrideActive = false;
            }
        }
    }
}