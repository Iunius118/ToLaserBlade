package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.item.upgrade.LaserBladeUpgrade;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

public class LBEmitterItem extends Item implements LaserBladeItemBase {
    public static Item.Properties properties = (new Item.Properties()).setNoRepair().group(ModMainItemGroup.ITEM_GROUP);

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
            if (tintIndex == 1) {
                Pair<Integer, Boolean> bladeColor;
                bladeColor = ModItems.LB_EMITTER.getBladeInnerColor(stack);
                int color = ModItems.LB_EMITTER.checkGamingColor(bladeColor.getLeft());
                return (bladeColor.getRight() ? ~color : color) | 0xFF000000;
            }

            return 0xFFFFFFFF;
        }
    }
}
