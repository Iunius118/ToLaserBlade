package com.github.iunius118.tolaserblade.common.util;

public class Color4Float {
    public final float b;
    public final float g;
    public final float r;
    public final float a;

    public Color4Float(int color) {
        b = (float)(color & 0xFF) / 0xFF;
        g = (float)((color >>> 8) & 0xFF) / 0xFF;
        r = (float)((color >>> 16) & 0xFF) / 0xFF;
        a = (float)((color >>> 24) & 0xFF) / 0xFF;
    }
}
