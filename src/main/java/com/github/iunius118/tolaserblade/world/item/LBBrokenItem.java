package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;

public class LBBrokenItem extends LBPartItem {
    public LBBrokenItem(Properties properties) {
        super(properties, Upgrade.Type.REPAIR);
    }
}
