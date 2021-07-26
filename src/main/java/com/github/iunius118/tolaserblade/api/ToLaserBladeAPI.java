package com.github.iunius118.tolaserblade.api;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;

import java.lang.reflect.Method;
import java.util.function.Supplier;

public class ToLaserBladeAPI {
    private static Object modelManagerInstance;
    private static Method addInternalModel;

    public static void addInternalModel(int index, Supplier<? extends LaserBladeModel> model) {
        if (modelManagerInstance == null || addInternalModel == null) {
            init();
        }

        try {
            addInternalModel.invoke(modelManagerInstance, index, model);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    private static void init() {
        try {
            Class<? extends Object> laserBladeInternalModelManager = Class.forName("com.github.iunius118.tolaserblade.client.model.LaserBladeInternalModelManager");
            Method getInstance = laserBladeInternalModelManager.getMethod("getInstance");
            modelManagerInstance = getInstance.invoke(null);
            addInternalModel = laserBladeInternalModelManager.getMethod("addInternalModel", int.class, Supplier.class);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
