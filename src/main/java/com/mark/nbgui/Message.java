package com.mark.nbgui;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class Message implements IMessage {
    private NBTTagCompound compound;

    public Message() {
        this.compound = new NBTTagCompound();
    }

    public Message(TileEntityNote entityNote) {
        this();
        entityNote.writeToNBT(this.compound);
    }

    public void setText(String text) {
        this.compound.setString("____NBGUIMsg", text);
    }

    public void setPitch(int pitch) {
        this.compound.setString("____NBGUIMsg", "PITCH_" + pitch);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.compound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.compound);
    }

    public static class Handler implements IMessageHandler<Message, IMessage> {
        @Override
        public IMessage onMessage(final Message message, MessageContext ctx) {
            IThreadListener mainThread
                    = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    TileEntityNote noteBlock = new TileEntityNote();
                    noteBlock.readFromNBT(message.compound);

                    String text = message.compound.getString("____NBGUIMsg");
                    if (text.startsWith("PITCH_")) {
                        int pitch = Integer.parseInt(text.replace("PITCH_", ""));
                        noteBlock.note = (byte) pitch;
                    } else if (text.equals("play")) {
                        noteBlock.triggerNote(Minecraft.getMinecraft().theWorld, noteBlock.getPos());
                    }
                }
            });
            return null;

        }
    }
}
