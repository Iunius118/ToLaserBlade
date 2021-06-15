package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
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
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        LaserBladeItemUtil.addLaserBladeInformation(stack, worldIn, tooltip, flagIn, upgradeType);
    }
}
