package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class LBCasingItem extends Item implements LaserBladeItemBase {
    public static Item.Properties properties = new Item.Properties();
    public final Upgrade.Type upgradeType = Upgrade.Type.CASING;

    public LBCasingItem(boolean isFireproof) {
        super(LaserBladeItemBase.setFireproof(properties, isFireproof));
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return type == upgradeType;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, tooltipContext, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, tooltipContext, tooltip, flag, upgradeType);
    }
}
