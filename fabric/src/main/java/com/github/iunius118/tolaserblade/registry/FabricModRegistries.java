package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FabricModRegistries {

    public static void registerGameObjects() {
        registerBlocks();
        registerItems();
        registerMenuTypes();
        registerCreativeModeTabs();
    }

    private static void registerBlocks() {
        var blocks = ModObjectRegistry.create(BuiltInRegistries.BLOCK, Constants.MOD_ID);
    }

    private static void registerItems() {
        var items = ModObjectRegistry.create(BuiltInRegistries.ITEM, Constants.MOD_ID);

        items.register(Constants.Items.LASER_BLADE.getPath(), ModItems.LASER_BLADE);
        items.register(Constants.Items.LASER_BLADE_FP.getPath(), ModItems.LASER_BLADE_FP);

        items.register(Constants.Items.LB_BATTERY.getPath(), ModItems.LB_BATTERY);
        items.register(Constants.Items.LB_MEDIUM.getPath(), ModItems.LB_MEDIUM);
        items.register(Constants.Items.LB_EMITTER.getPath(), ModItems.LB_EMITTER);
        items.register(Constants.Items.LB_CASING.getPath(), ModItems.LB_CASING);
        items.register(Constants.Items.LB_CASING_FP.getPath(), ModItems.LB_CASING_FP);
    }

    private static void registerMenuTypes() {
        var menuTypes = ModObjectRegistry.create(BuiltInRegistries.MENU, Constants.MOD_ID);
    }

    private static void registerCreativeModeTabs() {
        var creativeModeTabs = ModObjectRegistry.create(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.MOD_ID);

        creativeModeTabs.register(Constants.CreativeModeTabs.MAIN.getPath(), getMainCreativeModeTab());
    }

    private static CreativeModeTab getMainCreativeModeTab() {
        return FabricCreativeModeTab.builder()
                .icon(() -> new ItemStack(ModItems.LASER_BLADE))
                .title(Component.translatable(Constants.CreativeModeTabs.TITLE_MOD_MAIN))
                .displayItems((params, output) -> {
                    for (Item i : ModItems.ITEMS) {
                        if (i != null) output.accept(i);
                    }
                })
                .build();
    }

    private record ModObjectRegistry<V, T extends V>(Registry<V> registry, String modId) {

        public static <V, T extends V> ModObjectRegistry<V, T> create(Registry<V> registry, String modId) {
            return new ModObjectRegistry<>(registry, modId);
        }

        public void register(String id, T object) {
            Registry.register(registry, Identifier.fromNamespaceAndPath(modId, id), object);
        }
    }
}
