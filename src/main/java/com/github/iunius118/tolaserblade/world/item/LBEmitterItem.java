package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.world.item.Item;

public class LBEmitterItem extends Item implements LaserBladeItemBase {
    public static Item.Properties properties = new Item.Properties();
    public final Upgrade.Type upgradeType = Upgrade.Type.EMITTER;

    public LBEmitterItem() {
        super(properties);
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return type == upgradeType;
    }
}
