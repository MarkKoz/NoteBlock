package com.mark.nbgui.data;

import net.minecraftforge.event.world.NoteBlockEvent;

public enum Note {
    F_SHARP,
    G,
    G_SHARP,
    A,
    A_SHARP,
    B,
    C,
    C_SHARP,
    D,
    D_SHARP,
    E,
    F;

    public static int MAX_NOTE = 12;

    private static Note[] values = Note.values();

    public static Note fromBlockNote(NoteBlockEvent.Note note) {
        return Note.values[note.ordinal() % Note.values.length];
    }

    public static Note fromPitch(Pitch pitch) {
        return Note.values[pitch.getNum() % Note.values.length];
    }

    public static Note parse(String note) {
        note = note.replaceAll("\\s+", "");
        note = note.replaceAll("_", "");
        note = note.replaceAll("#", "sharp");
        note = note.replaceAll("♯", "sharp");

        boolean sharp = false;

        if (note.contains("sharp")) {
            note = note.replaceAll("sharp", "");
            sharp = true;
        }

        note = note.toUpperCase();

        String noteFinal = note.substring(0, 1);

        if (sharp) {
            noteFinal += "_SHARP";
        }

        return Note.fromString(noteFinal);
    }

    public static Note fromString(String strNote) {
        for (Note note : Note.values) {
            if (note.name().equals(strNote)) {
                return note;
            }
        }

        return null;
    }

    public String getName() {
        return this.name().replaceAll("_SHARP", "♯");
    }

    public Note add(int factor) {
        int index = Math.floorMod((this.ordinal() + factor), Note.values.length);
        return Note.values[index];
    }
}
