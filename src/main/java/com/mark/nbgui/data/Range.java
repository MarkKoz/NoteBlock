package com.mark.nbgui.data;

/**
 * A class that represents a number range.
 * @author Nima, Mark
 * @version 1.0.0
 */
public class Range {
    private int lower;
    private int upper;

    /**
     * Creates a new Range object
     * @param lower The lower bound of the range.
     * @param upper The upper bound of the range.
     */
    public Range(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    /**
     * Gets the upper range of this
     * @return The upper bound of this range.
     */
    public int getUpper() {
        return this.upper;
    }

    /**
     * Gets the lower range of this
     * @return The lower bound of this range.
     */
    public int getLower() {
        return this.lower;
    }

    /**
     * Checks if val is in range of the lowerbound and upperbound of this Range object.
     * @param val The value to check against.
     * @return True if val is in range of the lowerbound and upperbound, false otherwise.
     */
    public boolean inRange(int val) {
        return (val >= this.lower && val <= this.upper);
    }
}