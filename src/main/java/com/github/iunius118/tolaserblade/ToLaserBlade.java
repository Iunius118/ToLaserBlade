package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.common.CommonEventHandler;
import com.github.iunius118.tolaserblade.common.RegistryEventHandler;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.data.TLBDataGenerator;
import com.github.iunius118.tolaserblade.data.TLBEnchantmentProvider;
import com.github.iunius118.tolaserblade.data.TLBOldRecipeProvider6;
import com.github.iunius118.tolaserblade.world.item.ItemEventHandler;
import com.github.iunius118.tolaserblade.world.item.ModCreativeModeTabs;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;

@Mod(ToLaserBlade.MOD_ID)
public class ToLaserBlade {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";

    public static final Logger LOGGER = LogUtils.getLogger();

    public ToLaserBlade(FMLJavaModLoadingContext context) {
        final IEventBus modEventBus = context.getModEventBus();
        // Register lifecycle event listeners

        // Register config handlers
        context.registerConfig(ModConfig.Type.SERVER, ToLaserBladeConfig.serverSpec);
        context.registerConfig(ModConfig.Type.CLIENT, ToLaserBladeConfig.clientSpec);

        // Register event handlers
        RegistryEventHandler.registerGameObjects(modEventBus);
        modEventBus.addListener(TLBDataGenerator::gatherData);
        modEventBus.addListener(ModCreativeModeTabs::onCreativeModeTabBuildContents);
        modEventBus.addListener(TLBEnchantmentProvider::addPackFinders);
        modEventBus.addListener(TLBOldRecipeProvider6::addPackFinders);
        MinecraftForge.EVENT_BUS.register(CommonEventHandler.class);
        MinecraftForge.EVENT_BUS.register(ItemEventHandler.class);

        // Register client-side mod event handler
        if (FMLLoader.getDist().isClient()) {
            ClientModEventHandler.initClient();
            modEventBus.register(ClientModEventHandler.class);
            ToLaserBladeAPI.registerModelRegistrationListener(event -> event.register(LaserBladeModelManager.loadModels()));
        }
    }

    public static ResourceLocation makeId(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
