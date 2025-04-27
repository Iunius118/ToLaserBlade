package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public class LBPartItem extends Item implements LaserBladeItemBase {
    public final Upgrade.Type upgradeType;

    public LBPartItem(Properties properties, Upgrade.Type type) {
        super(properties);
        upgradeType = type;
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return type == upgradeType;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipFlag flag, List<Component> tooltip) {
        ArrayList<Component> myTooltip = new ArrayList<>();
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, tooltipContext, myTooltip, flag, upgradeType);
        tooltip.addAll(1, myTooltip);
    }
}
