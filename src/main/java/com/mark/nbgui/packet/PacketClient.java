package com.mark.nbgui.packet;

import com.mark.nbgui.data.Pitch;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketClient implements IMessage {
	protected Pitch pitch;

	public PacketClient () {
	}
	
	public PacketClient (Pitch pitch) {
		this.pitch = pitch;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.pitch = new Pitch(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
        buf.writeInt(this.pitch.getNum());
	}
}
