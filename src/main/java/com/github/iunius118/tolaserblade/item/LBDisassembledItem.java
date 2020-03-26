package com.github.iunius118.tolaserblade.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class LBDisassembledItem extends Item implements LaserBladeItemBase, ModMainItemGroup {
    public static Properties properties = (new Properties()).setNoRepair().group(ItemGroup.MISC);

    public LBDisassembledItem() {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        ModItems.LB_DISASSEMBLED.addLaserBladeInformation(stack, worldIn, tooltip, flagIn);
    }
}
