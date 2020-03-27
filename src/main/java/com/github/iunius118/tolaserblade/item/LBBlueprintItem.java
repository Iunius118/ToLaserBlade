package com.github.iunius118.tolaserblade.item;

import net.minecraft.item.Item;

public class LBBlueprintItem extends Item {
    public static Properties properties = (new Properties()).setNoRepair().group(ModMainItemGroup.ITEM_GROUP);

    public LBBlueprintItem() {
        super(properties);
    }
}
