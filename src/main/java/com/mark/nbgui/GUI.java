package com.mark.nbgui;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNote;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.event.world.NoteBlockEvent.Octave;
import net.minecraftforge.fml.common.network.internal.FMLMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

public class GUI extends GuiScreen {
	public final static int GUI_ID = 20;

    private static String instrumentText = "Instrument: {instrument}";
    private static String pitchText = "Pitch: {pitch}";
    private static String octaveText = "Octave: {octave}";

    private TileEntityNote entityNote;
    private BlockNote blockNote;
    private Block underBlock;
    
    public static GuiTextField noteTextField;
    public static GuiTextField octaveTextField;

    public GUI(EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        this.entityNote = (TileEntityNote) world.getTileEntity(pos);
        this.blockNote = (BlockNote) world.getBlockState(pos).getBlock();
        this.underBlock = world.getBlockState(pos.down()).getBlock();
    }
	
	@Override
    public void drawScreen(int x, int y, float f) {
			this.drawDefaultBackground();
			super.drawScreen(x, y, f);
			
			this.noteTextField.drawTextBox();
			this.octaveTextField.drawTextBox();
			
            this.drawCenteredString(this.fontRendererObj,
                    GUI.instrumentText.replace("{instrument}",
                    NoteUtils.getInstrumentString(NoteUtils.getNoteBlockInstrument(this.underBlock))),
                    this.width / 2, 40, 0xFFFFFFFF);
            this.drawCenteredString(this.fontRendererObj,
                    GUI.pitchText.replace("{pitch}", 
                    NoteUtils.getNoteString(NoteUtils.getBlockNote(this.entityNote))),
                    this.width / 2, 50, 0xFFFFFFFF);
            this.drawCenteredString(this.fontRendererObj,
                    GUI.octaveText.replace("{octave}", 
                    NoteUtils.getOctaveString(NoteUtils.getBlockOctave(this.entityNote))),
                    this.width / 2, 60, 0xFFFFFFFF);
            
    }
	
	@Override
	public void initGui() {    
		Keyboard.enableRepeatEvents(false);
		
		//Play
		GuiButton button = new GuiButton(1,this.width / 2 - 30, 90, 60, 20, "Play");
		this.buttonList.add(button);
		
		//Note Text Field
		this.noteTextField = new GuiTextField(2, fontRendererObj, this.width/2 - 60, 130, 120, 20);
		this.noteTextField.setFocused(false);
		this.noteTextField.setCanLoseFocus(true);
		this.noteTextField.setText(NoteUtils.getNoteString(NoteUtils.getBlockNote(this.entityNote)));
		this.noteTextField.setTextColor(-1);
		this.noteTextField.setDisabledTextColour(-1);
		this.noteTextField.setEnableBackgroundDrawing(true);
		this.noteTextField.setMaxStringLength(7);
		
		//Octave Text Field
		this.octaveTextField = new GuiTextField(3, fontRendererObj, this.width/2 - 60, 160, 120, 20);
		this.octaveTextField.setFocused(false);
		this.octaveTextField.setCanLoseFocus(true);
		this.octaveTextField.setText(NoteUtils.getOctaveString(NoteUtils.getBlockOctave(this.entityNote)));
		this.octaveTextField.setTextColor(-1);
		this.octaveTextField.setDisabledTextColour(-1);
		this.octaveTextField.setEnableBackgroundDrawing(true);
		this.octaveTextField.setMaxStringLength(1);		
    }

    @Override
    public void keyTyped(char character, int keyCode) throws IOException {
    	if (this.noteTextField.textboxKeyTyped(character, keyCode)) {
    		if (keyCode == Keyboard.KEY_RETURN) {
    			this.noteTextField.setFocused(false);
                NoteUtils.getNoteFromInput(GUI.noteTextField.getText());
    			//TODO add server packet stuff?
    		}
    	}
    	else if (this.octaveTextField.textboxKeyTyped(character, keyCode)) {
    		if (keyCode == Keyboard.KEY_RETURN) {
    			this.octaveTextField.setFocused(false);
    			NoteUtils.getOctaveFromInput(GUI.octaveTextField.getText());
    			//TODO add server packet stuff?
    		}
    	}
    	else {
    		super.keyTyped(character, keyCode);
    	}
    	}
    
    @Override
    public void mouseClicked(int x, int y, int clickedButon) throws IOException {
    	super.mouseClicked(x, y, clickedButon);
    	if (this.noteTextField.isFocused()) {
    		this.noteTextField.setText("");   		
    	}
    	else if (this.noteTextField.isFocused()) {
    		this.noteTextField.setText("");   		
    	}
    	this.noteTextField.mouseClicked(x, y, clickedButon);
    	this.octaveTextField.mouseClicked(x, y, clickedButon);
    	}
    
    @Override	
    public void updateScreen() {
    	 this.noteTextField.updateCursorCounter();
    	 this.octaveTextField.updateCursorCounter();
    	//note = noteTextField.getText();
    	//octave = octaveTextField.getText();
    }
    
    @Override
    public void onGuiClosed() {
    	Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }

    @Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
			case 1:
                Message msg = new Message(this.entityNote);
                msg.setText("play");
                NBGUI.network.sendToServer(msg);
				break;
		}
	}

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}