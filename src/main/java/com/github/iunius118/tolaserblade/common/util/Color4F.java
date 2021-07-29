package com.github.iunius118.tolaserblade.common.util;

public record Color4F(float a, float r, float g, float b) {
    public static Color4F of(int color) {
        float fb = (float) (color & 0xFF) / 0xFF;
        float fg = (float) ((color >>> 8) & 0xFF) / 0xFF;
        float fr = (float) ((color >>> 16) & 0xFF) / 0xFF;
        float fa = (float) ((color >>> 24) & 0xFF) / 0xFF;
        return new Color4F(fa, fr, fg, fb);
    }
}
