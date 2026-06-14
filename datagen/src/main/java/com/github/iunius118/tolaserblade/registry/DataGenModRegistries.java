package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.block.LBBlueprintBlock;
import com.github.iunius118.tolaserblade.block.ModBlocks;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.component.ModDataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DataGenModRegistries {

    public static void registerGameObjects(IEventBus modEventBus) {
        registerBlocks(modEventBus);
        registerBlockTypes(modEventBus);
        registerItems(modEventBus);
        registerDataComponentTypes(modEventBus);
    }

    private static void registerBlocks(IEventBus modEventBus) {
        var blocks = DeferredRegister.createBlocks(Constants.MOD_ID);

        blocks.register(Constants.Blocks.BL_BLUEPRINT.getPath(), () -> ModBlocks.BL_BLUEPRINT);

        blocks.register(modEventBus);
    }

    private static void registerBlockTypes(IEventBus modEventBus) {
        var blockTypes = DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, Constants.MOD_ID);

        blockTypes.register(Constants.Blocks.BL_BLUEPRINT.getPath(), () -> LBBlueprintBlock.CODEC);

        blockTypes.register(modEventBus);
    }

    private static void registerItems(IEventBus modEventBus) {
        var items = DeferredRegister.createItems(Constants.MOD_ID);

        items.register(Constants.Blocks.BL_BLUEPRINT.getPath(), () -> ModItems.BL_BLUEPRINT);

        items.register(Constants.Items.LASER_BLADE.getPath(), () -> ModItems.LASER_BLADE);
        items.register(Constants.Items.LASER_BLADE_FP.getPath(), () -> ModItems.LASER_BLADE_FP);

        items.register(Constants.Items.LB_BATTERY.getPath(), () -> ModItems.LB_BATTERY);
        items.register(Constants.Items.LB_MEDIUM.getPath(), () -> ModItems.LB_MEDIUM);
        items.register(Constants.Items.LB_EMITTER.getPath(), () -> ModItems.LB_EMITTER);
        items.register(Constants.Items.LB_CASING.getPath(), () -> ModItems.LB_CASING);
        items.register(Constants.Items.LB_CASING_FP.getPath(), () -> ModItems.LB_CASING_FP);

        items.register(modEventBus);
    }

    private static void registerDataComponentTypes(IEventBus modEventBus) {
        var dataComponentTypes = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

        dataComponentTypes.register(Constants.DataComponents.MODEL.getPath(), () -> ModDataComponents.MODEL);
        dataComponentTypes.register(Constants.DataComponents.BLEND_MODES.getPath(),
                () -> ModDataComponents.BLEND_MODES);

        dataComponentTypes.register(modEventBus);
    }
}
