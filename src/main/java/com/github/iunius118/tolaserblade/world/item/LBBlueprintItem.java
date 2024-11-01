package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.mixin.ItemAccessor;
import net.minecraft.world.item.Item;

public class LBBlueprintItem extends Item {
    public LBBlueprintItem(Properties properties) {
        super(properties);
        ((ItemAccessor) this).setCraftingRemainingItem(this);
    }
}
