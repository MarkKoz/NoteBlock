package com.mark.nbgui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GUI extends GuiScreen {
	
	private GuiButton buttonPlay;
	
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
	    
		buttonList.clear();
		super.initGui();
		
		buttonPlay = new GuiButton(1, 20, 90, 20, 15, "Play");
		buttonList.add(buttonPlay);
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
