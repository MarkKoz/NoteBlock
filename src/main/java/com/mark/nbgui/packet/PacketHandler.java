package com.mark.nbgui.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandler implements IMessageHandler<Packet, Packet> {
	@Override
	public Packet onMessage(final Packet packet, final MessageContext ctx) {
		final IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				World world = (World) mainThread;
				BlockPos pos = new BlockPos(packet.x, packet.y, packet.z);
				TileEntityNote noteBlock = (TileEntityNote) world.getTileEntity(pos);
				System.out.println("[DEBUG] PH World: "+world);
				System.out.println("[DEBUG] PH Pos: "+pos);
				//noteBlock.readFromNBT(packet.compound);
				noteBlock.triggerNote(world, pos);
				//noteBlock.getDescriptionPacket()
				
				//DEBUG
				System.out.println("[DEBUG] PH getWorld: "+noteBlock.getWorld());
				System.out.println("[DEBUG] PH getPos: "+noteBlock.getPos());
				System.out.println("[DEBUG] PH note: "+noteBlock.note);

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