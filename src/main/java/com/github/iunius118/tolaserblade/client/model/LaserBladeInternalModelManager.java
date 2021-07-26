package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.laserblade.*;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LaserBladeInternalModelManager {
    private static LaserBladeInternalModelManager instance;
    private static final LaserBladeModel defaultModel = new LaserBladeModelType0();
    private final Map<Integer, Supplier<? extends LaserBladeModel>> models;
    private final Map<Integer, LaserBladeModel> modelCache;
    private final boolean canUseInternalModel;
    private final boolean canRenderMultipleModels;

    public static LaserBladeInternalModelManager renewInstance() {
        instance = new LaserBladeInternalModelManager();
        return instance;
    }

    public static LaserBladeInternalModelManager getInstance() {
        return instance != null ? instance : renewInstance();
    }

    private LaserBladeInternalModelManager() {
        models = new HashMap<>();
        modelCache = new HashMap<>();
        canUseInternalModel = ToLaserBladeConfig.CLIENT.useInternalModel.get();
        canRenderMultipleModels = canUseInternalModel & ToLaserBladeConfig.CLIENT.renderMultipleModels.get();
        addInternalModels();
    }

    private void addInternalModels() {
        models.put(0, () -> defaultModel);
        models.put(1, LaserBladeModelType1::new);
        models.put(101, LaserBladeModelType101::new);
        models.put(217, LaserBladeModelType217::new);
        models.put(222, LaserBladeModelType222::new);
        models.put(305, LaserBladeModelType305::new);
        models.put(316, LaserBladeModelType316::new);
        models.put(407, LaserBladeModelType407::new);
        models.put(504, LaserBladeModelType504::new);
        models.put(519, LaserBladeModelType519::new);
        models.put(526, LaserBladeModelType526::new);
        models.put(606, LaserBladeModelType606::new);
        models.put(726, LaserBladeModelType726::new);
        models.put(801, LaserBladeModelType801::new);
        models.put(808, LaserBladeModelType808::new);
        models.put(825, LaserBladeModelType825::new);
        models.put(903, LaserBladeModelType903::new);
        models.put(913, LaserBladeModelType913::new);
        models.put(924, LaserBladeModelType924::new);
        models.put(1009, LaserBladeModelType1009::new);
        models.put(1108, LaserBladeModelType1108::new);
        models.put(1216, LaserBladeModelType1216::new);
    }

    public void addInternalModel(int index, Supplier<? extends LaserBladeModel> model) {
        if (index < 0) {
            ToLaserBlade.LOGGER.warn("[ToLaserBlade] Attempted to add a model to invalid number {}.", index);
            return;
        }

        if (model == null) {
            ToLaserBlade.LOGGER.warn("[ToLaserBlade] Attempted to add null as internal Laser Blade model #{}.", index);
            return;
        }

        if (models.containsKey(index)) {
            ToLaserBlade.LOGGER.info("[ToLaserBlade] Internal Laser Blade model #{} already exists. It will be overwritten.", index);
        }

        models.put(index, model);
    }

    public LaserBladeModel getModel() {
        int modelType = ToLaserBladeConfig.CLIENT.internalModelType.get();
        return getModel(modelType);
    }

    public LaserBladeModel getModel(int modelType) {
        if (modelType < 0) {
            modelType = ToLaserBlade.getTodayDateNumber();
        }

        LaserBladeModel model = modelCache.get(modelType);

        if(model != null) {
            // Return cached model
            return model;
        }

        Supplier<? extends LaserBladeModel> supplier = models.get(modelType);

        if (supplier == null) {
            // Return default model for non-existent model type
            return defaultModel;
        }

        model = supplier.get();
        modelCache.put(modelType, model);
        return model;
    }

    public boolean canUseInternalModel() {
        return canUseInternalModel;
    }

    public boolean canRenderMultipleModels() {
        return canRenderMultipleModels;
    }
}
