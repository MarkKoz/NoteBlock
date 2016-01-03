package com.mark.nbgui.data;

public enum Octave {
    LOW(3, new Range(0, 5)),
    MID(4, new Range(6, 17)),
    HIGH(5, new Range(18, 24));

    private static Octave[] values = Octave.values();
    private final int num;
    private final Range range;

    Octave(int num, Range range) {
        this.num = num;
        this.range = range;
    }

    /**
     * Returns the Octave from a Pitch object
     *
     * @param pitch The Pitch object to get the Octave of
     * @return The octave decried from the Pitch.
     */
    public static Octave fromPitch(Pitch pitch) {
        int numPitch = pitch.getNum();

        for (Octave octave : Octave.values) {
            Range range = octave.getRange();

            if (range.inRange(numPitch)) {
                return octave;
            }
        }

        return null;
    }

    /**
     * Returns the Octave object from its octave number.
     *
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

    /**
     * Returns the number value of this Octave
     *
     * @return The number value of the Octave.
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Returns the pitch range of this Octave.
     *
     * @return The pitch range of this Octave.
     */
    public Range getRange() {
        return this.range;
    }

    /**
     * Gets the current octave's name (number)
     *
     * @return The current Octave's name
     */
    public String getName() {
        return String.format("%d", this.num);
    }

    /**
     * Increments this octave by a given factor
     *
     * @param factor The factor to increment this octave by
     * @return The new octave created as a result.
     */
    public Octave add(int factor) {
        try {
            return Octave.values[this.ordinal() + factor];
        } catch (Throwable ignored) {
        }
        return this;
    }

    /**
     * Creates a String representation of this Octave
     *
     * @return A String representation of this Octave
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
