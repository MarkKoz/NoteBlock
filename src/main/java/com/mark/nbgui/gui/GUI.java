package com.mark.nbgui.gui;

import com.mark.nbgui.NBGUI;
import com.mark.nbgui.data.Instrument;
import com.mark.nbgui.data.Note;
import com.mark.nbgui.data.Octave;
import com.mark.nbgui.data.Pitch;
import com.mark.nbgui.packet.IStringReceived;
import com.mark.nbgui.packet.Instruction;
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
    private static final int ENTER_KEY_CODE = 28;

    private static String instrumentText = I18n.format("nbgui.string.gui.instrument") +" {instrument}";
    private static String noteText = I18n.format("nbgui.string.gui.note") + " {note}";
    private static String octaveText = I18n.format("nbgui.string.gui.octave") + " {octave}";

    private int x;
    private int y;
    private int z;
    private Block underBlock;

    private Pitch currentPitch;
    
    private GuiTextField noteTextField;
    private GuiTextField octaveTextField;
    private GuiTextField pitchTextField;

	public static List<IStringReceived> pitchReceived = new ArrayList<IStringReceived>();

    private void updateNote() {
        NBGUI.network.sendToServer(new Packet(this.x, this.y, this.z, Instruction.GetStrings));
        GUI.pitchReceived.add(pitch -> this.noteTextField.setText(pitch.getNote().getName()));
    }

    private void updateOctave() {
        NBGUI.network.sendToServer(new Packet(this.x, this.y, this.z, Instruction.GetStrings));
        GUI.pitchReceived.add(pitch -> this.octaveTextField.setText(pitch.getOctave().getName()));
    }

    private void changePitch(Pitch pitch) {
        NBGUI.network.sendToServer(new Packet(x, y, z, pitch, Instruction.ChangePitch));
        GUI.pitchReceived.add(newPitch -> {
            this.currentPitch = newPitch;
            this.noteTextField.setText(pitch.getNote().getName());
            this.octaveTextField.setText(pitch.getOctave().getName());
        });
    }

    public GUI(EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;

        this.underBlock = world.getBlockState(pos.down()).getBlock();
        this.currentPitch = new Pitch(0);
    }
    
	@Override
    public void drawScreen(int x, int y, float f) {
		this.drawDefaultBackground();
		super.drawScreen(x, y, f);
			
		this.noteTextField.drawTextBox();
		this.octaveTextField.drawTextBox();
			
        this.drawCenteredString(this.fontRendererObj,
                GUI.instrumentText.replace("{instrument}",
                        Instrument.get(this.underBlock).translate()),
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
		GuiButton playButton = new GuiButton(1, this.width / 2 - 30, 90, 60, 20, I18n.format("nbgui.button.playNote"));
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
		this.noteTextField = new GuiTextField(2, fontRendererObj, this.width / 2 - 30, 130, 60, 20);
		this.noteTextField.setFocused(false);
		this.noteTextField.setCanLoseFocus(true);
        this.updateNote();
		this.noteTextField.setTextColor(-1);
		this.noteTextField.setDisabledTextColour(-1);
		this.noteTextField.setEnableBackgroundDrawing(true);
		this.noteTextField.setMaxStringLength(7);
		
		//Octave Text Field
		this.octaveTextField = new GuiTextField(3, fontRendererObj, this.width / 2 - 30, 160, 60, 20);
		this.octaveTextField.setFocused(false);
		this.octaveTextField.setCanLoseFocus(true);
		this.octaveTextField.setTextColor(-1);
		this.octaveTextField.setDisabledTextColour(-1);
		this.octaveTextField.setEnableBackgroundDrawing(true);
		this.octaveTextField.setMaxStringLength(1);
        this.updateOctave();

        //Pitch Text Field
        this.pitchTextField = new GuiTextField(4, fontRendererObj, this.width / 2 - 30, 190, 60, 20);
        this.pitchTextField.setFocused(false);
        this.pitchTextField.setCanLoseFocus(true);
        this.pitchTextField.setTextColor(-1);
        this.pitchTextField.setDisabledTextColour(-1);
        this.pitchTextField.setEnableBackgroundDrawing(true);
        this.pitchTextField.setMaxStringLength(1);
    }
	
    @Override
    public void keyTyped(char character, int keyCode) throws IOException {
    	super.keyTyped(character, keyCode);
    	this.noteTextField.textboxKeyTyped(character, keyCode);
    	this.octaveTextField.textboxKeyTyped(character, keyCode);
        pitchTextField.textboxKeyTyped(character, keyCode);

        if (keyCode == ENTER_KEY_CODE) {
            if (this.noteTextField.isFocused()) {
                Note note = Note.fromString(this.noteTextField.getText());
                if (note != null) {
                    this.changePitch(new Pitch(note, this.currentPitch.getOctave()));
                }
            }

            if (this.octaveTextField.isFocused()) {
                Octave octave = Octave.fromNum(Integer.parseInt(this.octaveTextField.getText()));
                if (octave != null) {
                    this.changePitch(new Pitch(this.currentPitch.getNote(), octave));
                }
            }

            if (this.pitchTextField.isFocused()) {
                Pitch pitch = Pitch.parsePitch(this.pitchTextField.getText());

                if (pitch != null) {
                    this.changePitch(pitch);
                }
            }
        }

    	/*if (keyCode == 28 && this.noteTextField.isFocused()) {
        	this.noteTextField.setFocused(false);

        	String note = this.noteTextField.getText();
        	int octave = Integer.parseInt(this.octaveTextField.getText());

        	//TODO Send packets
        	if (!note.isEmpty()) {
        		this.updateNote();
        	} else {
        		int pitch = NoteUtils.parsePitch(note, octave);

        		if (pitch == 12) {
                    this.updateNote();
        		} else {
        			Packet notePacket = new Packet(x, y, z, pitch, "changePitch");
        			NBGUI.network.sendToServer(notePacket);

        			this.updateNote();
        		}
        	}

    	} else if (keyCode == 28 && this.octaveTextField.isFocused()) {
        	this.octaveTextField.setFocused(false);

			//TODO Send packets
        	if (this.octaveTextField.getText().equals("") ) {
                this.updateOctave();
        	} else {
            	String note = this.noteTextField.getText();
            	int octave = Integer.parseInt(this.octaveTextField.getText());
            	int octaveParsed = NoteUtils.parseOctave(note, octave);

            	if (octaveParsed == 0) {
            		System.out.println("[DEBUG] [GUI] Octave noteStr: "+note);
                    this.updateOctave();
            	} else {
            		int pitch = NoteUtils.parsePitch(note, octave);

                	Packet octavePacket = new Packet(x, y, z, pitch, "changePitch");
                	NBGUI.network.sendToServer(octavePacket);
                    this.updateOctave();
            	}
        	}
    	}*/
    }

    @Override
    public void mouseClicked(int x, int y, int clickedButton) throws IOException {
        super.mouseClicked(x, y, clickedButton);
        this.noteTextField.mouseClicked(x, y, clickedButton);
        this.octaveTextField.mouseClicked(x, y, clickedButton);
        this.pitchTextField.mouseClicked(x, y, clickedButton);
/*
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
    			NBGUI.network.sendToServer(new Packet(x, y, z, Instruction.PlayPitch));
    			break;
			case 2: // Note +1
                this.changePitch(this.currentPitch.incrementNote(1));
                break;
			case 3: //Note -1
                this.changePitch(this.currentPitch.incrementNote(-1));
				break;
			case 4: //Octave +1
                this.changePitch(this.currentPitch.incrementOctave(1));
				break;
			case 5: //Octave -1
                this.changePitch(this.currentPitch.incrementOctave(-1));
				break;
		}
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}