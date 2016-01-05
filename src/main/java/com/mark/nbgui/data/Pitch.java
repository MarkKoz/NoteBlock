package com.mark.nbgui.data;

/**
 * A class that represents a musical pitch.
 *
 * @author Nima, Mark
 * @version 1.0.0
 */
public class Pitch {
    public static final int MAX_PITCH = 25;

    private int num;

    /**
     * Creates a Pitch object from a pitch value
     *
     * @param num Pitch value as an int (byte in Minecraft engine)
     */
    public Pitch(int num) {
        this.num = Math.floorMod(num, Pitch.MAX_PITCH);
    }

    /**
     * Creates a Pitch object from a pitch value, returning null if the pitch isn't supported
     * @param num Pitch value as an int
     * @return The pitch object.
     */
    public static Pitch fromNum(int num) {
        if (num >= 0 && num < Pitch.MAX_PITCH) {
            return new Pitch(num);
        }

        return null;
    }

    /**
     * Creates a Pitch object from a NoteOctavePair object.
     *
     * @param pair The NoteOctavePair object
     */
    public static Pitch fromNoteOctave(NoteOctavePair pair) {
        if (pair.note != null && pair.octave != null) {
            return Pitch.fromNoteOctave(pair.note, pair.octave);
        }
        return null;
    }

    /**
     * Creates a Pitch object from a Note object and an Octave object.
     *
     * @param note   The note object
     * @param octave The octave object
     */
    public static Pitch fromNoteOctave(Note note, Octave octave) {
        int num = -1;

        Range range = octave.getRange();

        for (int i = range.getLower(); i <= range.getUpper(); i++) {
            if (note.ordinal() == Math.floorMod(i, Note.MAX_NOTE)) {
                num = i;
            }
        }

        if (num != -1) {
            return new Pitch(num);
        }
        return null;
    }

    /**
     * Gets the note of this pitch.
     *
     * @return A Note object that represents the note of this pitch.
     */
    public Note getNote() {
        return Note.fromPitch(this);
    }

    /**
     * Gets the octave of this pitch.
     *
     * @return A Octave object that represents the octave of this pitch.
     */
    public Octave getOctave() {
        return Octave.fromPitch(this);
    }

    /**
     * Adds parameter factor to this pitch's number
     *
     * @param factor The factor to increment the pitch by
     * @return The new incremented pitch
     */
    public Pitch increment(int factor) {
        return Pitch.fromNum(this.num + factor);
    }

    /**
     * Adds parameter factor to the note of this pitch
     *
     * @param factor The factor to increment the note by
     * @return The new pitch with the incremented note
     */
    public Pitch incrementNote(int factor) {
        return Pitch.fromNoteOctave(this.getNote().add(factor), this.getOctave());
    }

    /**
     * Adds parameter factor to the octave of this pitch
     *
     * @param factor The factor to increment the octave by
     * @return The new pitch with the incremented octave
     */
    public Pitch incrementOctave(int factor) {
        return Pitch.fromNoteOctave(this.getNote(), this.getOctave().add(factor));
    }

    /**
     * Returns the integer value of this pitch
     *
     * @return The integer number of this pitch.
     */
    public int getNum() {
        return this.num;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.getNote(), this.getOctave());
    }

    /**
     * Checks if another object equals this pitch object
     *
     * @param o Object to check against
     * @return True if the two objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Pitch pitch = (Pitch) o;

        return this.num == pitch.num;
    }

    /**
     * Calculates the hash code of this object
     *
     * @return The hash code of this object.
     */
    @Override
    public int hashCode() {
        return this.num;
    }
}
