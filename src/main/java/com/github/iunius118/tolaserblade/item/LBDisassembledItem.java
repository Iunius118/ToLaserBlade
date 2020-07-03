package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.item.upgrade.LaserBladeUpgrade;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class LBDisassembledItem extends Item implements LaserBladeItemBase {
    public static Properties properties = (new Properties()).setNoRepair().group(ModMainItemGroup.ITEM_GROUP);
    public final LaserBladeUpgrade.Type upgradeType = LaserBladeUpgrade.Type.REPAIR;

    public LBDisassembledItem(boolean isFireproof) {
        super(LaserBladeItemBase.setFireproof(properties, isFireproof));
    }

    @Override
    public boolean canUpgrade(LaserBladeUpgrade.Type type) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        addLaserBladeInformation(stack, worldIn, tooltip, flagIn, upgradeType);
    }
}
