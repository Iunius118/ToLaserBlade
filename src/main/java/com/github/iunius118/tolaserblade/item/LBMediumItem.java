package com.github.iunius118.tolaserblade.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.List;

public class LBMediumItem extends Item implements LaserBladeItemBase, ModMainItemGroup {
    public static Item.Properties properties = (new Item.Properties()).setNoRepair().group(ItemGroup.MISC);

    public LBMediumItem() {
        super(properties);
    }

    @Override
    public boolean canUpgrade(LaserBladeUpgrade.Type type) {
        return type == LaserBladeUpgrade.Type.MEDIUM;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        ModItems.LB_MEDIUM.addLaserBladeInformation(stack, worldIn, tooltip, flagIn);
    }

    @OnlyIn(Dist.CLIENT)
    public static class ColorHandler implements IItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            Pair<Integer, Boolean> bladeColor;

            if (tintIndex == 1) {
                bladeColor = ModItems.LB_MEDIUM.getBladeInnerColor(stack);
                return (bladeColor.getRight() ? ~bladeColor.getLeft() : bladeColor.getLeft()) | 0xFF000000;
            }

            return 0xFFFFFFFF;
        }
    }
}
