package com.pvp_utils.client.render.skia;

import io.github.humbleui.skija.Canvas;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class SkiaScreen extends Screen {

    protected final Screen parent;
    private boolean redrawRequested = true;
    private int lastFrameWidth = -1;
    private int lastFrameHeight = -1;

    protected SkiaScreen(Component title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float delta) {
        if (shouldRedraw()) {
            Canvas canvas = SkiaRenderer.begin();
            if (canvas != null) {
                drawSkia(canvas, this.width, this.height, mouseX, mouseY, delta);
            }
            SkiaRenderer.end(graphics, this.width, this.height);
            redrawRequested = false;
            lastFrameWidth = this.width;
            lastFrameHeight = this.height;
            return;
        }
        SkiaRenderer.drawCached(graphics, this.width, this.height);
    }

    protected boolean shouldRedraw() {
        return redrawRequested
                || !SkiaRenderer.supportsFrameCache()
                || needsContinuousRedraw()
                || this.width != lastFrameWidth
                || this.height != lastFrameHeight
                || !SkiaRenderer.hasFrameCache();
    }

    protected void requestRedraw() {
        redrawRequested = true;
        SkiaRenderer.markFrameDirty();
    }

    protected boolean needsContinuousRedraw() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        requestRedraw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        requestRedraw();
    }

    @Override
    protected void renderBlurredBackground(DrawContext guiGraphics) {}

    @Override
    protected void renderMenuBackground(DrawContext guiGraphics) {}

    @Override
    public void renderBackground(DrawContext guiGraphics, int i, int j, float f) {}

    protected abstract void drawSkia(Canvas canvas, int width, int height, int mouseX, int mouseY, float delta);

    @Override
    public void onClose() {
        closing();
    }

    protected void closing() {
        SkiaRenderer.resetFrameState();
        if (this.minecraft != null) {
            this.minecraft.gui.setScreen(parent);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
