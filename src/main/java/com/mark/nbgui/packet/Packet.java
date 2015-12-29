package com.mark.nbgui.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class Packet implements IMessage {
    //protected NBTTagCompound compound;
    protected int x;
    protected int y;
    protected int z;

    public Packet() {
        //this.compound = new NBTTagCompound();
    }

    public Packet(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        		 	
    	//this();
        //entityNote.writeToNBT(this.compound);
    }

    /*public void setText(String text) {
        this.compound.setString("____NBGUIMsg", text);
    }

    public void setPitch(int pitch) {
        this.compound.setString("____NBGUIMsg", "PITCH_" + pitch);
    }*/

    @Override
    public void fromBytes(ByteBuf buf) {
        //this.compound = ByteBufUtils.readTag(buf);
    	x = buf.readInt();
    	y = buf.readInt();
    	z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        //ByteBufUtils.writeTag(buf, this.compound);
    	buf.writeInt(x);
    	buf.writeInt(y);
    	buf.writeInt(z);
    }
}