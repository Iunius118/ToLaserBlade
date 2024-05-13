package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LBBlueprintItem extends Item {
    public static Properties properties = new Properties();

    public LBBlueprintItem() {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return itemStack.copy();
    }
}
