package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import net.minecraft.resources.ResourceLocation;

public class LaserBladeModelHolder {
    private static LaserBladeModel model = null;
    private static ResourceLocation texture = new ResourceLocation("forge", "textures/white.png");

    public static LaserBladeModel getModel() {
        return model;
    }

    public static void setModel(LaserBladeModel modelIn) {
        model = modelIn;
        texture = model.getTexture();
    }

    public static ResourceLocation getTexture() {
        return texture;
    }
}
