package com.github.iunius118.tolaserblade.client.model;

import net.minecraft.util.ResourceLocation;

public class LaserBladeItemModelHolder {
    private static SimpleItemModel model = null;
    private static ResourceLocation texture = new ResourceLocation("forge", "textures/white.png");;

    public static SimpleItemModel getModel() {
        return model;
    }

    public static void setModel(SimpleItemModel modelIn) {
        model = modelIn;
        texture = model.getTexture();
    }

    public static ResourceLocation getTexture() {
        return texture;
    }
}
