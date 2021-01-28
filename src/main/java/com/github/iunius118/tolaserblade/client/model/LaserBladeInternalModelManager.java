package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.api.client.model.ILaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.laserblade.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LaserBladeInternalModelManager {
    private static LaserBladeInternalModelManager instance;
    private final Map<Integer, Supplier<? extends ILaserBladeModel>> models;
    private final Map<Integer, ILaserBladeModel> modelCache;
    private static final ILaserBladeModel defaultModel = new LaserBladeModelType0();

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
        addInternalModels();
    }

    private void addInternalModels() {
        models.put(0, () -> defaultModel);
        models.put(1, LaserBladeModelType1::new);
        models.put(101, LaserBladeModelType101::new);
        models.put(217, LaserBladeModelType217::new);
        models.put(305, LaserBladeModelType305::new);
        models.put(316, LaserBladeModelType316::new);
        models.put(407, LaserBladeModelType407::new);
        models.put(504, LaserBladeModelType504::new);
        models.put(519, LaserBladeModelType519::new);
        models.put(526, LaserBladeModelType526::new);
        models.put(606, LaserBladeModelType606::new);
        models.put(726, LaserBladeModelType726::new);
        models.put(801, LaserBladeModelType801::new);
        models.put(825, LaserBladeModelType825::new);
        models.put(903, LaserBladeModelType903::new);
        models.put(913, LaserBladeModelType913::new);
        models.put(924, LaserBladeModelType924::new);
        models.put(1009, LaserBladeModelType1009::new);
        models.put(1108, LaserBladeModelType1108::new);
        models.put(1216, LaserBladeModelType1216::new);
    }

    public void addInternalModel(int index, Supplier<? extends ILaserBladeModel> model) {
        if (model == null) {
            ToLaserBlade.LOGGER.warn("[ToLaserBlade] Attempted to add null as internal Laser Blade model #{}.", index);
            return;
        }

        if (models.containsKey(index)) {
            ToLaserBlade.LOGGER.info("[ToLaserBlade] Internal Laser Blade model #{} already exists. It will be overwritten.", index);
        }

        models.put(index, model);
    }

    public ILaserBladeModel getModel() {
        int modelType = ToLaserBladeConfig.CLIENT.internalModelType.get();
        return getModel(modelType);
    }

    public ILaserBladeModel getModel(int modelType) {
        if (modelType < 0) {
            final Calendar calendar = Calendar.getInstance();
            modelType = (calendar.get(Calendar.MONTH) + 1) * 100 + calendar.get(Calendar.DATE);
        }

        ILaserBladeModel model = modelCache.get(modelType);

        if(model != null) {
            // Return cached model
            return model;
        }

        Supplier<? extends ILaserBladeModel> supplier = models.get(modelType);

        if (supplier == null) {
            // Return default model for non-existent model type
            return defaultModel;
        }

        model = supplier.get();
        modelCache.put(modelType, model);
        return model;
    }
}
