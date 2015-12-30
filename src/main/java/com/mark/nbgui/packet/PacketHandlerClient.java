package com.mark.nbgui.packet;

import com.mark.nbgui.gui.GUI;

import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandlerClient implements IMessageHandler<PacketClient, PacketClient> {
	@Override
	public PacketClient onMessage(final PacketClient packet, final MessageContext ctx) {
		for (IStringReceived o : GUI.pitchReceived) {
			o.received(packet.pitch);
		}
		GUI.pitchReceived.clear();

		return null;
	}
}