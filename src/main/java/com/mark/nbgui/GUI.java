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
			
			noteTextField.drawTextBox();
			octaveTextField.drawTextBox();
			
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
		noteTextField = new GuiTextField(2, fontRendererObj, this.width/2 - 60, 130, 120, 20);
		noteTextField.setFocused(false);
		noteTextField.setTextColor(-1);
		noteTextField.setDisabledTextColour(-1);
		noteTextField.setEnableBackgroundDrawing(true);
		noteTextField.setMaxStringLength(7);
		
		//Octave Text Field
		octaveTextField = new GuiTextField(3, fontRendererObj, this.width/2 - 60, 160, 120, 20);
		octaveTextField.setFocused(false);
		octaveTextField.setTextColor(-1);
		octaveTextField.setDisabledTextColour(-1);
		octaveTextField.setEnableBackgroundDrawing(true);
		octaveTextField.setMaxStringLength(1);		
    }

    @Override
    public void keyTyped(char c, int i) throws IOException{
    	if (noteTextField.textboxKeyTyped(c, i)) {
    		//mc.thePlayer.sendQueue.addToSendQueue(new pack("string", this.noteTextField.getText().getBytes());
    	}
    	else if (octaveTextField.textboxKeyTyped(c, i)) {
    		//mc.thePlayer.sendQueue.addToSendQueue(new pack("string", this.octaveTextField.getText().getBytes());
    	}
    	else {
    		super.keyTyped(c, i);
    	}
    	}
    
    @Override
    public void mouseClicked(int i, int j, int k) throws IOException {
    	super.mouseClicked(i, j, k);
    	noteTextField.mouseClicked(i, j, k);
    	octaveTextField.mouseClicked(i, j, k);
    	}
    
    @Override	
    public void updateScreen() {
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