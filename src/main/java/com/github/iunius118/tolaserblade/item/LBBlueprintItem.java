package com.github.iunius118.tolaserblade.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LBBlueprintItem extends Item {
    public static Properties properties = (new Properties()).setNoRepair().group(ModMainItemGroup.ITEM_GROUP);

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
