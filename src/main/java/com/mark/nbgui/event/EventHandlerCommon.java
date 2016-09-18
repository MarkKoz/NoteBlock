package com.mark.nbgui.event;

import com.mark.nbgui.nbgui;
import com.mark.nbgui.gui.GUI;
import com.mark.nbgui.gui.KeyBindings;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

/*	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (KeyBindings.Return.isPressed() && FMLClientHandler.instance()
				.getClient().inGameHasFocus) {
			System.out.println("[DEBUG] Return Constant");
			System.out.println("[DEBUG] KeyCode: " + KeyBindings.Return
					.getKeyCode());
			System.out.println("[DEBUG] KeyCodeDefault: " + KeyBindings
					.Return.getKeyCodeDefault());
			if (mc.currentScreen instanceof GUI) {
				//GUI.thing = true;
    			System.out.println("[DEBUG] Return Constant Screen Checked");
    		}
		}
	}*/

	@SubscribeEvent
	public void playerInteract(PlayerInteractEvent event) {
		if (event instanceof PlayerInteractEvent.RightClickBlock) {
			World world = event.getWorld();
			Block block = world.getBlockState(event.getPos()).getBlock();
			if (block.equals(Blocks.NOTEBLOCK)) {
				event.getEntityPlayer().openGui(nbgui.instance, GUI.GUI_ID,
						world, event.getPos()
								.getX(), event.getPos().getY(), event.getPos()
								.getZ());

				((PlayerInteractEvent.RightClickBlock) event).setUseBlock
						(Event.Result.DENY);
				//event.setCanceled(true);
			}
		}
	}
}