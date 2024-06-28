package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.List;

public class ModCreativeModeTabs {
    public static final CreativeModeTab TAB_LASER_BLADE = CreativeModeTab.builder()
            .title(LaserBladeTextKey.KEY_ITEM_GROUP_GENERAL.translate())
            .icon(() -> LaserBladeItemStack.ICON.getCopy(null))
            .displayItems((params, output) -> {
                HolderLookup.Provider lookupProvider = params.holders();
                // DX Laser B1ade
                output.accept(ModItems.DX_LASER_BLADE);
                // Laser Blades
                output.accept(ModItems.LASER_BLADE);
                output.accept(LaserBladeItemStack.LIGHT_ELEMENT_1.getCopy(lookupProvider));
                output.accept(LaserBladeItemStack.LIGHT_ELEMENT_2.getCopy(lookupProvider));
                output.accept(LaserBladeItemStack.GIFT.getCopy(lookupProvider));
                output.accept(LaserBladeItemStack.UPGRADED.getCopy(lookupProvider));
                output.accept(LaserBladeItemStack.DAMAGED.getCopy(lookupProvider));
                output.accept(LaserBladeItemStack.FULL_MOD.getCopy(lookupProvider));
                // Fireproof Laser Blades
                output.accept(ModItems.LASER_BLADE_FP);
                output.accept(LaserBladeItemStack.UPGRADED_FP.getCopy(lookupProvider));
                output.accept(LaserBladeItemStack.DAMAGED_FP.getCopy(lookupProvider));
                output.accept(LaserBladeItemStack.FULL_MOD_FP.getCopy(lookupProvider));
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
                output.accept(LaserBladeItemStack.DISASSEMBLED_FULL_MOD.getCopy(lookupProvider));
                output.accept(ModItems.LB_DISASSEMBLED_FP);
                output.accept(LaserBladeItemStack.DISASSEMBLED_FULL_MOD_FP.getCopy(lookupProvider));
                // Parts
                output.accept(ModItems.LB_BLUEPRINT);
                output.accept(ModItems.LB_BATTERY);
                output.accept(ModItems.LB_MEDIUM);
                output.accept(ModItems.LB_EMITTER);
                output.accept(ModItems.LB_CASING);
                output.accept(ModItems.LB_CASING_FP);
            })
            .build();

    public static void onCreativeModeTabBuildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() != ModCreativeModeTabs.TAB_LASER_BLADE)
            return;

        // Register model-changed laser blades to mod creative mode tab
        List<Integer> modelTypes = LaserBladeModelManager.getInstance().getModels().keySet().stream().sorted().toList();
        for (int modelType : modelTypes) {
            if (modelType != 0) {
                ItemStack laserBlade = LaserBladeItemStack.getModelChangedStack(modelType, false, event.getParameters().holders());
                event.accept(laserBlade);
            }
        }
    }
}
