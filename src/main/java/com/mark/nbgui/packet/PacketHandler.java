package com.mark.nbgui.packet;

import com.mark.nbgui.nbgui;
import com.mark.nbgui.data.Pitch;
import com.mark.nbgui.utils.NotePlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandler implements IMessageHandler<Packet, Packet> {
	@Override
	public Packet onMessage(final Packet packet, final MessageContext ctx) {
		final IThreadListener mainThread = (WorldServer) ctx.getServerHandler
				().playerEntity.worldObj;
		mainThread.addScheduledTask(() -> {
			World world = (World) mainThread;
			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			BlockPos pos = new BlockPos(packet.x, packet.y, packet.z);
			TileEntityNote noteBlock = (TileEntityNote) world.getTileEntity
					(pos);

			switch (packet.instruction) {
				case PlayPitch:
					new NotePlayer(noteBlock, pos, world).play();
					break;
				case ChangePitch:
					noteBlock.note = (byte) packet.pitch.getNum();
					nbgui.network.sendTo(new PacketClient(new Pitch(noteBlock
							.note)), player);
					break;
				case GetPitch:
					nbgui.network.sendTo(new PacketClient(new Pitch(noteBlock
							.note)), player);
					break;
			}
		});
		return null;
	}
}