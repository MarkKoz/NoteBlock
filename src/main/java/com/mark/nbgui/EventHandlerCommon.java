package com.mark.nbgui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerCommon {
	
	
	@SubscribeEvent
    public void openGUI(PlayerInteractEvent event) {
    	if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK){
    		World world = event.world;
    		Block block = world.getBlockState(event.pos).getBlock();
    		String blocktype = block.toString();
    		EntityPlayer player = event.entityPlayer;
    		if (blocktype.equals("Block{minecraft:noteblock}")) {
    			player.openGui(NBGUI.instance, GUI.GUI_ID, world, event.pos.getX(), event.pos.getY(), event.pos.getZ());
    		}
    		//DEBUG
    		System.out.println("POS: " +event.pos);
    		System.out.println("BLOCK: " +block);
    		System.out.print(blocktype);
    	}
    }
}
