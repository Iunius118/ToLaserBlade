package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.data.ModDataGenerator;
import com.github.iunius118.tolaserblade.data.pack.RepulsiveForceProvider;
import com.github.iunius118.tolaserblade.registry.ModCreativeModeTabs;
import com.github.iunius118.tolaserblade.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ToLaserBlade.MOD_ID)
public class ToLaserBlade {
    public static final String MOD_ID = "tolaserblade";
    public static final String MOD_NAME = "ToLaserBlade";

    public static final Logger LOGGER = LogUtils.getLogger();

    public ToLaserBlade(IEventBus modEventBus, ModContainer modContainer) {
        // Register lifecycle event listeners

        // Register config handlers

        // Register game objects
        ModItems.ITEMS.register(modEventBus);
        ModCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        // Register event handlers
        modEventBus.addListener(ModDataGenerator::gatherData);
        modEventBus.addListener(RepulsiveForceProvider::addPackFinders);
    }

    public static final Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
