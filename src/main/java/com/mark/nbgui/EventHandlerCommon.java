package com.mark.nbgui;

import com.mark.nbgui.packet.Packet;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandlerCommon {	
	
    /*@SubscribeEvent
    public void noteBlockChange(NoteBlockEvent.Change event) {
        event.setCanceled(true);
        //CHANGE EVENT
    }*/

    /*@SubscribeEvent
    public void noteBlockPlay(NoteBlockEvent.Play event) {
        //PLAY OVERRIDDEN
    }*/
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
    	Minecraft mc = Minecraft.getMinecraft();
        if (KeyBindings.returnInput.isPressed() && FMLClientHandler.instance().getClient().inGameHasFocus) {
        	System.out.println("[DEBUG] Return Constant");
        	System.out.println("[DEBUG] KeyCode: "+KeyBindings.returnInput.getKeyCode());
        	System.out.println("[DEBUG] KeyCodeDefault: "+KeyBindings.returnInput.getKeyCodeDefault());
			/*if (mc.currentScreen instanceof GUI) {
    			//GUI.thing = true;
    			System.out.println("[DEBUG] Return Constant Screen Checked");
    		}*/
        }
    }

	@SubscribeEvent
    public void playerInteract(PlayerInteractEvent event) {
    	if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
    		World world = event.world;
    		Block block = world.getBlockState(event.pos).getBlock();
    		if (block.equals(Blocks.noteblock)) {
                event.entityPlayer.openGui(NBGUI.instance, GUI.GUI_ID, world, event.pos.getX(), event.pos.getY(), event.pos.getZ());
                
                event.useBlock = Event.Result.DENY;
                event.setCanceled(true);
    		}
    	}
    }
}