package com.mark.nbgui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerCommon {
    @SubscribeEvent
    public void noteBlockChange(NoteBlockEvent.Change event) {
        event.setCanceled(true);
        //CHANGE EVENT
    }

    @SubscribeEvent
    public void noteBlockPlay(NoteBlockEvent.Play event) {
        //PLAY OVERRIDDEN
    }

	@SubscribeEvent
    public void playerInteract(PlayerInteractEvent event) {
    	if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
    		World world = event.world;
    		Block block = world.getBlockState(event.pos).getBlock();
    		if (block.equals(Blocks.noteblock)) {
                event.entityPlayer.openGui(NBGUI.instance,
                        GUI.GUI_ID, world,
                        event.pos.getX(),
                        event.pos.getY(),
                        event.pos.getZ());

                event.useBlock = Event.Result.DENY;
    		}
    		//DEBUG
    		System.out.println("POS: " + event.pos);
    		System.out.println("BLOCK: " + block);
    	}
    }
}
