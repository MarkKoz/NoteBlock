package com.mark.nbgui;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class Message implements IMessage {
    private String text;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public Message(NoteBlockEvent.Note note) {
        this.text = "NOTE_" + note.name();
    }

    public Message(NoteBlockEvent.Octave octave) {
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
                    System.out.println("Setting note to " + note);
                }
            } else if (message.text.startsWith("OCTAVE_")) {
                NoteBlockEvent.Octave octave = NoteBlockEvent.Octave.valueOf(message.text.substring(7));
                if (octave != null) {
                    System.out.println("Setting octave to " + octave);
                }
            } else if (message.text.equals("play")) {
                System.out.println("SERVER: Play pressed");
            }
            return null;
        }
    }
}
