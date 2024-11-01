package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;

public interface LaserBladeItemBase {
    boolean canUpgrade(Upgrade.Type type);
}
