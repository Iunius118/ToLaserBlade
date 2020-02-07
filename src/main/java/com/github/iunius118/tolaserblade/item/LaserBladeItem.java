package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

public class LaserBladeItem extends SwordItem {
    public static Item.Properties properties = (new Item.Properties()).setNoRepair().group(ItemGroup.TOOLS);

    public LaserBladeItem() {
        super(new ItemTier(), 3, -1.2F, properties);
    }

    @OnlyIn(Dist.CLIENT)
    public static class ColorHandler implements IItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            if (ToLaserBladeConfig.CLIENT.isEnabledLaserBlade3DModel.get()) {
                return 0xFFFFFFFF;
            }

            switch (tintIndex) {
                case 1:
                    return getColorFromNBT(stack, LaserBlade.KEY_COLOR_HALO, LaserBlade.KEY_IS_SUB_COLOR_HALO, LaserBlade.DEFAULT_COLOR_HALO);

                case 2:
                    return getColorFromNBT(stack, LaserBlade.KEY_COLOR_CORE, LaserBlade.KEY_IS_SUB_COLOR_CORE, LaserBlade.DEFAULT_COLOR_CORE);

                default:
                    return 0xFFFFFFFF;
            }
        }

        private int getColorFromNBT(ItemStack stack, String keyColor, String keyIsSubColor, int defaultColor) {
            CompoundNBT nbt = stack.getTag();

            if (nbt != null && nbt.contains(keyColor, Constants.NBT.TAG_INT)) {
                int color = nbt.getInt(keyColor);

                if (nbt.getBoolean(keyIsSubColor)) {
                    color = ~color | 0xFF000000;
                }

                return color;
            }

            return defaultColor;
        }
    }

    public static class ItemTier implements IItemTier {
        @Override
        public int getHarvestLevel() {
            return 3;
        }

        @Override
        public int getMaxUses() {
            return LaserBlade.MAX_USES;
        }

        @Override
        public float getEfficiency() {
            return ToLaserBladeConfig.COMMON.laserBladeEfficiencyInServer.get();
        }

        @Override
        public float getAttackDamage() {
            return 3.0F;
        }

        @Override
        public int getEnchantability() {
            return 15;
        }

        @Override
        public Ingredient getRepairMaterial() {
            Tag<Item> tag = ItemTags.getCollection().get(new ResourceLocation("forge", "ingots/iron"));

            if (tag != null) {
                return Ingredient.fromTag(tag);
            } else {
                return Ingredient.fromItems(Items.IRON_INGOT);
            }
        }
    }
}
