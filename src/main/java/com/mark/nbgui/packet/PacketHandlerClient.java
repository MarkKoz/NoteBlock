package com.mark.nbgui.packet;

import com.mark.nbgui.GUI;

import com.mark.nbgui.IStringReceived;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandlerClient implements IMessageHandler<PacketClient, PacketClient> {
	@Override
	public PacketClient onMessage(final PacketClient packet, final MessageContext ctx) {
		for (IStringReceived o : GUI.notesReceived) {
			o.received(packet.note);
		}
		GUI.notesReceived.clear();

		for (IStringReceived o : GUI.octavesReceived) {
			o.received(packet.octave);
		}
		GUI.octavesReceived.clear();
		
		System.out.println("[DEBUG] [ClientPH] note: " + packet.note);
		System.out.println("[DEBUG] [ClientPH] octave: " + packet.octave);
//		System.out.println("[DEBUG] [ClientPH] noteStr: " + GUI.noteStr);
//		System.out.println("[DEBUG] [ClientPH] octaveStr: " + GUI.octaveStr);
		return null;
	}
}