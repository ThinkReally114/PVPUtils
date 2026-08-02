package com.pvp_utils.mixin.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.client.gui.components.ChatComponent$DrawingBackgroundGraphicsAccess")
public interface ChatComponentDrawingBackgroundGraphicsAccessor {
    @Accessor("graphics")
    GuiGraphicsExtractor pvp_utils$getGraphics();
}