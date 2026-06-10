package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.registry.FabricModRegistries;
import net.fabricmc.api.ModInitializer;

public class ToLaserBlade implements ModInitializer {

    @Override
    public void onInitialize() {
        // Use Fabric to bootstrap the Common mod.
        //Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        // Register mod game objects
        FabricModRegistries.registerGameObjects();
    }
}
