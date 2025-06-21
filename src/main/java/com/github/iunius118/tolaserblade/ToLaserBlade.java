package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.client.ClientModEventHandler;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.common.CommonEventHandler;
import com.github.iunius118.tolaserblade.common.RegistryEventHandler;
import com.github.iunius118.tolaserblade.config.TLBClientConfig;
import com.github.iunius118.tolaserblade.config.TLBServerConfig;
import com.github.iunius118.tolaserblade.data.TLBDataGenerator;
import com.github.iunius118.tolaserblade.data.TLBEnchantmentProvider;
import com.github.iunius118.tolaserblade.data.TLBOldRecipeProvider6;
import com.github.iunius118.tolaserblade.data.TLBSampleSoundPackProvider;
import com.github.iunius118.tolaserblade.world.item.ItemEventHandler;
import com.github.iunius118.tolaserblade.world.item.ModCreativeModeTabs;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;

import java.lang.invoke.MethodHandles;

@Mod(ToLaserBlade.MOD_ID)
public class ToLaserBlade {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";

    public static final Logger LOGGER = LogUtils.getLogger();

    public ToLaserBlade(FMLJavaModLoadingContext context) {
        final var modBusGroup = context.getModBusGroup();
        // Register lifecycle event listeners

        // Register config handlers
        context.registerConfig(ModConfig.Type.SERVER, TLBServerConfig.SPEC);
        context.registerConfig(ModConfig.Type.CLIENT, TLBClientConfig.SPEC);

        // Register event handlers
        RegistryEventHandler.registerGameObjects(modBusGroup);
        GatherDataEvent.getBus(modBusGroup).addListener(TLBDataGenerator::gatherData);
        BuildCreativeModeTabContentsEvent.getBus(modBusGroup).addListener(ModCreativeModeTabs::onCreativeModeTabBuildContents);
        AddPackFindersEvent.getBus(modBusGroup).addListener(TLBEnchantmentProvider::addPackFinders);
        AddPackFindersEvent.getBus(modBusGroup).addListener(TLBOldRecipeProvider6::addPackFinders);
        AddPackFindersEvent.getBus(modBusGroup).addListener(TLBSampleSoundPackProvider::addPackFinders);
        BusGroup.DEFAULT.register(MethodHandles.lookup(), CommonEventHandler.class);
        BusGroup.DEFAULT.register(MethodHandles.lookup(), ItemEventHandler.class);

        // Register client-side mod event handler
        if (FMLLoader.getDist().isClient()) {
            ClientModEventHandler.initClient();
            modBusGroup.register(MethodHandles.lookup(), ClientModEventHandler.class);
            ToLaserBladeAPI.registerModelRegistrationListener(event -> event.register(LaserBladeModelManager.loadModels()));
        }
    }

    public static ResourceLocation makeId(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
