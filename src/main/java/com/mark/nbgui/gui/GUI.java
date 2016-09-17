package com.mark.nbgui.gui;

import com.mark.nbgui.nbgui;
import com.mark.nbgui.data.*;
import com.mark.nbgui.packet.IStringReceived;
import com.mark.nbgui.packet.Instruction;
import com.mark.nbgui.packet.Packet;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI extends GuiScreen {
	public final static int GUI_ID = 20;
	private static final int ENTER_KEY_CODE = 28;
	private static final int UP_KEY_CODE = 200;
	private static final int DOWN_KEY_CODE = 208;
	private static final int RIGHT_KEY_CODE = 205;
	private static final int LEFT_KEY_CODE = 203;
	public static List<IStringReceived> pitchReceived = new
			ArrayList<IStringReceived>();
	private static String instrumentText = I18n.format("nbgui.string.gui" +
			".instrument") + " {instrument}";
	private static String noteText = I18n.format("nbgui.string.gui.note") + "" +
			" " +
			"{note}";
	private static String octaveText = I18n.format("nbgui.string.gui.octave")
			+ " {octave}";
	private int x;
	private int y;
	private int z;
	private Block underBlock;
	private Pitch currentPitch;
	private GuiTextField noteTextField;
	private GuiTextField octaveTextField;
	private GuiTextField pitchTextField;
	private GUIErrorLabel error;

	public GUI(EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		this.x = x;
		this.y = y;
		this.z = z;

		this.underBlock = world.getBlockState(pos.down()).getBlock();
	}

	private void updateNote() {
		nbgui.network.sendToServer(new Packet(this.x, this.y, this.z,
				Instruction.GetPitch));
		GUI.pitchReceived.add(pitch -> this.noteTextField.setText(pitch
				.getNote().getName()));
	}

	private void updateOctave() {
		nbgui.network.sendToServer(new Packet(this.x, this.y, this.z,
				Instruction.GetPitch));
		GUI.pitchReceived.add(pitch -> this.octaveTextField.setText(pitch
				.getOctave().getName()));
	}

	private void updatePitch() {
		nbgui.network.sendToServer(new Packet(this.x, this.y, this.z,
				Instruction.GetPitch));
		GUI.pitchReceived.add(pitch -> {
			this.noteTextField.setText(pitch.getNote().getName());
			this.octaveTextField.setText(pitch.getOctave().getName());
			this.currentPitch = pitch;
		});
	}

	private void changePitch(Pitch pitchToSend) {
		if (pitchToSend != null) {
			nbgui.network.sendToServer(new Packet(x, y, z, pitchToSend,
					Instruction.ChangePitch));
			GUI.pitchReceived.add(pitch -> {
				this.currentPitch = pitch;
				this.noteTextField.setText(pitch.getNote().getName());
				this.octaveTextField.setText(pitch.getOctave().getName());
			});
		}
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		this.drawDefaultBackground();
		super.drawScreen(x, y, f);

		this.noteTextField.drawTextBox();
		this.octaveTextField.drawTextBox();
		this.pitchTextField.drawTextBox();

		this.error.draw();

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
		GuiButton playButton = new GuiButton(1, this.width / 2 - 30, 90, 60,
				20, I18n.format("nbgui.button.playNote"));
		this.buttonList.add(playButton);

		this.error = new GUIErrorLabel(this.fontRendererObj, this.width / 2,
				60);

		//Pitch +1 Button
		GuiButton subPitchButton = new GuiButton(2, this.width / 2 + 40, 145,
				20, 20, "+");
		this.buttonList.add(subPitchButton);

		//Pitch -1 Button
		GuiButton addPitchButton = new GuiButton(3, this.width / 2 - 60, 145,
				20, 20, "-");
		this.buttonList.add(addPitchButton);


		//Note Text Field
		this.noteTextField = new GuiTextField(2, this.fontRendererObj, this
				.width / 2 - 30, 130, 60, 20);
		this.noteTextField.setFocused(false);
		this.noteTextField.setCanLoseFocus(true);
		this.noteTextField.setTextColor(-1);
		this.noteTextField.setDisabledTextColour(-1);
		this.noteTextField.setEnableBackgroundDrawing(true);
		this.noteTextField.setMaxStringLength(8);
		this.updateNote();

		//Octave Text Field
		this.octaveTextField = new GuiTextField(3, this.fontRendererObj, this
				.width / 2 - 30, 160, 60, 20);
		this.octaveTextField.setFocused(false);
		this.octaveTextField.setCanLoseFocus(true);
		this.octaveTextField.setTextColor(-1);
		this.octaveTextField.setDisabledTextColour(-1);
		this.octaveTextField.setEnableBackgroundDrawing(true);
		this.octaveTextField.setMaxStringLength(1);
		this.updateOctave();

		//Pitch Text Field
		this.pitchTextField = new GuiTextField(4, this.fontRendererObj, this
				.width / 2 - 30, 190, 60, 20);
		this.pitchTextField.setFocused(false);
		this.pitchTextField.setCanLoseFocus(true);
		this.pitchTextField.setTextColor(-1);
		this.pitchTextField.setDisabledTextColour(-1);
		this.pitchTextField.setEnableBackgroundDrawing(true);
		this.pitchTextField.setMaxStringLength(16);


		this.currentPitch = new Pitch(0);
		this.updatePitch();
	}

	@Override
	public void keyTyped(char character, int keyCode) throws IOException {
		super.keyTyped(character, keyCode);
		this.noteTextField.textboxKeyTyped(character, keyCode);
		this.octaveTextField.textboxKeyTyped(character, keyCode);
		this.pitchTextField.textboxKeyTyped(character, keyCode);

		if (keyCode == ENTER_KEY_CODE) {
			this.error(null, "");
			if (this.noteTextField.isFocused()) {
				Note note = Note.fromString(this.noteTextField.getText());
				if (note != null) {
					Pitch newPitch = Pitch.fromNoteOctave(note, this
							.currentPitch.getOctave());
					if (newPitch != null) {
						this.changePitch(newPitch);
					} else {
						this.error(this.noteTextField, "The entered " +
								"note/octave is not supported.");
					}
				} else {
					this.error(this.noteTextField, "The entered note could " +
							"not" +
							" be parsed.");
				}
				this.noteTextField.setFocused(false);
			}

			if (this.octaveTextField.isFocused()) {
				Octave octave = Octave.fromNum(Integer.parseInt(this
						.octaveTextField.getText()));
				if (octave != null) {
					Pitch newPitch = Pitch.fromNoteOctave(this.currentPitch
							.getNote(), octave);
					if (newPitch != null) {
						this.changePitch(newPitch);
					} else {
						this.error(this.octaveTextField, "The entered " +
								"note/octave is not supported.");
					}
				} else {
					this.error(this.octaveTextField, "The entered octave " +
							"could" +
							" not be parsed.");
				}
				this.octaveTextField.setFocused(false);
			}

			if (this.pitchTextField.isFocused()) {
				NoteOctavePair pair = NoteOctavePair.parseNoteOctave(this
						.pitchTextField.getText());

				if (pair != null) {
					Pitch pitch = Pitch.fromNoteOctave(pair);
					if (pitch != null) {
						this.changePitch(pitch);
					} else {
						this.error(this.pitchTextField, "The entered " +
								"note/octave is not supported.");
					}
				} else {
					this.error(this.pitchTextField, "The entered note/octave" +
							" " +
							"could not be parsed.");
				}
				this.pitchTextField.setFocused(false);
			}
		} else if (keyCode == UP_KEY_CODE || keyCode == RIGHT_KEY_CODE) {
			this.changePitch(this.currentPitch.increment(1));
		} else if (keyCode == DOWN_KEY_CODE || keyCode == LEFT_KEY_CODE) {
			this.changePitch(this.currentPitch.increment(-1));
		}
	}

	private void error(GuiTextField field, String error) {
		if (field == null) {
			this.error.disable();
		} else if (field.equals(this.noteTextField)) {
			this.noteTextField.setText(this.currentPitch.getNote().toString());
		} else if (field.equals(this.octaveTextField)) {
			this.octaveTextField.setText(this.currentPitch.getOctave()
					.toString());
		}
		this.error.setError(error);
	}

	@Override
	public void mouseClicked(int x, int y, int clickedButton) throws
			IOException {
		super.mouseClicked(x, y, clickedButton);
		this.noteTextField.mouseClicked(x, y, clickedButton);
		this.octaveTextField.mouseClicked(x, y, clickedButton);
		this.pitchTextField.mouseClicked(x, y, clickedButton);
	}

	@Override
	public void updateScreen() {
		this.noteTextField.updateCursorCounter();
		this.octaveTextField.updateCursorCounter();
		this.pitchTextField.updateCursorCounter();
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
				nbgui.network.sendToServer(new Packet(x, y, z, Instruction
						.PlayPitch));
				break;
			case 2: // Pitch +1
				if (this.currentPitch.getNoteOctavePair().equals(new
						NoteOctavePair(Note.F_SHARP, Octave.FIVE))) {
					this.changePitch(this.currentPitch.increment(2));
				} else {
					this.changePitch(this.currentPitch.increment(1));
				}
				break;
			case 3: //Pitch -1
				if (this.currentPitch.getNoteOctavePair().equals(new
						NoteOctavePair(Note.F_SHARP, Octave.THREE))) {
					this.changePitch(this.currentPitch.increment(-2));
				} else {
					this.changePitch(this.currentPitch.increment(-1));
				}
				break;
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}