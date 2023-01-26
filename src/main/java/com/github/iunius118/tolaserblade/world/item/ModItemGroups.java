package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ModItemGroups {
    public static CreativeModeTab TAB_LASER_BLADE;

    @SubscribeEvent
    public static void onCreativeModeTabRegister(CreativeModeTabEvent.Register event) {
        TAB_LASER_BLADE = event.registerCreativeModeTab(new ResourceLocation(ToLaserBlade.MOD_ID, "main"),
                builder -> builder.icon(LaserBladeItemStack.ICON::getCopy)
                        .title(Component.translatable("itemGroup.tolaserblade"))
                        .displayItems((features, output, hasPermissions) -> {
                            // DX Laser B1ade
                            output.accept(ModItems.DX_LASER_BLADE);
                            // Laser Blades
                            output.accept(ModItems.LASER_BLADE);
                            output.accept(LaserBladeItemStack.LIGHT_ELEMENT_1.getCopy());
                            output.accept(LaserBladeItemStack.LIGHT_ELEMENT_2.getCopy());
                            output.accept(LaserBladeItemStack.GIFT.getCopy());
                            output.accept(LaserBladeItemStack.UPGRADED.getCopy());
                            output.accept(LaserBladeItemStack.DAMAGED.getCopy());
                            output.accept(LaserBladeItemStack.FULL_MOD.getCopy());
                            // Fireproof Laser Blades
                            output.accept(ModItems.LASER_BLADE_FP);
                            output.accept(LaserBladeItemStack.UPGRADED_FP.getCopy());
                            output.accept(LaserBladeItemStack.DAMAGED_FP.getCopy());
                            output.accept(LaserBladeItemStack.FULL_MOD_FP.getCopy());
                            // Brand-new
                            output.accept(ModItems.LB_BRAND_NEW_1);
                            output.accept(ModItems.LB_BRAND_NEW_2);
                            // Repaired
                            output.accept(ModItems.LB_BRAND_NEW);
                            output.accept(ModItems.LB_BRAND_NEW_FP);
                            // Broken
                            output.accept(ModItems.LB_BROKEN);
                            output.accept(ModItems.LB_BROKEN_FP);
                            // Disassembled
                            output.accept(ModItems.LB_DISASSEMBLED);
                            output.accept(LaserBladeItemStack.DISASSEMBLED_FULL_MOD.getCopy());
                            output.accept(ModItems.LB_DISASSEMBLED_FP);
                            output.accept(LaserBladeItemStack.DISASSEMBLED_FULL_MOD_FP.getCopy());
                            // Parts
                            output.accept(ModItems.LB_BLUEPRINT);
                            output.accept(ModItems.LB_BATTERY);
                            output.accept(ModItems.LB_MEDIUM);
                            output.accept(ModItems.LB_EMITTER);
                            output.accept(ModItems.LB_CASING);
                            output.accept(ModItems.LB_CASING_FP);
                        })
        );
    }

    @SubscribeEvent
    public static void onCreativeModeTabBuildContents(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() != ModItemGroups.TAB_LASER_BLADE)
            return;

        // Register model-changed laser blades to mod creative mode tab
        List<Integer> modelTypes = LaserBladeModelManager.getInstance().getModels().keySet().stream().sorted().toList();
        for (int modelType : modelTypes) {
            if (modelType != 0) {
                ItemStack laserBlade = LaserBladeItemStack.getModelChangedStack(modelType, false);
                event.accept(laserBlade);
            }
        }
    }
}
