package com.mark.nbgui;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class Message implements IMessage {
    private String text;

    public Message() {
    }

    public Message(TileEntityNote noteEntity, String text) {
        this.text = text;
    }

    public Message(TileEntityNote noteEntity, NoteBlockEvent.Note note) {
        this.text = "NOTE_" + NoteUtils.noteToInt(note);
    }

    public Message(TileEntityNote noteEntity, NoteBlockEvent.Octave octave) {
        this.text = "OCTAVE_" + octave.name();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        text = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.text);
    }

    public static class Handler implements IMessageHandler<Message, IMessage> {
        @Override
        public IMessage onMessage(Message message, MessageContext ctx) {
            if (message.text.startsWith("NOTE_")) {
                NoteBlockEvent.Note note = NoteBlockEvent.Note.valueOf(message.text.substring(5));
                if (note != null) {
                }
            } else if (message.text.startsWith("OCTAVE_")) {
                NoteBlockEvent.Octave octave = NoteBlockEvent.Octave.valueOf(message.text.substring(7));
                if (octave != null) {
                }
            } else if (message.text.equals("play")) {
                System.out.println("SERVER: Play pressed");
            }
            return null;
        }
    }
}
