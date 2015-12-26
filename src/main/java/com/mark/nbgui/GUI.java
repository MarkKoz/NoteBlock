package com.mark.nbgui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.Arrays;

public class GUI extends GuiScreen {
	public final static int GUI_ID = 20;
	
	@Override
    public void drawScreen(int x, int y, float f) {
			//EventHandlerCommon.openGUI(event);
			//String pitch = d;
			
			//Draw GUI
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, "Instrument: ", this.width / 2, 40, 0xFFFFFFFF);
            this.drawCenteredString(this.fontRendererObj, "Pitch: ", this.width / 2, 50, 0xFFFFFFFF);
            this.drawCenteredString(this.fontRendererObj, "Octave: ", this.width / 2, 60, 0xFFFFFFFF);
    }
	
	@Override
	public void initGui() {
		// DEBUG
	    System.out.println("GUI initGUI()");

		super.initGui();

        GuiButton button = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, "Play");

		this.buttonList.add(button);

        System.out.println(Arrays.toString(this.buttonList.toArray()));
    }

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
			case 1:
				System.out.println("Play button clicked");
				break;
		}
	}
/**
     * Returns true if this GUI should pause the game when it is displayed in 

     * single-player
     */
    /*@Override
    public boolean doesGuiPauseGame() {
        return true;
    }*/
}
