package com.mark.nbgui.packet;

import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandler implements IMessageHandler<Packet, Packet> {
	@Override
	public Packet onMessage(final Packet packet, MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				TileEntityNote noteBlock = packet.entity;
				//noteBlock.readFromNBT(packet.compound);
				noteBlock.triggerNote(noteBlock.getWorld(), noteBlock.getPos());
				//noteBlock.getDescriptionPacket()

				/*String text = packet.compound.getString("____NBGUIMsg");
				if (text.startsWith("PITCH_")) {
					int pitch = Integer.parseInt(text.replace("PITCH_", ""));
					noteBlock.note = (byte) pitch;
				} else if (text.equals("play")) {
					noteBlock.triggerNote(noteBlock.getWorld(), noteBlock.getPos());
				}*/
			}
		});
		return null;
	}

}