package com.github.iunius118.tolaserblade.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class LBCasingItem extends Item implements LaserBladeItemBase, ModMainItemGroup {
    public static Item.Properties properties = (new Item.Properties()).setNoRepair().group(ItemGroup.MISC);

    public LBCasingItem() {
        super(properties);
    }

    @Override
    public boolean canUpgrade(LaserBladeUpgrade.Type type) {
        return type == LaserBladeUpgrade.Type.CASING;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ColorHandler implements IItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            return ModItems.LB_CASING.getGripColor(stack) | 0xFF000000;
        }
    }
}
