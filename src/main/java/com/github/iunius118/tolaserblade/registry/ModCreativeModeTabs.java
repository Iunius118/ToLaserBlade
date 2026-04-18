package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ToLaserBlade.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> LASER_BLADE_TAB =
            CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tolaserblade.main"))
                    .icon(() -> ModItems.LASER_BLADE.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        // Add items to the creative mode tab here
                        output.accept(ModItems.LASER_BLADE.get());
                        output.accept(ModItems.LB_BATTERY.get());
                        output.accept(ModItems.LB_MEDIUM.get());
                        output.accept(ModItems.LB_EMITTER.get());
                        output.accept(ModItems.LB_CASING.get());
                    })
                    .build());
}
