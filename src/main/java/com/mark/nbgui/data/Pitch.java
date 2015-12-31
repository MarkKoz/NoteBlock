package com.mark.nbgui.data;

import com.mark.nbgui.exception.PitchNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that represents a musical pitch.
 * @author Nima, Mark
 * @version 1.0.0
 */
public class Pitch {
    public static final int MAX_PITCH = 25;
    private static Pattern pattern = Pattern.compile("^([A-Za-z#]{1,2}|[A-Za-z]sharp)\\s*(\\d)$");

    private int num;

    /**
     * Creates a Pitch object from a pitch value
     * @param num Pitch value as an int (byte in Minecraft engine)
     */
    public Pitch(int num) {
        this.num = (num % Pitch.MAX_PITCH);
    }

    /**
     * Creates a Pitch object from a Note object and an Octave object.
     * @param note The note object
     * @param octave The octave object
     */
    public Pitch(Note note, Octave octave) {
        Range range = octave.getRange();

        boolean found = false;
        for (int i = range.getLower(); i <= range.getUpper(); i++) {
            if (found || note.ordinal() == (i % Note.MAX_NOTE)) {
                this.num = i;
                found = true;
            }
        }

        if (!found) {
            throw new PitchNotFoundException(
                    String.format("Could not find the pitch at note %s and octave %s",
                            note.name(), octave.name()));
        }
    }

    /**
     * Gets the note of this pitch.
     * @return A Note object that represents the note of this pitch.
     */
    public Note getNote() {
        return Note.fromPitch(this);
    }

    /**
     * Gets the octave of this pitch.
     * @return A Octave object that represents the octave of this pitch.
     */
    public Octave getOctave() {
        return Octave.fromPitch(this);
    }

    /**
     * Adds parameter factor to the note of this pitch
     * @param factor The factor to increment the note by
     * @return The new pitch with the incremented note
     */
    public Pitch incrementNote(int factor) {
        return new Pitch(this.getNote().add(factor), this.getOctave());
    }

    /**
     * Adds parameter factor to the octave of this pitch
     * @param factor The factor to increment the octave by
     * @return The new pitch with the incremented octave
     */
    public Pitch incrementOctave(int factor) {
        return new Pitch(this.getNote(), this.getOctave().add(factor));
    }

    /**
     * Returns the integer value of this pitch
     * @return The integer number of this pitch.
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Checks if another object equals this pitch object
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
     * @return The hash code of this object.
     */
    @Override
    public int hashCode() {
        return this.num;
    }

    /**
     * Parses a pitch from a user input
     * @param pitch The user input
     * @return The Pitch object parsed from the input.
     */
    public static Pitch parsePitch(String pitch) {
        pitch = pitch.trim();

        Matcher m = Pitch.pattern.matcher(pitch);
        if (m.matches() && m.groupCount() == 2) {
            String strNote = m.group(1);
            int iOctave = Integer.parseInt(m.group(2));

            Note note = Note.fromString(strNote);
            Octave octave = Octave.fromNum(iOctave);

            return new Pitch(note, octave);
        }

        return null;
    }
}
