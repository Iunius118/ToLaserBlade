package com.github.iunius118.tolaserblade.item;

import net.minecraft.block.Blocks;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public class DXLaserBladeItemTier implements IItemTier  {
    @Override
    public int getLevel() {
        return 3;
    }

    @Override
    public int getUses() {
        return 255;
    }

    @Override
    public float getSpeed() {
        return 12.0F;
    }

    @Override
    public float getAttackDamageBonus() {
        return 1.0F;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Blocks.REDSTONE_TORCH);
    }
}
