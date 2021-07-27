package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class DXLaserBladeItemTier implements Tier {
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
