package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeModelType0;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeModelType1;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeModelType217;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeModelType305;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LaserBladeInternalModelManager {
    private static LaserBladeInternalModelManager instance;
    private final Map<Integer, Class<? extends SimpleModel>> models;
    private final Class<? extends SimpleModel> defaultModel = LaserBladeModelType0.class;

    public static LaserBladeInternalModelManager renewInstance() {
        instance = new LaserBladeInternalModelManager();
        return instance;
    }

    public static LaserBladeInternalModelManager getInstance() {
        return instance != null ? instance : renewInstance();
    }

    private LaserBladeInternalModelManager() {
        models = new HashMap<>();
        addInternalModels();
    }

    private void addInternalModels() {
        models.put(0, defaultModel);
        models.put(1, LaserBladeModelType1.class);
        models.put(217, LaserBladeModelType217.class);
        models.put(305, LaserBladeModelType305.class);
    }

    public void addInternalModel(int index, Class<? extends SimpleModel> model) {
        if (model == null) {
            ToLaserBlade.LOGGER.warn("Attempted to add null to internal Laser Blade model #{}.", index);
            return;
        }

        if (models.containsKey(index)) {
            ToLaserBlade.LOGGER.info("Internal Laser Blade model #{} already exists. It will be overwritten by {}.", index, model.getCanonicalName());
        }

        models.put(index, model);
    }

    public SimpleModel getModel() {
        int modelType = ToLaserBladeConfig.CLIENT.internalModelType.get();

        if (modelType < 0) {
            final Calendar calendar = Calendar.getInstance();
            modelType = (calendar.get(Calendar.MONTH) + 1) * 100 + calendar.get(Calendar.DATE);
        }

        try {
            return models.getOrDefault(modelType, defaultModel).newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return new LaserBladeModelType0();
    }
}
