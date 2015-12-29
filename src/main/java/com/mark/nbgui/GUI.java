package com.mark.nbgui;

import com.mark.nbgui.packet.Packet;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI extends GuiScreen {
	public final static int GUI_ID = 20;

    private static String instrumentText = I18n.format("nbgui.string.gui.instrument", new Object[0])+" {instrument}";
    private static String noteText = I18n.format("nbgui.string.gui.note", new Object[0])+" {note}";
    private static String octaveText = I18n.format("nbgui.string.gui.octave", new Object[0])+" {octave}";

    private int x;
    private int y;
    private int z;
    private TileEntityNote entityNote;
    private Block underBlock;
    
    private GuiTextField noteTextField;
    private GuiTextField octaveTextField;

	public static List<IStringReceived> notesReceived = new ArrayList<IStringReceived>();
	public static List<IStringReceived> octavesReceived = new ArrayList<IStringReceived>();

    public static void addNoteReceived(IStringReceived received, int x, int y, int z) {
        NBGUI.network.sendToServer(new Packet(x, y, z, 0, "getStrings"));
        GUI.notesReceived.add(received);
    }

    public static void addOctaveReceived(IStringReceived received, int x, int y, int z) {
        NBGUI.network.sendToServer(new Packet(x, y, z, 0, "getStrings"));
        GUI.octavesReceived.add(received);
    }


    public GUI(EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
        
        this.entityNote = (TileEntityNote) world.getTileEntity(pos);
        this.underBlock = world.getBlockState(pos.down()).getBlock();
        
        //DEBUG
        /*System.out.println("[DEBUG] [GUI] World: "+world);
        System.out.println("[DEBUG] [GUI] Player: "+player);
        System.out.println("[DEBUG] [GUI] Pos: "+pos);
        System.out.println("[DEBUG] [GUI] TE: "+this.entityNote);
        System.out.println("[DEBUG] [GUI] TE Note: "+this.entityNote.note);
        System.out.println("[DEBUG] [GUI] Under: "+this.underBlock);*/
    }
	
    /*public String getNoteString() {
		return null;
    }
    
    public String getOctaveString() {
		return null;
    }*/
    
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
        /*this.drawCenteredString(this.fontRendererObj, 
        	GUI.noteText.replace("{note}",  
        	getNoteString()), 
        	this.width / 2, 50, 0xFFFFFFFF);
        this.drawCenteredString(this.fontRendererObj,
            GUI.octaveText.replace("{octave}", 
            getOctaveString()),
            this.width / 2, 70, 0xFFFFFFFF);*/
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
		//this.noteTextField.setText(NoteUtils.getNoteString(NoteUtils.getBlockNote(this.entityNote)));

        GUI.addNoteReceived(str -> GUI.this.noteTextField.setText(str), this.x, this.y, this.z);
//		this.noteTextField.setText(getNoteString());
		this.noteTextField.setTextColor(-1);
		this.noteTextField.setDisabledTextColour(-1);
		this.noteTextField.setEnableBackgroundDrawing(true);
		this.noteTextField.setMaxStringLength(7);
		
		//Octave Text Field
		this.octaveTextField = new GuiTextField(3, fontRendererObj, this.width/2 - 30, 160, 60, 20);
		this.octaveTextField.setFocused(false);
		this.octaveTextField.setCanLoseFocus(true);
		this.octaveTextField.setTextColor(-1);
		this.octaveTextField.setDisabledTextColour(-1);
		this.octaveTextField.setEnableBackgroundDrawing(true);
		this.octaveTextField.setMaxStringLength(1);
		//this.octaveTextField.setText(NoteUtils.getOctaveString(NoteUtils.getBlockOctave(this.entityNote)));
        GUI.addOctaveReceived(str -> GUI.this.octaveTextField.setText(str), this.x, this.y, this.z);
//		this.octaveTextField.setText(getOctaveString());
		
		//TODO Add option to merge note and octave text fields into one.
    }
	
    @Override
    public void keyTyped(char character, int keyCode) throws IOException {
    	super.keyTyped(character, keyCode);
    	this.noteTextField.textboxKeyTyped(character, keyCode);
    	this.octaveTextField.textboxKeyTyped(character, keyCode);
    	
    	if (keyCode == 28 && this.noteTextField.isFocused()) {   
        	this.noteTextField.setFocused(false);
        	    	
        	String note = this.noteTextField.getText();
        	int octave = Integer.parseInt(this.octaveTextField.getText());
        	
        	//TODO Send packets
        	if (note.equals("")) {
        		//this.noteTextField.setText(NoteUtils.getNoteString(NoteUtils.getBlockNote(this.entityNote)));
                GUI.addNoteReceived(str -> GUI.this.noteTextField.setText(str), this.x, this.y, this.z);
        	} else {
        		int pitch = NoteUtils.parsePitch(note, octave);
        		
        		if (pitch == 12) {
        			//this.noteTextField.setText(NoteUtils.getNoteString(NoteUtils.getBlockNote(this.entityNote)));
                    GUI.addNoteReceived(str -> GUI.this.noteTextField.setText(str), this.x, this.y, this.z);
        		} else {
        			Packet notePacket = new Packet(x, y, z, pitch, "changePitch");
        			NBGUI.network.sendToServer(notePacket);
        			
        			//this.noteTextField.setText(NoteUtils.parseNoteStr(note)); //TODO Get note from server (from the TE)
                    GUI.addNoteReceived(str -> GUI.this.noteTextField.setText(str), this.x, this.y, this.z);
        		
        			//DEBUG
        			/*System.out.println("[DEBUG] [GUI] Note Str: "+note);
        			System.out.println("[DEBUG] [GUI] Octave Str: "+octave);
        			System.out.println("[DEBUG] [GUI] Note Parse1: "+NoteUtils.parseNote(note));
        			System.out.println("[DEBUG] [GUI] Note Parse2: "+NoteUtils.parseNoteInt(note));
        			System.out.println("[DEBUG] [GUI] Note Parse3: "+NoteUtils.parseNoteStr(note));
        			System.out.println("[DEBUG] [GUI] Note Parse4: "+pitch);
        			System.out.println("[DEBUG] [GUI] notePacket: "+notePacket);*/
        		}
        	}
        
    	} else if (keyCode == 28 && this.octaveTextField.isFocused()) {
        	this.octaveTextField.setFocused(false);
        	
			//TODO Send packets
        	if (this.octaveTextField.getText().equals("") ) {
        		//this.octaveTextField.setText(NoteUtils.getOctaveString(NoteUtils.getBlockOctave(this.entityNote)));
                GUI.addOctaveReceived(str -> GUI.this.octaveTextField.setText(str), this.x, this.y, this.z);
        	} else {
            	String note = this.noteTextField.getText();
            	int octave = Integer.parseInt(this.octaveTextField.getText());
            	int octaveParsed = NoteUtils.parseOctave(note, octave);
            	
            	if (octaveParsed == 0) {
            		System.out.println("[DEBUG] [GUI] Octave noteStr: "+note);
            		//this.octaveTextField.setText(NoteUtils.getOctaveString(NoteUtils.getBlockOctave(this.entityNote)));
                    GUI.addOctaveReceived(str -> GUI.this.octaveTextField.setText(str), this.x, this.y, this.z);
            	} else {
            		int pitch = NoteUtils.parsePitch(note, octave);
            		
                	Packet octavePacket = new Packet(x, y, z, pitch, "changePitch");
                	NBGUI.network.sendToServer(octavePacket);
                	
                	//this.octaveTextField.setText(Integer.toString(octaveParsed)); //TODO Get octave from server (from TE)
                    GUI.addOctaveReceived(str -> GUI.this.octaveTextField.setText(str), this.x, this.y, this.z);
                	
            		//DEBUG
            		/*System.out.println("[DEBUG] [GUI] Note Str: "+note);
            		System.out.println("[DEBUG] [GUI] Octave Str: "+this.octaveTextField.getText());
            		System.out.println("[DEBUG] [GUI] Octave Int: "+octave);
            		System.out.println("[DEBUG] [GUI] Octave Parsed: "+octaveParsed);
            		System.out.println("[DEBUG] [GUI] Note Parse1: "+NoteUtils.parseNote(note));
            		System.out.println("[DEBUG] [GUI] Note Parse2: "+NoteUtils.parseNoteInt(note));
            		System.out.println("[DEBUG] [GUI] Note Parse3: "+NoteUtils.parseNoteStr(note));
            		System.out.println("[DEBUG] [GUI] Note Parse4: "+NoteUtils.parsePitch(note, octave));
            		System.out.println("[DEBUG] [GUI] Octave Packet: "+octavePacket);*/
            	}
        	}
    	}   	
    }
    
    @Override
    public void mouseClicked(int x, int y, int clickedButon) throws IOException {
    	super.mouseClicked(x, y, clickedButon);
    	this.noteTextField.mouseClicked(x, y, clickedButon);
    	this.octaveTextField.mouseClicked(x, y, clickedButon);
    	if (this.noteTextField.isFocused()) {
    		if (!this.noteTextField.getText().equals("")) {
    			
    		} else {
    		this.noteTextField.setText("");
    		}
    	} else if (this.octaveTextField.isFocused()) {
    		if (!this.octaveTextField.getText().equals("")) {
    			
    		} else {
    		this.octaveTextField.setText("");
    		}
    	} 
    	if (!this.noteTextField.isFocused()) {
    		//this.noteTextField.setText(NoteUtils.getNoteString(NoteUtils.getBlockNote(this.entityNote)));
    	} else if (!this.octaveTextField.isFocused()) {
    		//this.octaveTextField.setText(NoteUtils.getOctaveString(NoteUtils.getBlockOctave(this.entityNote)));
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
    			Packet playNote = new Packet(x, y, z, 0, "playPitch");
    			//playNote.setText("play");
    			NBGUI.network.sendToServer(playNote);
                GUI.addNoteReceived(System.out::println, this.x, this.y, this.z);
//    			System.out.println(getNoteString());
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