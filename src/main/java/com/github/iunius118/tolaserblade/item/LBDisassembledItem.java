package com.github.iunius118.tolaserblade.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class LBDisassembledItem extends Item implements LaserBladeItemBase, ModMainItemGroup {
    public static Properties properties = (new Properties()).setNoRepair().group(ItemGroup.MISC);

    public LBDisassembledItem() {
        super(properties);
    }
}
