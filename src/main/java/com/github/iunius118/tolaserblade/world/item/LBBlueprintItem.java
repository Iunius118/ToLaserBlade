package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LBBlueprintItem extends Item {
    public static Properties properties = (new Properties()).setNoRepair().tab(ModMainItemGroup.ITEM_GROUP);

    public LBBlueprintItem() {
        super(properties);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack.copy();
    }
}
