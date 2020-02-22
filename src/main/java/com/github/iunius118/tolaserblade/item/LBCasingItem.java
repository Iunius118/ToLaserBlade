package com.github.iunius118.tolaserblade.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class LBCasingItem extends Item implements ToLaserBladeItemGroup {
    public static Item.Properties properties = (new Item.Properties()).setNoRepair().group(ItemGroup.TOOLS);

    public LBCasingItem() {
        super(properties);
    }
}
