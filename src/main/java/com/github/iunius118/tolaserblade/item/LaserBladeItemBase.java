package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import net.minecraft.item.Item;

public interface LaserBladeItemBase {
    LaserBladeItemCore core = LaserBladeItemCore.INSTANCE;

    float MOD_CRITICAL_BONUS_VS_WITHER = 0.5F;
    int MAX_USES = 32000;

    /* Laser Blade properties */

    static Item.Properties setFireproof(Item.Properties properties, boolean isFireproof) {
        return isFireproof ? properties.fireResistant() : properties;
    }

    default boolean canUpgrade(Upgrade.Type type) {
        return false;
    }
}
