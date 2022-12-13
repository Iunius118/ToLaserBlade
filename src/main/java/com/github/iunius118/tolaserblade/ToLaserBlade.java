package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.common.CommonEventHandler;
import com.github.iunius118.tolaserblade.common.RegistryEventHandler;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.data.*;
import com.github.iunius118.tolaserblade.world.item.ItemEventHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;

import java.util.Calendar;

@Mod(ToLaserBlade.MOD_ID)
public class ToLaserBlade {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";

    public static final Logger LOGGER = LogUtils.getLogger();

    public ToLaserBlade() {
        // Register lifecycle event listeners
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::initClient);
        modEventBus.register(ToLaserBladeConfig.class);

        // Register config handlers
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ToLaserBladeConfig.serverSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ToLaserBladeConfig.clientSpec);

        // Register event handlers
        RegistryEventHandler.registerGameObjects(modEventBus);
        modEventBus.addListener(this::gatherData);
        MinecraftForge.EVENT_BUS.register(CommonEventHandler.class);
        MinecraftForge.EVENT_BUS.register(ItemEventHandler.class);

        // Register client-side mod event handler
        if (FMLLoader.getDist().isClient()) {
            modEventBus.register(ClientModEventHandler.class);
        }
    }

    private static boolean canUseFixedVertexBuffer;

    private void initClient(final FMLClientSetupEvent event) {
        // Get useFixedVertexBuffer only once at startup
        canUseFixedVertexBuffer = ToLaserBladeConfig.CLIENT.useFixedVertexBuffer.get();
    }

    public static boolean canUseFixedVertexBuffer() {
        return canUseFixedVertexBuffer;
    }

    public static int getTodayDateNumber() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1) * 100 + calendar.get(Calendar.DATE);
    }

    // Generate Data
    private void gatherData(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        ForgeBlockTagsProvider blockTags = new ForgeBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        // Server
        boolean includesServer = event.includeServer();
        dataGenerator.addProvider(includesServer, new TLBRecipeProvider(packOutput));
        dataGenerator.addProvider(includesServer, new TLBItemTagsProvider(packOutput, lookupProvider, blockTags, existingFileHelper));
        dataGenerator.addProvider(includesServer, new TLBAdvancementProvider(packOutput, lookupProvider, existingFileHelper));

        // Client
        boolean includesClient = event.includeClient();
        dataGenerator.addProvider(includesClient, new TLBItemModelProvider(packOutput, existingFileHelper));
        TLBLanguageProvider.addProviders(includesClient, dataGenerator, packOutput);
        dataGenerator.addProvider(includesClient, new TLBSoundDefinitionsProvider(packOutput, existingFileHelper));
    }
}
