package com.mark.nbgui;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNote;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class EventHandlerCommon {

    @SubscribeEvent
    public void noteBlockChange(NoteBlockEvent.Change event) {
        //CHANGE EVENT
    }

    @SubscribeEvent
    public void noteBlockPlay(NoteBlockEvent.Play event) {
        event.setCanceled(true);

        //PLAY OVERRIDDEN
    }
	
	@SubscribeEvent
    public void openGUI(PlayerInteractEvent event) {
    	if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK){
    		World world = event.world;
    		Block block = world.getBlockState(event.pos).getBlock();
    		EntityPlayer player = event.entityPlayer;
    		if (block.equals(Blocks.noteblock)) {
                TileEntity tileentity = world.getTileEntity(event.pos);

                if (tileentity instanceof TileEntityNote) {
                    TileEntityNote tileentitynote = (TileEntityNote) tileentity;
                    tileentitynote.invalidate();
                }
    			player.openGui(NBGUI.instance,
                        GUI.GUI_ID, world,
                        event.pos.getX(),
                        event.pos.getY(),
                        event.pos.getZ());
    		}
    		//DEBUG
    		System.out.println("POS: " +event.pos);
    		System.out.println("BLOCK: " +block);
    		
    		//Note pitch = NoteBlockEvent.getNote();
    		//String note = d;
    	}
    }
}
