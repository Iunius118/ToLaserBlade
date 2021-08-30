package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ModItemTiers {
    private final static int LB_HARVEST_LEVEL = 3;
    private final static int LB_FP_HARVEST_LEVEL = 4;
    private static final int LB_MAX_USES = 32000;
    private static final Supplier<Float> LB_SPEED = () -> (float) ToLaserBladeConfig.SERVER.laserBladeEfficiency.get();
    private final static float LB_DAMAGE_BONUS = 3.0F;
    private final static float LB_FP_DAMAGE_BONUS = 4.0F;
    private final static int LB_ENCHANTMENT_VALUE = 15;
    private final static Supplier<Ingredient> LB_REPAIR_INGREDIENT = () -> Ingredient.of(ModItemTags.CASING_REPAIR);

    public static final Tier DX_LASER_BLADE = new ModItemTier(3, 255, () -> 12.0F, 1.0F, 15, () -> Ingredient.of(Blocks.REDSTONE_TORCH));
    public static final Tier LASER_BLADE = new ModItemTier(LB_HARVEST_LEVEL, LB_MAX_USES, LB_SPEED, LB_DAMAGE_BONUS, LB_ENCHANTMENT_VALUE, LB_REPAIR_INGREDIENT);
    public static final Tier LASER_BLADE_FP = new ModItemTier(LB_FP_HARVEST_LEVEL, LB_MAX_USES, LB_SPEED, LB_FP_DAMAGE_BONUS, LB_ENCHANTMENT_VALUE, LB_REPAIR_INGREDIENT);

    public static Tier getLBSwordTier(boolean isFireproof) {
        return isFireproof ? LASER_BLADE_FP : LASER_BLADE;
    }

    public record ModItemTier(int level, int uses, @Nonnull Supplier<Float> speed, float attackDamageBonus, int enchantmentValue, @Nonnull Supplier<Ingredient> repairIngredient)
            implements Tier {
        @Override
        public int getUses() {
            return this.uses;
        }

        @Override
        public float getSpeed() {
            return this.speed.get();
        }

        @Override
        public float getAttackDamageBonus() {
            return this.attackDamageBonus;
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getEnchantmentValue() {
            return this.enchantmentValue;
        }

        @Nonnull
        @Override
        public Ingredient getRepairIngredient() {
            return this.repairIngredient.get();
        }
    }
}
