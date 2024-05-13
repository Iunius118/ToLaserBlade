package com.github.iunius118.tolaserblade.api.core.laserblade;

/**
 * State of a laser blade at a given moment.
 */
public interface LaserBladeState {
    /** @return Additional attack damage from the attack damage upgrade. */
    float attack();
    /** @return Additional attack speed from the attack speed upgrade. */
    float speed();
    /** @return Non-negative integer to identify model type. */
    int modelType();
    /** @return State of the outer blade. */
    Part outer();
    /** @return State of the inner blade. */
    Part inner();
    /** @return State of the grip. */
    Part grip();

    /**
     * Part of the laser blade.
     */
    interface Part {
        /** @return Whether the laser blade has a tag of this part. */
        boolean exists();
        /** @return 32-bit ARGB value for the color of this part. */
        int color();
        /** @return Whether this part should be rendered in subtractive color mixing. */
        boolean isSubtractiveColor();
    }
}
