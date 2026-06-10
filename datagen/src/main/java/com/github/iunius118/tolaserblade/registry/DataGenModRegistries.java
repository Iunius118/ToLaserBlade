package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DataGenModRegistries {

    public static void registerGameObjects(IEventBus modEventBus) {
        registerBlocks(modEventBus);
        registerItems(modEventBus);
    }

    private static void registerBlocks(IEventBus modEventBus) {
        var blocks = DeferredRegister.createBlocks(Constants.MOD_ID);


        blocks.register(modEventBus);
    }

    private static void registerItems(IEventBus modEventBus) {
        var items = DeferredRegister.createItems(Constants.MOD_ID);

        items.register(Constants.Items.LASER_BLADE.getPath(), () -> ModItems.LASER_BLADE);
        items.register(Constants.Items.LASER_BLADE_FP.getPath(), () -> ModItems.LASER_BLADE_FP);

        items.register(Constants.Items.LB_BATTERY.getPath(), () -> ModItems.LB_BATTERY);
        items.register(Constants.Items.LB_MEDIUM.getPath(), () -> ModItems.LB_MEDIUM);
        items.register(Constants.Items.LB_EMITTER.getPath(), () -> ModItems.LB_EMITTER);
        items.register(Constants.Items.LB_CASING.getPath(), () -> ModItems.LB_CASING);
        items.register(Constants.Items.LB_CASING_FP.getPath(), () -> ModItems.LB_CASING_FP);

        items.register(modEventBus);
    }
}
