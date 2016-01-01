package com.mark.nbgui.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

public class GUIErrorLabel extends Gui {
    private String error;
    private FontRenderer fontRendererObj;
    private int x;
    private int y;
    private int color;
    private boolean enabled = true;

    public GUIErrorLabel(FontRenderer fontRendererObj, int x, int y, int color) {
        this.fontRendererObj = fontRendererObj;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public GUIErrorLabel(FontRenderer fontRendererObj, int x, int y) {
        this(fontRendererObj, x, y, 0xFF0000);
    }

    public GUIErrorLabel(FontRenderer fontRendererObj, GuiTextField field, int color) {
        this(fontRendererObj, field.xPosition + 10, field.yPosition + 10, color);
    }

    public GUIErrorLabel(FontRenderer fontRendererObj, GuiTextField field) {
        this(fontRendererObj, field, 0xFF0000);
    }

    public void disable() {
        this.enabled = false;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void draw() {
        if (!this.error.isEmpty() && this.enabled) {
            this.drawCenteredString(this.fontRendererObj,
                    this.error, this.x, this.y, this.color);
        }
    }
}
