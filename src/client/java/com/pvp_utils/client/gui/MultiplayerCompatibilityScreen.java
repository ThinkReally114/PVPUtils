package com.pvp_utils.client.gui;

import com.pvp_utils.Config;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MultiplayerCompatibilityScreen extends Screen {
    private final Screen lastScreen;

    public MultiplayerCompatibilityScreen(Screen lastScreen) {
        super(Component.literal(Config.isChinese ? "联机设置" : "Multiplayer Settings"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int buttonWidth = Math.min(400, this.width - 20);
        int buttonX = centerX - buttonWidth / 2;
        int firstButtonY = this.height / 2 - 48;

        this.addRenderableWidget(Button.builder(modeLabel(), button -> {
            Config.multiplayerAdvancedMode = !Config.multiplayerAdvancedMode;
            if (!Config.multiplayerAdvancedMode) {
                setAllEnabled(true);
            }
            Config.save();
            this.minecraft.gui.setScreen(new MultiplayerCompatibilityScreen(lastScreen));
        }).bounds(buttonX, firstButtonY, buttonWidth, 20).build());

        if (Config.multiplayerAdvancedMode) {
            addAdvancedSettings(buttonX, firstButtonY + 24, buttonWidth);
        } else {
            this.addRenderableWidget(Button.builder(oneClickLabel(), button -> {
                setAllEnabled(!allEnabled());
                Config.save();
                button.setMessage(oneClickLabel());
            }).bounds(buttonX, firstButtonY + 24, buttonWidth, 20).build());
        }

        this.addRenderableWidget(Button.builder(Component.literal(Config.isChinese ? "完成" : "Done"), button -> this.onClose())
                .bounds(centerX - 100, this.height - 28, 200, 20).build());
    }

    private void addAdvancedSettings(int x, int y, int width) {
        this.addRenderableWidget(Button.builder(brandLabel(), button -> {
            Config.modifyBrand = !Config.modifyBrand;
            Config.save();
            button.setMessage(brandLabel());
        }).bounds(x, y, width, 20).build());
        this.addRenderableWidget(Button.builder(channelLabel(), button -> {
            Config.modifyChannels = !Config.modifyChannels;
            Config.save();
            button.setMessage(channelLabel());
        }).bounds(x, y + 24, width, 20).build());
        this.addRenderableWidget(Button.builder(translationKeyLabel(), button -> {
            Config.modifyTranslationKeys = !Config.modifyTranslationKeys;
            Config.save();
            button.setMessage(translationKeyLabel());
        }).bounds(x, y + 48, width, 20).build());
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(lastScreen);
    }

    private static Component modeLabel() {
        String mode = Config.multiplayerAdvancedMode
                ? (Config.isChinese ? "高级模式" : "Advanced")
                : (Config.isChinese ? "简单模式" : "Simple");
        return Component.literal((Config.isChinese ? "设置模式" : "Settings Mode") + ": " + mode);
    }

    private static Component oneClickLabel() {
        return toggleLabel(Config.isChinese ? "一键害死 MCBI 和 CNTier 无能的反作弊" : "Fuck MCBI & CNTier", allEnabled());
    }

    private static Component brandLabel() {
        return toggleLabel(Config.isChinese ? "修改客户端类型" : "Modify Brand", Config.modifyBrand);
    }

    private static Component channelLabel() {
        return toggleLabel(Config.isChinese ? "阻止自定义信道" : "Block Channels", Config.modifyChannels);
    }

    private static Component translationKeyLabel() {
        return toggleLabel(Config.isChinese ? "反检测翻译键" : "Anti Detecting Translation Key", Config.modifyTranslationKeys);
    }

    private static Component toggleLabel(String name, boolean enabled) {
        return Component.literal(name + ": " + enabledText(enabled));
    }

    private static boolean allEnabled() {
        return Config.modifyBrand && Config.modifyChannels && Config.modifyTranslationKeys;
    }

    private static void setAllEnabled(boolean enabled) {
        Config.modifyBrand = enabled;
        Config.modifyChannels = enabled;
        Config.modifyTranslationKeys = enabled;
    }

    private static String enabledText(boolean enabled) {
        if (Config.isChinese) {
            return enabled ? "开启" : "关闭";
        }
        return enabled ? "On" : "Off";
    }
}
