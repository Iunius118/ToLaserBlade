package com.github.iunius118.tolaserblade;

import com.github.iunius118.tolaserblade.registry.ForgeModRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class ToLaserBlade {

    public ToLaserBlade(FMLJavaModLoadingContext context) {
        final var modBusGroup = context.getModBusGroup();

        // Use Forge to bootstrap the Common mod.
        //Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        // Register mod event listeners
        ForgeModRegistries.registerGameObjects(modBusGroup);

        // Init client-side
        if (FMLLoader.getDist().isClient()) {
            ToLaserBladeClient.initClient(context);
        }
    }
}
