package com.mark.nbgui.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoteOctavePair {
    private static Pattern pattern = Pattern.compile("^([A-Za-z]\\s*(?:#|sharp)?)\\s*(\\d)$");

    /**
     * The Note object in this pair
     */
    public Note note;
    /**
     * The Octave object in this pair
     */
    public Octave octave;

    /**
     * Creates a new NoteOctavePair
     *
     * @param note   The Note object
     * @param octave The Octave object
     */
    public NoteOctavePair(Note note, Octave octave) {
        this.note = note;
        this.octave = octave;
    }

    /**
     * Parses a note-octave input from a user
     *
     * @param input The user input
     * @return The NoteOctavePair object parsed from the input.
     */
    public static NoteOctavePair parseNoteOctave(String input) {
        input = input.trim();

        Matcher m = NoteOctavePair.pattern.matcher(input);
        if (m.matches() && m.groupCount() == 2) {
            String strNote = m.group(1);
            int iOctave = Integer.parseInt(m.group(2));

            Note note = Note.parse(strNote);
            Octave octave = Octave.fromNum(iOctave);

            return new NoteOctavePair(note, octave);
        }

        return null;
    }
}
