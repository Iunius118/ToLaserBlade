package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.registry.FabricModRegistries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.chat.Component;

import java.util.Optional;

public class ToLaserBlade implements ModInitializer {

    @Override
    public void onInitialize() {
        // Use Fabric to bootstrap the Common mod.
        //Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        // Register mod game objects
        FabricModRegistries.registerGameObjects();
        addResourcePack();
    }

    public static void addResourcePack() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(Constants.MOD_ID);
        container.ifPresent(modContainer ->
                ResourceLoader.registerBuiltinPack(Constants.DataPacks.REPULSIVE_FORCE.id(),
                        modContainer, Component.translatable(Constants.DataPacks.REPULSIVE_FORCE.nameKey()),
                        PackActivationType.DEFAULT_ENABLED));
    }
}
