package com.github.iunius118.tolaserblade.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class LBBatteryItem extends Item implements ModMainItemGroup {
    public static Item.Properties properties = (new Item.Properties()).setNoRepair().group(ItemGroup.MISC);

    public LBBatteryItem() {
        super(properties);
    }
}
