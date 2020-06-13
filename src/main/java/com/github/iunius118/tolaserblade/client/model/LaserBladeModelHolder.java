package com.github.iunius118.tolaserblade.client.model;

import net.minecraft.util.ResourceLocation;

public class LaserBladeModelHolder {
    private static SimpleModel model = null;
    private static ResourceLocation texture = new ResourceLocation("forge", "textures/white.png");;

    public static SimpleModel getModel() {
        return model;
    }

    public static void setModel(SimpleModel modelIn) {
        model = modelIn;
        texture = model.getTexture();
    }

    public static ResourceLocation getTexture() {
        return texture;
    }
}
