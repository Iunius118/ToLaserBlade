package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.world.item.Item;

public interface LaserBladeItemBase {
    static Item.Properties setFireproof(Item.Properties properties, boolean isFireproof) {
        return isFireproof ? properties.fireResistant() : properties;
    }

    boolean canUpgrade(Upgrade.Type type);
}
