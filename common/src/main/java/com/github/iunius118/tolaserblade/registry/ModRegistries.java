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
    private static Holder<Item> LASER_BLADE;

    public static void registerGameObjects() {
        // Blocks
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.BLOCK, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.Blocks.BL_BLUEPRINT.getPath(), () -> ModBlocks.BL_BLUEPRINT);
                });
        // Block types
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.BLOCK_TYPE, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.Blocks.BL_BLUEPRINT.getPath(), () -> LBBlueprintBlock.CODEC);
                });
        // Items
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.ITEM, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.Blocks.BL_BLUEPRINT.getPath(), () -> ModItems.BL_BLUEPRINT);

                    LASER_BLADE = r.register(Constants.Items.LASER_BLADE.getPath(), () -> ModItems.LASER_BLADE);
                    r.register(Constants.Items.LASER_BLADE_FP.getPath(), () -> ModItems.LASER_BLADE_FP);

                    r.register(Constants.Items.LB_BATTERY.getPath(), () -> ModItems.LB_BATTERY);
                    r.register(Constants.Items.LB_MEDIUM.getPath(), () -> ModItems.LB_MEDIUM);
                    r.register(Constants.Items.LB_EMITTER.getPath(), () -> ModItems.LB_EMITTER);
                    r.register(Constants.Items.LB_CASING.getPath(), () -> ModItems.LB_CASING);
                    r.register(Constants.Items.LB_CASING_FP.getPath(), () -> ModItems.LB_CASING_FP);
                });
        // Data component types
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.DataComponents.MODEL.getPath(), () -> ModDataComponents.MODEL);
                    r.register(Constants.DataComponents.BLEND_MODES.getPath(), () -> ModDataComponents.BLEND_MODES);
                });
        // Menus
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.MENU, Constants.MOD_ID)
                .registerObjects(r -> {
                });
        // Creative mode tabs
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.CreativeModeTabs.MAIN.getPath(), ModRegistries::createMainCreativeModeTab);
                });
    }

    private static CreativeModeTab createMainCreativeModeTab() {
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

    private ModRegistries() {}
}
