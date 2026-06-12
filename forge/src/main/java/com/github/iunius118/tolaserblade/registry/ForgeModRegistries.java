package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ForgeModRegistries {

    public static void registerGameObjects(BusGroup modBusGroup) {
        registerBlocks(modBusGroup);
        registerItems(modBusGroup);
        registerMenuTypes(modBusGroup);
        registerCreativeModeTabs(modBusGroup);
    }

    private static void registerBlocks(BusGroup modBusGroup) {
        var blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

        blocks.register(modBusGroup);
    }

    private static RegistryObject<Item> LASER_BLADE;

    private static void registerItems(BusGroup modBusGroup) {
        var items = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

        items.register(modBusGroup);
    }

    private static void registerMenuTypes(BusGroup modBusGroup) {
        var menuTypes = DeferredRegister.create(Registries.MENU, Constants.MOD_ID);

        menuTypes.register(modBusGroup);
    }

    private static void registerCreativeModeTabs(BusGroup modBusGroup) {
        var creativeModeTabs = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

        creativeModeTabs.register(Constants.CreativeModeTabs.MAIN.getPath(),
                ForgeModRegistries::getMainCreativeModeTab);

        creativeModeTabs.register(modBusGroup);
    }

    private static CreativeModeTab getMainCreativeModeTab() {
        return CreativeModeTab.builder()
                .title(Component.translatable(Constants.CreativeModeTabs.TITLE_MOD_MAIN))
                // Check whether the mod items exist
                .icon(() -> LASER_BLADE.isPresent() ? new ItemStack(LASER_BLADE.get()) : ItemStack.EMPTY)
                .displayItems((params, output) -> {
                    // Check whether the mod items exist
                    if (!LASER_BLADE.isPresent()) return;

                    for (Item i : ModItems.ITEMS) {
                        output.accept(i);
                    }
                })
                .build();
    }
}
