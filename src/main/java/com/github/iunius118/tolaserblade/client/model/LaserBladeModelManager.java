package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.api.client.event.LaserBladeModelRegistrationEvent;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeJsonModelLoader;
import com.github.iunius118.tolaserblade.client.model.laserblade.v1.LaserBladeModelV1;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModLoader;
import org.slf4j.Logger;

import java.util.*;

public class LaserBladeModelManager {
    private static final Logger LOGGER = ToLaserBlade.LOGGER;
    private static final String MODEL_DIR = "models/item/laser_blade";
    private static final LaserBladeModelManager INSTANCE = new LaserBladeModelManager();

    private Map<Integer, LaserBladeModel> models = Collections.emptyMap();
    private LaserBladeModel defaultModel;
    private boolean canUseOriginalModelType;
    private boolean canUseMultipleModels;

    private LaserBladeModelManager() {}

    public static LaserBladeModelManager getInstance() {
        return INSTANCE;
    }

    public static List<LaserBladeModel> loadModels() {
        return parseJsonModels();
    }

    public void reload() {
        canUseOriginalModelType = ToLaserBladeConfig.CLIENT.useOriginalModelType.get();
        canUseMultipleModels = ToLaserBladeConfig.CLIENT.renderMultipleModels.get();

        if (!canUseOriginalModelType) {
            // Don't use original 3D models
            models = Collections.emptyMap();
            defaultModel = null;
            return;
        }

        // Clear model cache
        models = new HashMap<>();

        // Load models
        ModLoader.get().postEvent(new LaserBladeModelRegistrationEvent(models -> models.forEach(this::addModel)));

        // Set default model
        int defaultModelNumber = ToLaserBladeConfig.CLIENT.defaultModel.get();

        if (models.containsKey(defaultModelNumber)) {
            defaultModel = models.get(defaultModelNumber);
        } else {
            defaultModel = models.get(0);
        }

        // Reset render types
        LaserBladeModelV1.resetRenderTypes();
    }

    public void logLoadedModelCount() {
        int count = models.size();

        if (count == 1) {
            LOGGER.info("1 model has been loaded as a laser blade model");
        } else {
            LOGGER.info("{} models have been loaded as laser blade models", count);
        }
    }

    private static List<LaserBladeModel> parseJsonModels() {
        Map<ResourceLocation, Resource> resourceMap = findJsonFiles();
        List<LaserBladeModel> jsonModels = new LinkedList<>();

        resourceMap.forEach((location, resource) -> {
            LaserBladeModel model = LaserBladeJsonModelLoader.parse(location.toString(), resource);
            if (model != null) {
                jsonModels.add(model);
            }
        });

        return jsonModels;
    }

    private static Map<ResourceLocation, Resource> findJsonFiles() {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

        // Search resource packs for .json files
        return resourceManager.listResources(
                MODEL_DIR, resourceLocation -> {
                    String namespace = resourceLocation.getNamespace();
                    String path = resourceLocation.getPath();
                    return namespace.equals(ToLaserBlade.MOD_ID) && path.endsWith(".json");
                }
        );
    }

    public boolean canUseOriginalModelType() {
        return canUseOriginalModelType;
    }

    public boolean canUseMultipleModels() {
        return canUseMultipleModels;
    }

    public void addModel(LaserBladeModel model) {
        if (model == null) {
            ToLaserBlade.LOGGER.warn("[ToLaserBlade] Attempted to add null as Laser Blade model.");
            return;
        }

        int index = model.getID();

        if (index < 0) {
            ToLaserBlade.LOGGER.warn("[ToLaserBlade] Attempted to add a model to invalid index {}.", index);
            return;
        }

        if (models.containsKey(index)) {
            ToLaserBlade.LOGGER.info("[ToLaserBlade] Laser Blade model #{} already exists. It will be overwritten.", index);
        }

        models.put(index, model);
    }

    public LaserBladeModel getModel() {
        return defaultModel;
    }

    public LaserBladeModel getModel(ItemStack itemStack) {
        if (canUseMultipleModels) {
            int type = LaserBlade.of(itemStack).getType();
            LaserBladeModel model = getModel(type);

            if (model != null) {
                return model;
            } else {
                return defaultModel;
            }
        } else {
            // When don't use multiple models
            return defaultModel;
        }
    }

    public LaserBladeModel getModel(int modelID) {
        if (canUseMultipleModels) {
            LaserBladeModel model = models.get(modelID);

            if (model != null) {
                return model;
            } else {
                return defaultModel;
            }
        } else {
            // When don't use multiple models
            return defaultModel;
        }
    }

    public Map<Integer, LaserBladeModel> getModels() {
        return models;
    }
}
