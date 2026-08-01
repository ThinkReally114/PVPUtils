package com.pvp_utils.mixin.client;

import com.pvp_utils.client.modules.impl.Optimize.InputMethodFix.InputMethodFix;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class InputMethodFixMinecraftMixin {
    @Inject(method = "setWindowActive", at = @At("TAIL"))
    private void pvp_utils$onWindowActiveChanged(boolean active, CallbackInfo ci) {
        InputMethodFix.onWindowActiveChanged(active, (Minecraft) (Object) this);
    }
}