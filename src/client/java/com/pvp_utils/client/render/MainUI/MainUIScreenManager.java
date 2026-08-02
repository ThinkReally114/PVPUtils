package com.pvp_utils.client.render.MainUI;

import com.pvp_utils.Config;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

import java.lang.reflect.Method;

public final class MainUIScreenManager {
    private static boolean firstMainUIAutoOpen = true;

    private MainUIScreenManager() {}

    private static void addRenderableWidgetReflective(Screen screen, AbstractWidget widget) {
        try {
            Method method = Screen.class.getDeclaredMethod("addRenderableWidget", AbstractWidget.class);
            method.setAccessible(true);
            method.invoke(screen, widget);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add widget via reflection", e);
        }
    }

    public static void init() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!(screen instanceof TitleScreen titleScreen)) return;
            if (Config.useMainUI) {
                boolean delayEntryFade = firstMainUIAutoOpen;
                firstMainUIAutoOpen = false;
                client.gui.setScreen(new PVPUtilsMainUI(titleScreen, false, delayEntryFade));
                return;
            }
            Button button = Button.builder(Component.literal("P"), b -> {
                Config.useMainUI = true;
                Config.save();
                client.gui.setScreen(new PVPUtilsMainUI(titleScreen, true));
            }).bounds(scaledWidth / 2 + 104, scaledHeight / 4 + 48 + 36 - 24, 20, 20).build();
            addRenderableWidgetReflective(titleScreen, button);
        });
    }
}
