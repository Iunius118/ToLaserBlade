package com.github.iunius118.tolaserblade.api.core.laserblade;

/**
 * State of a laser blade at a given moment.
 * @param damage Additional attack damage from the attack damage upgrade.
 * @param speed Additional attack speed from the attack speed upgrade.
 * @param modelType Non-negative integer to identify model type.
 * @param inner State of the inner blade.
 * @param outer State of the outer blade.
 * @param grip State of the grip.
 */
public record LaserBladeState(float damage,  float speed, int modelType, Part inner, Part outer, Part grip) {
    /**
     * Part of the laser blade.
     * @param exists Whether the laser blade has a tag of this part.
     * @param color 32-bit ARGB value for the color of this part.
     * @param isSubtractiveColor Whether this part should be rendered in subtractive color mixing.
     */
    public record Part(boolean exists, int color, boolean isSubtractiveColor) { }
}
