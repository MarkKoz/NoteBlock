package com.mark.nbgui.data;

/**
 * An enum that represents a note
 * @author Nima, Mark
 * @version 1.0.0
 */
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

    private static Note[] values = Note.values();

    public static int MAX_NOTE = Note.values.length;

    /**
     * Returns the name of this Note
     * @return The name of this Note
     */
    public String getName() {
        return this.name().replaceAll("_SHARP", "♯");
    }

    /**
     * Increments this note by a given factor
     * @param factor The factor to increment this Note by
     * @return The incremented Note
     */
    public Note add(int factor) {
        int index = Math.floorMod((this.ordinal() + factor), Note.MAX_NOTE);
        return Note.values[index];
    }

    /**
     * Creates a new Note object from a Pitch object
     * @param pitch The Pitch object
     * @return The Note object created
     */
    public static Note fromPitch(Pitch pitch) {
        return Note.values[pitch.getNum() % Note.MAX_NOTE];
    }

    /**
     * Parses a note string and produces a Note object
     * @param note The string that contains the note.
     * @return The note parsed from the string.
     */
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

    /**
     * Converts a Note enum name to a Note object
     * (e.g. from F_SHARP into a Note object that represents
     * F#)
     * @param strNote The Note enum name
     * @return The Note object that represents the name
     */
    public static Note fromString(String strNote) {
        for (Note note : Note.values) {
            if (note.name().equals(strNote)) {
                return note;
            }
        }

        return null;
    }
}
