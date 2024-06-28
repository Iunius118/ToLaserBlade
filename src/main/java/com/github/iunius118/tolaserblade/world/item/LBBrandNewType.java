package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;

public enum LBBrandNewType {
    NONE(LaserBladeItemStack.ORIGINAL),
    LIGHT_ELEMENT_1(LaserBladeItemStack.LIGHT_ELEMENT_1),
    LIGHT_ELEMENT_2(LaserBladeItemStack.LIGHT_ELEMENT_2),
    FP(LaserBladeItemStack.FP);

    private final LaserBladeItemStack original;

    LBBrandNewType(LaserBladeItemStack stack) {
        original = stack;
    }

    public ItemStack getCopy(HolderLookup.Provider lookupProvider) {
        return original.getCopy(lookupProvider);
    }
}
