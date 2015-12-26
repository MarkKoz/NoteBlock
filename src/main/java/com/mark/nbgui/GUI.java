package com.mark.nbgui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GUI extends GuiScreen {
	
	public final static int GUI_ID = 20;
	
	@Override
    public void drawScreen(int x, int y, float f) {
            this.drawDefaultBackground();
    }
}
