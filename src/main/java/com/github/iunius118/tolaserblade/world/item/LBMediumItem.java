package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class LBMediumItem extends Item implements LaserBladeItemBase {
    public static Item.Properties properties = (new Item.Properties()).setNoRepair().tab(ModMainItemGroup.ITEM_GROUP);
    public final Upgrade.Type upgradeType = Upgrade.Type.MEDIUM;

    public LBMediumItem() {
        super(properties);
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return type == upgradeType;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, level, tooltip, flag, upgradeType);
    }
}
