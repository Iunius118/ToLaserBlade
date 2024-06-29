package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.common.CommonEventHandler;
import com.github.iunius118.tolaserblade.common.RegistryEventHandler;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.data.TLBDataGenerator;
import com.github.iunius118.tolaserblade.data.TLBOldRecipeProvider6;
import com.github.iunius118.tolaserblade.world.item.ItemEventHandler;
import com.github.iunius118.tolaserblade.world.item.ModCreativeModeTabs;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(ToLaserBlade.MOD_ID)
public class ToLaserBlade {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";

    public static final Logger LOGGER = LogUtils.getLogger();

    public ToLaserBlade(IEventBus modEventBus, ModContainer modContainer) {
        // Register lifecycle event listeners

        // Register config handlers
        modContainer.registerConfig(ModConfig.Type.SERVER, ToLaserBladeConfig.serverSpec);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ToLaserBladeConfig.clientSpec);

        // Register event handlers
        RegistryEventHandler.registerGameObjects(modEventBus);
        modEventBus.addListener(TLBDataGenerator::gatherData);
        modEventBus.addListener(ModCreativeModeTabs::onCreativeModeTabBuildContents);
        modEventBus.addListener(TLBOldRecipeProvider6::addPackFinders);
        NeoForge.EVENT_BUS.register(CommonEventHandler.class);
        NeoForge.EVENT_BUS.register(ItemEventHandler.class);

        // Register client-side mod event handler
        if (FMLLoader.getDist().isClient()) {
            modEventBus.register(ClientModEventHandler.class);
            ToLaserBladeAPI.registerModelRegistrationListener(event -> event.register(LaserBladeModelManager.loadModels()));
        }
    }

    public static ResourceLocation makeId(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
