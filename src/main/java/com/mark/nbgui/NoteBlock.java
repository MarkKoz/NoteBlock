package com.mark.nbgui;

import net.minecraft.block.BlockNote;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class NoteBlock extends BlockNote {
	
    /**@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote) {
    	playerIn.openGui(NBGUI.instance, GUI.GUI_ID, worldIn, 10, 10, 10);
    }  
    return true;
    }*/
}
