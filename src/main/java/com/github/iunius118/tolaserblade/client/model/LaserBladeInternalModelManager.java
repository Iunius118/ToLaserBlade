package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.client.model.laserblade.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LaserBladeInternalModelManager {
    private static LaserBladeInternalModelManager instance;
    private final Map<Integer, Supplier<? extends SimpleModel>> models;
    private static final Supplier<? extends SimpleModel> defaultModel = LaserBladeModelType0::new;

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
        models.put(1, LaserBladeModelType1::new);
        models.put(101, LaserBladeModelType101::new);
        models.put(217, LaserBladeModelType217::new);
        models.put(305, LaserBladeModelType305::new);
        models.put(407, LaserBladeModelType407::new);
        models.put(504, LaserBladeModelType504::new);
        models.put(519, LaserBladeModelType519::new);
        models.put(526, LaserBladeModelType526::new);
        models.put(606, LaserBladeModelType606::new);
        models.put(726, LaserBladeModelType726::new);
        models.put(825, LaserBladeModelType825::new);
        models.put(903, LaserBladeModelType903::new);
        models.put(913, LaserBladeModelType913::new);
        models.put(1009, LaserBladeModelType1009::new);
        models.put(1108, LaserBladeModelType1108::new);
        models.put(1216, LaserBladeModelType1216::new);
    }

    public void addInternalModel(int index, Supplier<? extends SimpleModel> model) {
        if (model == null) {
            ToLaserBlade.LOGGER.warn("Attempted to add null as internal Laser Blade model #{}.", index);
            return;
        }

        if (models.containsKey(index)) {
            ToLaserBlade.LOGGER.info("Internal Laser Blade model #{} already exists. It will be overwritten.", index);
        }

        models.put(index, model);
    }

    public SimpleModel getModel() {
        int modelType = ToLaserBladeConfig.CLIENT.internalModelType.get();

        if (modelType < 0) {
            final Calendar calendar = Calendar.getInstance();
            modelType = (calendar.get(Calendar.MONTH) + 1) * 100 + calendar.get(Calendar.DATE);
        }

        return models.getOrDefault(modelType, defaultModel).get();
    }
}