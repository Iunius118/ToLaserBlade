package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.block.LBBlueprintBlock;
import com.github.iunius118.tolaserblade.block.ModBlocks;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.component.ModDataComponents;
import com.github.iunius118.tolaserblade.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModRegistries {

    private ModRegistries() {}

    public static void registerGameObjects() {
        registerBlocks();
        registerBlockTypes();
        registerItems();
        registerDataComponentTypes();
        registerMenuTypes();
        registerCreativeModeTabs();
    }

    private static void registerBlocks() {
        var blocks = Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.BLOCK, Constants.MOD_ID);

        blocks.register(Constants.Blocks.BL_BLUEPRINT.getPath(), () -> ModBlocks.BL_BLUEPRINT);

        blocks.register();
    }

    private static void registerBlockTypes() {
        var blockTypes = Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.BLOCK_TYPE, Constants.MOD_ID);

        blockTypes.register(Constants.Blocks.BL_BLUEPRINT.getPath(), () -> LBBlueprintBlock.CODEC);

        blockTypes.register();
    }

    private static Holder<Item> LASER_BLADE;

    private static void registerItems() {
        var items = Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.ITEM, Constants.MOD_ID);

        items.register(Constants.Blocks.BL_BLUEPRINT.getPath(), () -> ModItems.BL_BLUEPRINT);

        LASER_BLADE = items.register(Constants.Items.LASER_BLADE.getPath(), () -> ModItems.LASER_BLADE);
        items.register(Constants.Items.LASER_BLADE_FP.getPath(), () -> ModItems.LASER_BLADE_FP);

        items.register(Constants.Items.LB_BATTERY.getPath(), () -> ModItems.LB_BATTERY);
        items.register(Constants.Items.LB_MEDIUM.getPath(), () -> ModItems.LB_MEDIUM);
        items.register(Constants.Items.LB_EMITTER.getPath(), () -> ModItems.LB_EMITTER);
        items.register(Constants.Items.LB_CASING.getPath(), () -> ModItems.LB_CASING);
        items.register(Constants.Items.LB_CASING_FP.getPath(), () -> ModItems.LB_CASING_FP);

        items.register();
    }

    private static void registerDataComponentTypes() {
        var dataComponentTypes =
                Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

        dataComponentTypes.register(Constants.DataComponents.MODEL.getPath(), () -> ModDataComponents.MODEL);
        dataComponentTypes.register(Constants.DataComponents.BLEND_MODES.getPath(),
                () -> ModDataComponents.BLEND_MODES);

        dataComponentTypes.register();
    }

    private static void registerMenuTypes() {
        var menuTypes = Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.MENU, Constants.MOD_ID);

        menuTypes.register();
    }

    private static void registerCreativeModeTabs() {
        var creativeModeTabs =
                Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.MOD_ID);

        creativeModeTabs.register(Constants.CreativeModeTabs.MAIN.getPath(), ModRegistries::getMainCreativeModeTab);

        creativeModeTabs.register();
    }

    private static CreativeModeTab getMainCreativeModeTab() {
        return Services.PLATFORM.createCreativeModeTabBuilder()
                .title(Component.translatable(Constants.CreativeModeTabs.TITLE_MOD_MAIN))
                // Check whether the mod items exist
                .icon(() -> LASER_BLADE.isBound() ? new ItemStack(LASER_BLADE.value()) : ItemStack.EMPTY)
                .displayItems((params, output) -> {
                    // Check whether the mod items exist
                    if (!LASER_BLADE.isBound()) return;

                    for (Item i : ModItems.ITEMS) {
                        output.accept(i);
                    }
                })
                .build();
    }
}
