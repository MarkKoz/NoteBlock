package com.mark.nbgui.packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketClient implements IMessage {
	
	protected String note;
	protected String octave;
	
	public PacketClient () {
		
	}
	
	public PacketClient (String note, String octave) {
		this.note = note;
		this.octave = octave;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		note = ByteBufUtils.readUTF8String(buf);
		octave = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, note);
		ByteBufUtils.writeUTF8String(buf, octave);
	}
}
