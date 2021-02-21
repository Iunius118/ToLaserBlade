package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.api.client.model.ILaserBladeModel;
import net.minecraft.util.ResourceLocation;

public class LaserBladeModelHolder {
    private static ILaserBladeModel model = null;
    private static ResourceLocation texture = new ResourceLocation("forge", "textures/white.png");

    public static ILaserBladeModel getModel() {
        return model;
    }

    public static void setModel(ILaserBladeModel modelIn) {
        model = modelIn;
        texture = model.getTexture();
    }

    public static ResourceLocation getTexture() {
        return texture;
    }
}
