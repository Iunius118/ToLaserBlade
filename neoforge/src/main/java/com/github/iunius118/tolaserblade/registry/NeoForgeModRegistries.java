package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.component.ModDataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeModRegistries {

    public static void registerGameObjects(IEventBus modEventBus) {
        registerBlocks(modEventBus);
        registerItems(modEventBus);
        registerDataComponentTypes(modEventBus);
        registerMenuTypes(modEventBus);
        registerCreativeModeTabs(modEventBus);
    }

    private static void registerBlocks(IEventBus modEventBus) {
        var blocks = DeferredRegister.createBlocks(Constants.MOD_ID);

        blocks.register(modEventBus);
    }

    private static DeferredItem<Item> LASER_BLADE;

    private static void registerItems(IEventBus modEventBus) {
        var items = DeferredRegister.createItems(Constants.MOD_ID);

        LASER_BLADE = items.register(Constants.Items.LASER_BLADE.getPath(), () -> ModItems.LASER_BLADE);
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

    private static void registerMenuTypes(IEventBus modEventBus) {
        var menuTypes = DeferredRegister.create(Registries.MENU, Constants.MOD_ID);

        menuTypes.register(modEventBus);
    }

    private static void registerCreativeModeTabs(IEventBus modEventBus) {
        var creativeModeTabs = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

        creativeModeTabs.register(Constants.CreativeModeTabs.MAIN.getPath(),
                NeoForgeModRegistries::getMainCreativeModeTab);

        creativeModeTabs.register(modEventBus);
    }

    private static CreativeModeTab getMainCreativeModeTab() {
        return CreativeModeTab.builder()
                .title(Component.translatable(Constants.CreativeModeTabs.TITLE_MOD_MAIN))
                // Check whether the mod items exist
                .icon(() -> LASER_BLADE.isBound() ? new ItemStack(LASER_BLADE.get()) : ItemStack.EMPTY)
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
