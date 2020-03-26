package com.github.iunius118.tolaserblade.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class LBEmitterItem extends Item implements LaserBladeItemBase, ModMainItemGroup {
    public static Item.Properties properties = (new Item.Properties()).setNoRepair().group(ItemGroup.MISC);

    public LBEmitterItem() {
        super(properties);
    }

    @Override
    public boolean canUpgrade(LaserBladeUpgrade.Type type) {
        return type == LaserBladeUpgrade.Type.EMITTER;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ColorHandler implements IItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            Pair<Integer, Boolean> bladeColor;

            if (tintIndex == 1) {
                bladeColor = ModItems.LB_EMITTER.getBladeOuterColor(stack);
                return (bladeColor.getRight() ? ~bladeColor.getLeft() : bladeColor.getLeft()) | 0xFF000000;
            }

            return 0xFFFFFFFF;
        }
    }
}
