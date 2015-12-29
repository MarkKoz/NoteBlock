package com.mark.nbgui;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNote;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.event.world.NoteBlockEvent.Octave;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.internal.FMLMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.mark.nbgui.packet.Packet;

public class GUI extends GuiScreen {
	public final static int GUI_ID = 20;

    private static String instrumentText = I18n.format("nbgui.string.gui.instrument", new Object[0])+" {instrument}";
    private static String noteText = I18n.format("nbgui.string.gui.note", new Object[0])+" {note}";
    private static String octaveText = I18n.format("nbgui.string.gui.octave", new Object[0])+" {octave}";

    private int x;
    private int y;
    private int z;
    private TileEntityNote entityNote;
    private BlockNote blockNote;
    private Block underBlock;
    
    public static GuiTextField noteTextField;
    public static GuiTextField octaveTextField;
    
    public GUI(EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
        
        //TODO Returns null, fix.
        //System.out.println("[DEBUG] GNote: "+entityNote);
        //System.out.println("[DEBUG] GBlock: "+blockNote);
        //System.out.println("[DEBUG] GUnder: "+underBlock);
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
                    this.width / 2, 30, 0xFFFFFFFF);
            //TODO Remove note and octave strings; redundant with text fields
            this.drawCenteredString(this.fontRendererObj,
                    GUI.noteText.replace("{note}", 
                    NoteUtils.getNoteString(NoteUtils.getBlockNote(this.entityNote))),
                    this.width / 2, 50, 0xFFFFFFFF);
            this.drawCenteredString(this.fontRendererObj,
                    GUI.octaveText.replace("{octave}", 
                    NoteUtils.getOctaveString(NoteUtils.getBlockOctave(this.entityNote))),
                    this.width / 2, 70, 0xFFFFFFFF);
            
    }
	
	@Override
	public void initGui() {    
		Keyboard.enableRepeatEvents(false);
		
		//Play Button
		GuiButton playButton = new GuiButton(1, this.width / 2 - 30, 90, 60, 20, I18n.format("nbgui.button.playNote", new Object[0]));	
		this.buttonList.add(playButton);
		
		//Note +1 Button
		GuiButton noteButtonAdd = new GuiButton(2, this.width / 2 - 60, 130, 20, 20, "+");
		this.buttonList.add(noteButtonAdd);
		
		//Note -1 Button
		GuiButton noteButtonSub = new GuiButton(3, this.width / 2 + 40, 130, 20, 20, "-");
		this.buttonList.add(noteButtonSub);
		
		//Octave +1 Button
		GuiButton octaveButtonAdd = new GuiButton(4, this.width / 2 - 60, 160, 20, 20, "+");
		this.buttonList.add(octaveButtonAdd);
		//TODO Grey out button if octave == 5 && note.equals("F_SHARP")
		
		//Octave -1 Button
		GuiButton octaveButtonSub = new GuiButton(5, this.width / 2 + 40, 160, 20, 20, "-");
		this.buttonList.add(octaveButtonSub);
		//TODO Grey out button if octave == 3
		
		
		//Note Text Field
		this.noteTextField = new GuiTextField(2, fontRendererObj, this.width/2 - 30, 130, 60, 20);
		this.noteTextField.setFocused(false);
		this.noteTextField.setCanLoseFocus(true);
		this.noteTextField.setText(NoteUtils.getNoteString(NoteUtils.getBlockNote(this.entityNote)));
		this.noteTextField.setTextColor(-1);
		this.noteTextField.setDisabledTextColour(-1);
		this.noteTextField.setEnableBackgroundDrawing(true);
		this.noteTextField.setMaxStringLength(7);
		
		//Octave Text Field
		this.octaveTextField = new GuiTextField(3, fontRendererObj, this.width/2 - 30, 160, 60, 20);
		this.octaveTextField.setFocused(false);
		this.octaveTextField.setCanLoseFocus(true);
		this.octaveTextField.setText(NoteUtils.getOctaveString(NoteUtils.getBlockOctave(this.entityNote)));
		this.octaveTextField.setTextColor(-1);
		this.octaveTextField.setDisabledTextColour(-1);
		this.octaveTextField.setEnableBackgroundDrawing(true);
		this.octaveTextField.setMaxStringLength(1);	
		
		//TODO Add option to merge note and octave text fields into one.
    }
	
    @Override
    public void keyTyped(char character, int keyCode) throws IOException {
    	super.keyTyped(character, keyCode);
    	if (keyCode == 28 && this.noteTextField.isFocused()) {
        	System.out.println("[DEBUG] Character: "+character);
        	System.out.println("[DEBUG] Code: "+keyCode);        	
			//TODO Send packets
    	} else if (keyCode == 28 && this.octaveTextField.isFocused()) {
        	System.out.println("[DEBUG] Character: "+character);
        	System.out.println("[DEBUG] Code: "+keyCode);        	
			//TODO Send packets
    	}   	
    }
    
    @Override
    public void mouseClicked(int x, int y, int clickedButon) throws IOException {
    	super.mouseClicked(x, y, clickedButon);
    	this.noteTextField.mouseClicked(x, y, clickedButon);
    	this.octaveTextField.mouseClicked(x, y, clickedButon);
    	if (this.noteTextField.isFocused()) {
    		this.noteTextField.setText("");
    	} else if (this.octaveTextField.isFocused()) {
    		this.octaveTextField.setText("");   		
    	}
    	/*Debug
    	System.out.println("[DEBUG] Note focused: "+noteTextField.isFocused());
    	System.out.println("[DEBUG] Octave focused: "+octaveTextField.isFocused());
    	System.out.println("[DEBUG]-------------------END-----------------------");*/
    	}
    
    @Override	
    public void updateScreen() {
    	 this.noteTextField.updateCursorCounter();
    	 this.octaveTextField.updateCursorCounter();
    }
    
    @Override
    public void onGuiClosed() {
    	Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }
    
    @Override
	protected void actionPerformed(GuiButton button) throws IOException {
    	switch (button.id) {
    		case 1: //Play
    			Packet playNote = new Packet(x, y, z);
    			//playNote.setText("play");
    			NBGUI.network.sendToServer(playNote);          
    			break;
			case 2: //Note +1
				break;
			case 3: //Note -1
				break;
			case 4: //Octave +1
				break;
			case 5: //Octave -1
				break;
		}
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}