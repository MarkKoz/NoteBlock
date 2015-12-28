package com.mark.nbgui.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class Packet implements IMessage {
    protected NBTTagCompound compound;
    protected static TileEntityNote entity;

    public Packet() {
        //this.compound = new NBTTagCompound();
    	//this.entity = new TileEntityNote();
    }

    public Packet(TileEntityNote entityNote) {
        //this();
        //entityNote.writeToNBT(this.compound);
    	entity = entityNote;
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
    }

    @Override
    public void toBytes(ByteBuf buf) {
        //ByteBufUtils.writeTag(buf, this.compound);
    }
}