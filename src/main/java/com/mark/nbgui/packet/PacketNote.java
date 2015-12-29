package com.mark.nbgui.packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketNote implements IMessage {
	protected int note;
	
    public PacketNote() {
    }

    public PacketNote(int note) {
    this.note = note;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	note = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(note);
    }
}