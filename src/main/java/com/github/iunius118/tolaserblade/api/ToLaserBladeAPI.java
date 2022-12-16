package com.github.iunius118.tolaserblade.api;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Supplier;

public class ToLaserBladeAPI {
    public static void addModels(List<LaserBladeModel> models) {
        try {
            Class<?> laserBladeModelManager = Class.forName("com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager");
            Method getInstance = laserBladeModelManager.getMethod("getInstance");
            Object modelManagerInstance = getInstance.invoke(null);
            Method addModel = laserBladeModelManager.getMethod("addModel", Supplier.class);

            for(LaserBladeModel model : models) {
                addModel.invoke(modelManagerInstance, model);
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
