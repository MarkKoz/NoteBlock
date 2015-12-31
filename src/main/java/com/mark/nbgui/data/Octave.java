package com.mark.nbgui.data;

public enum Octave {
    LOW(3, new Range(0, 5)),
    MID(4, new Range(6, 17)),
    HIGH(5, new Range(18, 24));


    private final int num;
    private final Range range;

    Octave(int num, Range range) {
        this.num = num;
        this.range = range;
    }

    private static Octave[] values = Octave.values();

    /**
     * Returns the number value of this Octave
     * @return The number value of the Octave.
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Returns the pitch range of this Octave.
     * @return The pitch range of this Octave.
     */
    public Range getRange() {
        return this.range;
    }

    public String getName() {
        return String.format("%d", this.num);
    }

    public Octave add(int factor) {
        try {
            return Octave.values[this.ordinal() + factor];
        } catch (Throwable ignored) {
        }
        return this;
    }

    /**
     * Returns the Octave from a Pitch object
     * @param pitch The Pitch object to get the Octave of
     * @return The octave decried from the Pitch.
     */
    public static Octave fromPitch(Pitch pitch) {
        int numPitch = pitch.getNum();

        for ()

        return null;
    }

    /**
     * Returns the Octave object from its octave number.
     * @param num The octave number
     * @return The Octave object
     */
    public static Octave fromNum(int num) {
        for (Octave octave : Octave.values) {
            if (octave.getNum() == num) {
                return octave;
            }
        }

        return null;
    }
}
