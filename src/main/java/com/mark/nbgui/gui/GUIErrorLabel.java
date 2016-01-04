package com.mark.nbgui.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

public class GUIErrorLabel extends Gui {
    private String error = "";
    private FontRenderer fontRendererObj;
    private int x;
    private int y;
    private int color;
    private boolean enabled = false;

    public GUIErrorLabel(FontRenderer fontRendererObj, int x, int y, int color) {
        this.fontRendererObj = fontRendererObj;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public GUIErrorLabel(FontRenderer fontRendererObj, int x, int y) {
        this(fontRendererObj, x, y, 0xFF0000);
    }

    public void disable() {
        this.enabled = false;
        this.error = "";
    }

    public void setError(String error) {
        this.enabled = !error.isEmpty();
        this.error = error;
    }

    public void draw() {
        if (this.enabled && !this.error.isEmpty()) {
            this.drawCenteredString(this.fontRendererObj,
                    this.error, this.x, this.y, this.color);
        }
    }
}
