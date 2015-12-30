package com.mark.nbgui.data;

import net.minecraftforge.event.world.NoteBlockEvent;

public enum Octave {
    LOW,
    MID,
    HIGH;

    private static Octave[] values = Octave.values();

    public static Octave fromBlockOctave(NoteBlockEvent.Octave octave) {
        return Octave.values[octave.ordinal() % Octave.values.length];
    }

    public static Octave fromPitch(Pitch pitch) {
        int numPitch = pitch.getNum();
        numPitch = numPitch % Pitch.getMax();

        Range low = Octave.LOW.getRange();
        Range mid = Octave.MID.getRange();
        Range high = Octave.HIGH.getRange();

        if (low.inRange(numPitch)) {
            return Octave.LOW;
        }
        if (mid.inRange(numPitch)) {
            return Octave.MID;
        }
        if (high.inRange(numPitch)) {
            return Octave.HIGH;
        }

        return null;
    }

    public static Octave fromNum(int num) {
        for (Octave octave : Octave.values) {
            if (octave.getNum() == num) {
                return octave;
            }
        }

        return null;
    }

    public int getNum() {
        switch (this) {
            case LOW:
                return 3;
            case MID:
                return 4;
            case HIGH:
                return 5;
        }

        return -1;
    }

    public Range getRange() {
        Range range = null;

        switch (this) {
            case LOW:
                range = new Range(0, 5);
                break;
            case MID:
                range = new Range(6, 17);
                break;
            case HIGH:
                range = new Range(18, 24);
                break;
        }

        return range;
    }

    public String getName() {
        switch (this) {
            case LOW:
                return "Low";
            case MID:
                return "Mid";
            case HIGH:
                return "High";
        }

        return "Unknown";
    }

    public Octave add(int factor) {
        return Octave.values[this.ordinal() + factor];
    }
}

class Range {
    private int lower;
    private int upper;

    public Range(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public int getUpper() {
        return this.upper;
    }

    public int getLower() {
        return this.lower;
    }

    public boolean inRange(int val) {
        return (val >= this.lower && val <= this.upper);
    }
}
