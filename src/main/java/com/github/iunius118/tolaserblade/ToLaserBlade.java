package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.client.renderer.entity.LaserTrapEntityRenderer;
import com.github.iunius118.tolaserblade.common.CommonEventHandler;
import com.github.iunius118.tolaserblade.common.RegistryEventHandler;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.world.entity.ModEntities;
import com.github.iunius118.tolaserblade.world.item.ItemEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fmlclient.registry.RenderingRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

@Mod(ToLaserBlade.MOD_ID)
@EventBusSubscriber
public class ToLaserBlade {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";

    public static final Logger LOGGER = LogManager.getLogger();

    public ToLaserBlade() {
        // Register lifecycle event listeners
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::initClient);
        modEventBus.register(ToLaserBladeConfig.class);

        // Register config handlers
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ToLaserBladeConfig.serverSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ToLaserBladeConfig.clientSpec);

        // Register event handlers
        modEventBus.register(RegistryEventHandler.class);
        MinecraftForge.EVENT_BUS.register(CommonEventHandler.class);
        MinecraftForge.EVENT_BUS.register(ItemEventHandler.class);

        // Register client-side mod event handler
        if (FMLLoader.getDist().isClient()) {
            modEventBus.register(ClientModEventHandler.class);
        }
    }

    private void initClient(final FMLClientSetupEvent event) {
        // Register laser trap entity renderer
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.LASER_TRAP, LaserTrapEntityRenderer::new);
    }

    public static int getTodayDateNumber() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1) * 100 + calendar.get(Calendar.DATE);
    }
}
