package com.mark.nbgui;

import net.minecraft.block.BlockNote;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;

public class NoteUtils {
    private static NoteBlockEvent.Instrument[] instruments
            = NoteBlockEvent.Instrument.values();

    private static NoteBlockEvent.Note[] notes
            = NoteBlockEvent.Note.values();

    private static NoteBlockEvent.Octave[] octaves
            = NoteBlockEvent.Octave.values();

    public static String getNoteString(NoteBlockEvent.Note note) {
        switch (note) {
            case F_SHARP:
                return "F♯";
            case G:
                return "G";
            case G_SHARP:
                return "G♯";
            case A:
                return "A";
            case A_SHARP:
                return "A♯";
            case B:
                return "B";
            case C:
                return "C";
            case C_SHARP:
                return "C♯";
            case D:
                return "D";
            case D_SHARP:
                return "D♯";
            case E:
                return "E";
            case F:
                return "F♯";
        }
        return "";
    }

    public static int noteToInt(NoteBlockEvent.Note note) {
        return 0;
    }

    public static NoteBlockEvent.Instrument getNoteBlockInstrument(BlockNote block) {
        if (block == null) {
            return NoteBlockEvent.Instrument.PIANO;
        }
        Material material = block.getMaterial();
        byte id = 0;
        if (material == Material.rock) {
            id = 1;
        } else if (material == Material.sand) {
            id = 2;
        } else if (material == Material.glass) {
            id = 3;
        } else if (material == Material.wood) {
            id = 4;
        }
        return NoteUtils.instruments[id];
    }

    public static NoteBlockEvent.Note getBlockNote(TileEntityNote entityNote) {
        byte note = entityNote.note;

        return NoteUtils.notes[note % 12];
    }

    public static NoteBlockEvent.Octave getBlockOctave(TileEntityNote entityNote) {
        byte note = entityNote.note;
        NoteBlockEvent.Octave octave = note < 12
                ? NoteBlockEvent.Octave.LOW : note == 24
                ? NoteBlockEvent.Octave.HIGH : NoteBlockEvent.Octave.MID;
        return octave;
    }
}
