package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.common.CommonEventHandler;
import com.github.iunius118.tolaserblade.common.RegistryEventHandler;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.data.*;
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
import net.neoforged.neoforge.data.event.GatherDataEvent;
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
        modEventBus.addListener(this::gatherData);
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

    // Generate Data
    private void gatherData(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        var blockTagsProvider = new TLBBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        // Server
        final boolean includesServer = event.includeServer();
        dataGenerator.addProvider(includesServer, new TLBRecipeProvider(packOutput, lookupProvider));
        dataGenerator.addProvider(includesServer, blockTagsProvider);
        dataGenerator.addProvider(includesServer, new TLBItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        dataGenerator.addProvider(includesServer, new TLBEntityTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        dataGenerator.addProvider(includesServer, new TLBAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
        TLBOldRecipeProvider6.addProviders(event);

        // Client
        final boolean includesClient = event.includeClient();
        dataGenerator.addProvider(includesClient, new TLBItemModelProvider(packOutput, existingFileHelper));
        TLBLanguageProvider.addProviders(includesClient, dataGenerator, packOutput);
        dataGenerator.addProvider(includesClient, new TLBSoundDefinitionsProvider(packOutput, existingFileHelper));
    }
}
