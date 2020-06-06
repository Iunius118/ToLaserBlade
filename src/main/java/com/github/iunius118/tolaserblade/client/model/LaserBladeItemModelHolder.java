package com.github.iunius118.tolaserblade.client.model;

public class LaserBladeItemModelHolder {
    private static SimpleItemModel model = null;

    public static SimpleItemModel getModel() {
        return model;
    }

    public static void setModel(SimpleItemModel model) {
        LaserBladeItemModelHolder.model = model;
    }
}
