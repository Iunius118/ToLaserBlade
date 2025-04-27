package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public interface LaserBladeItemBase {
    boolean canUpgrade(Upgrade.Type type);
    void appendTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipFlag flag, List<Component> tooltip);
}
