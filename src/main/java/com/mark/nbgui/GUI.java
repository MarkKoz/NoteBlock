package com.mark.nbgui;

import net.minecraft.block.BlockNote;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.network.internal.FMLMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

public class GUI extends GuiScreen {
	public final static int GUI_ID = 20;

    private static String instrumentText = "Instrument: {instrument}";
    private static String pitchText = "Pitch: {pitch}";
    private static String octaveText = "Octave: {octave}";

    private TileEntityNote entityNote;
    private BlockNote blockNote;

    public GUI(EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        this.entityNote = (TileEntityNote) world.getTileEntity(pos);
        this.blockNote = (BlockNote) world.getBlockState(pos).getBlock();
    }
	
	@Override
    public void drawScreen(int x, int y, float f) {
			//EventHandlerCommon.openGUI(event);
			//String pitch = d;
			
			//Draw GUI
			this.drawDefaultBackground();
			super.drawScreen(x, y, f);
            this.drawCenteredString(this.fontRendererObj,
                    GUI.instrumentText.replace("{instrument}",
                            NoteUtils.getNoteBlockInstrument(this.blockNote).name()),
                    this.width / 2, 40, 0xFFFFFFFF);
            this.drawCenteredString(this.fontRendererObj,
                    GUI.pitchText.replace("{pitch}", NoteUtils.getNoteString(
                            NoteUtils.getBlockNote(this.entityNote))),
                    this.width / 2, 50, 0xFFFFFFFF);
            this.drawCenteredString(this.fontRendererObj,
                    GUI.octaveText.replace("{octave}",
                            NoteUtils.getBlockOctave(this.entityNote).name()),
                    this.width / 2, 60, 0xFFFFFFFF);
            
    }
	
	@Override
	public void initGui() {
		// DEBUG
	    System.out.println("GUI initGUI()");

        GuiButton button = new GuiButton(1,this.width / 2, 90, 60, 20, "Play");
		this.buttonList.add(button);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
			case 1:
				System.out.println("Play button clicked");
                NBGUI.network.sendToServer(new Message(entityNote, "play"));
                NBGUI.network.sendToServer(new Message(entityNote, NoteBlockEvent.Note.A_SHARP));
                NBGUI.network.sendToServer(new Message(entityNote, NoteBlockEvent.Octave.HIGH));
				break;
		}
	}

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}