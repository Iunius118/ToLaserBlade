package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ToLaserBlade.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LASER_BLADE_TAB =
            CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tolaserblade.main"))
                    // Check whether the mod items exist
                    .icon(() -> ModItems.LASER_BLADE.isBound() ?
                            ModItems.LASER_BLADE.get().getDefaultInstance() : ItemStack.EMPTY)
                    .displayItems((parameters, output) -> {
                        // Check whether the mod items exist
                        if (!ModItems.LASER_BLADE.isBound()) return;

                        // Add items to the creative mode tab here
                        output.accept(ModItems.LASER_BLADE.get());
                        output.accept(ModItems.LASER_BLADE_FP.get());
                        output.accept(ModItems.LB_BATTERY.get());
                        output.accept(ModItems.LB_MEDIUM.get());
                        output.accept(ModItems.LB_EMITTER.get());
                        output.accept(ModItems.LB_CASING.get());
                    })
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
