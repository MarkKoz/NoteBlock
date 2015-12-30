package com.mark.nbgui.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pitch {
    private static final int MAX_PITCH = 25;
    private static Pattern pattern = Pattern.compile("^([A-Za-z#]{1,2}|[A-Za-z]sharp)(\\d)$");

    private int num;

    public Pitch(int num) {
        this.num = (num % 25);
    }

    public Pitch(Note note, Octave octave) {
        Range range = octave.getRange();

        for (int i = range.getLower(); i <= range.getUpper(); i++) {
            int noteNum = i % 12;

            if (note.ordinal() == noteNum) {
                this.num = i;
            }
        }
    }

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

    public Note getNote() {
        return Note.fromPitch(this);
    }

    public Octave getOctave() {
        return Octave.fromPitch(this);
    }

    public Pitch incrementNote(int factor) {
        return new Pitch(this.getNote().add(factor), this.getOctave());
    }

    public Pitch incrementOctave(int factor) {
        return new Pitch(this.getNote(), this.getOctave().add(factor));
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = (num % 25);
    }

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

    @Override
    public int hashCode() {
        return this.num;
    }

    public static int getMax() {
        return Pitch.MAX_PITCH;
    }
}
