package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class LBSwordItemTier implements IItemTier {
    public static final int MAX_USES = 32000;
    public final static int NETHERITIC_HARVEST_LEVEL = 4;
    public final static int HARVEST_LEVEL = 3;
    public final static float NETHERITIC_DAMAGE = 4.0F;
    public final static float NORMAL_DAMAGE = 3.0F;
    public final static int ENCHANTABILITY = 15;

    private final boolean isNetheritic;

    public LBSwordItemTier(boolean isFireproof) {
        isNetheritic = isFireproof;
    }

    @Override
    public int getLevel() {
        return isNetheritic ? NETHERITIC_HARVEST_LEVEL : HARVEST_LEVEL;
    }

    @Override
    public int getUses() {
        return MAX_USES;
    }

    @Override
    public float getSpeed() {
        return ToLaserBladeConfig.SERVER.laserBladeEfficiency.get();
    }

    @Override
    public float getAttackDamageBonus() {
        return isNetheritic ? NETHERITIC_DAMAGE : NORMAL_DAMAGE;
    }

    @Override
    public int getEnchantmentValue() {
        return ENCHANTABILITY;
    }

    @Override
    public Ingredient getRepairIngredient() {
        ITag<Item> tag = ItemTags.getAllTags().getTag(new ResourceLocation("forge", "ingots/iron"));

        if (tag != null) {
            return Ingredient.of(tag);

        } else {
            return Ingredient.of(Items.IRON_INGOT);
        }
    }
}