package com.github.iunius118.tolaserblade.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.Pair;

public interface LaserBladeItemBase {
    // Blade color table
    LBColor[] colors = {LBColor.WHITE, LBColor.ORANGE, LBColor.MAGENTA, LBColor.LIGHT_BLUE, LBColor.YELLOW, LBColor.LIME, LBColor.PINK, LBColor.GRAY, LBColor.LIGHT_GRAY, LBColor.CYAN, LBColor.PURPLE, LBColor.BLUE, LBColor.BROWN, LBColor.GREEN, LBColor.RED, LBColor.BLACK, LBColor.TEMP_DESERT, LBColor.TEMP_SAVANNA, LBColor.TEMP_JUNGLE, LBColor.TEMP_TAIGA, LBColor.TEMP_ICE_PLAIN, LBColor.TEMP_COLD_TAIGA};

    int DEFAULT_COLOR_INNER = LBColor.WHITE.getBladeColor();
    int DEFAULT_COLOR_OUTER = LBColor.RED.getBladeColor();
    int DEFAULT_COLOR_GRIP = LBColor.WHITE.getGripColor();

    String KEY_ATK = "ATK";
    String KEY_SPD = "SPD";
    String KEY_LIGHTNESS = "LTN";

    String KEY_GRIP_COLOR = "colorG";
    String KEY_INNER_COLOR = "colorC";
    String KEY_OUTER_COLOR = "colorH";
    String KEY_IS_INNER_SUB_COLOR = "isSubC";
    String KEY_IS_OUTER_SUB_COLOR = "isSubH";

    float MOD_SPD_MIN = 0.0F;
    float MOD_SPD_MAX = 1.2F;
    float MOD_SPD_EFFICIENCY_MAX = 2.0F;

    float MOD_ATK_MIN = 0.0F;
    float MOD_ATK_CLASS_1 = -1.0F;
    float MOD_ATK_CLASS_2 = 0.0F;
    float MOD_ATK_CLASS_3 = 3.0F;
    float MOD_ATK_CLASS_4 = 7.0F;
    float MOD_ATK_CLASS_5 = 8.0F;
    float MOD_ATK_MAX = 2041.0F;

    float MOD_CRITICAL_VS_WITHER = 2.0F;

    int LVL_SMITE_CLASS_1 = 1;
    int LVL_SMITE_CLASS_2 = 2;
    int LVL_SMITE_CLASS_3 = 5;
    int LVL_SMITE_CLASS_4 = 10;

    int COST_LVL_CLASS_2 = 5;
    int COST_LVL_CLASS_4 = 30;

    int MAX_USES = 32000;

    static int getGripColor(ItemStack stack) {
        int gripColor = DEFAULT_COLOR_GRIP;
        CompoundNBT nbt = stack.getTag();

        if (nbt != null && nbt.contains(KEY_GRIP_COLOR, Constants.NBT.TAG_INT)) {
            gripColor = nbt.getInt(KEY_GRIP_COLOR);
        }

        return gripColor;
    }

    static Pair<Integer, Boolean> getBladeInnerColor(ItemStack stack) {
        int bladeInnerColor = DEFAULT_COLOR_INNER;
        boolean isBladeInnerSubColor = false;
        CompoundNBT nbt = stack.getTag();

        if (nbt != null) {
            if (nbt.contains(KEY_INNER_COLOR, Constants.NBT.TAG_INT)) {
                bladeInnerColor = nbt.getInt(KEY_INNER_COLOR);
            }

            isBladeInnerSubColor = nbt.getBoolean(KEY_IS_INNER_SUB_COLOR);
        }

        return Pair.of(bladeInnerColor, isBladeInnerSubColor);
    }

    static Pair<Integer, Boolean> getBladeOuterColor(ItemStack stack) {
        int bladeOuterColor = DEFAULT_COLOR_OUTER;
        boolean isBladeOuterSubColor = false;
        CompoundNBT nbt = stack.getTag();

        if (nbt != null) {
            if (nbt.contains(KEY_OUTER_COLOR, Constants.NBT.TAG_INT)) {
                bladeOuterColor = nbt.getInt(KEY_OUTER_COLOR);
            }

            isBladeOuterSubColor = nbt.getBoolean(KEY_IS_OUTER_SUB_COLOR);
        }

        return Pair.of(bladeOuterColor, isBladeOuterSubColor);
    }


    default void setGripColor(ItemStack stack, int color) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putInt(KEY_GRIP_COLOR, color);
    }

    default void setBladeInnerColor(ItemStack stack, int color) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putInt(KEY_INNER_COLOR, color);
    }

    default void setBladeOuterColor(ItemStack stack, int color) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putInt(KEY_OUTER_COLOR, color);
    }

    default void setBladeInnerSubColorFlag(ItemStack stack, boolean isSubColor) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putBoolean(KEY_IS_INNER_SUB_COLOR, isSubColor);
    }

    default void setBladeOuterSubColorFlag(ItemStack stack, boolean isSubColor) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putBoolean(KEY_IS_OUTER_SUB_COLOR, isSubColor);
    }

    default float getLaserBladeATK(ItemStack stack) {
        float attackDamage = 0;
        CompoundNBT nbt = stack.getTag();

        if (nbt != null) {
            attackDamage = nbt.getFloat(KEY_ATK);

            if (attackDamage < MOD_ATK_MIN) {
                attackDamage = MOD_ATK_MIN;
            } else if (attackDamage > MOD_ATK_MAX) {
                attackDamage = MOD_ATK_MAX;
            }
        }

        return attackDamage;
    }

    default float getLaserBladeSPD(ItemStack stack) {
        float attackSpeed = 0.0F;
        CompoundNBT nbt = stack.getTag();

        if (nbt != null) {
            attackSpeed = nbt.getFloat(KEY_SPD);

            if (attackSpeed < MOD_SPD_MIN) {
                attackSpeed = MOD_SPD_MIN;
            } else if (attackSpeed > MOD_SPD_MAX) {
                attackSpeed = MOD_SPD_MAX;
            }
        }

        return attackSpeed;
    }

    default float getDestroySpeedRate(ItemStack stack) {
        float rate = 0.0F;
        CompoundNBT nbt = stack.getTag();

        if (nbt != null) {
            rate = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) / 5;

            if (rate < 0.0F) {
                rate = 0.0F;
            } else if (rate > 1.0F) {
                rate = 1.0F;
            }
        }

        return rate;
    }

    default void setLaserBladeATK(ItemStack stack, float atk) {
        float attackDamage = atk;
        CompoundNBT nbt = stack.getOrCreateTag();

        if (attackDamage < MOD_ATK_MIN) {
            attackDamage = MOD_ATK_MIN;
        } else if (attackDamage > MOD_ATK_MAX) {
            attackDamage = MOD_ATK_MAX;
        }

        nbt.putFloat(KEY_ATK, attackDamage);
    }

    default void setLaserBladeSPD(ItemStack stack, float spd) {
        float attackSpeed = spd;
        CompoundNBT nbt = stack.getOrCreateTag();

        if (attackSpeed < MOD_SPD_MIN) {
            attackSpeed = MOD_SPD_MIN;
        } else if (attackSpeed > MOD_SPD_MAX) {
            attackSpeed = MOD_SPD_MAX;
        }


        nbt.putFloat(KEY_SPD, attackSpeed);
    }

    default int getBladeColorFromTintIndex(int index, boolean isInner) {
        return getColorFromTintIndex(index, true, isInner);
    }

    default int getGripColorFromTintIndex(int index) {
        return getColorFromTintIndex(index, false, false);
    }

    default int getColorFromTintIndex(int index, boolean isBlade, boolean isInner) {
        if (index >= 0 && index < colors.length) {
           return isBlade ? colors[index].getBladeColor() : colors[index].getGripColor();
        }

        return isBlade ? (isInner ? DEFAULT_COLOR_INNER : DEFAULT_COLOR_OUTER) : DEFAULT_COLOR_GRIP;
    }

    enum LBColor {
        WHITE(0xFFFFFFFF, 0xFFF9FFFE),
        ORANGE(0xFFFF681F, 0xFFF9801D),
        MAGENTA(0xFFFF0080, 0xFFC74EBD),
        LIGHT_BLUE(0xFF00AAFF, 0xFF3AB3DA),
        YELLOW(0xFFFFEE00, 0xFFFED83D),
        LIME(0xFFA9FF32, 0xFF80C71F),
        PINK(0xFFFF004C, 0xFFF38BAA),
        GRAY(0xFF555555, 0xFF474F52),
        LIGHT_GRAY(0xFFAAAAAA, 0xFF9D9D97),
        CYAN(0xFF00FFFF, 0xFF169C9C),
        PURPLE(0xFFFF00FF, 0xFF8932B8),
        BLUE(0xFF0011FF, 0xFF3C44AA),
        BROWN(0xFFFF6B00, 0xFF835432),
        GREEN(0xFF80FF00, 0xFF5E7C16),
        RED(0xFFFF0000, 0xFFB02E26),
        BLACK(0xFF020202, 0xFF1D1D21),
        TEMP_DESERT(0xFFA000FF, 0xFFF9FFFE),
        TEMP_SAVANNA(0xFFFF00CC, 0xFFF9FFFE),
        TEMP_JUNGLE(0xFFFFC400, 0xFFF9FFFE),
        TEMP_TAIGA(0xFF00FF00, 0xFFF9FFFE),
        TEMP_ICE_PLAIN(0xFF0080FF, 0xFFF9FFFE),
        TEMP_COLD_TAIGA(0xFF0000FF, 0xFFF9FFFE);

        private int bladeColor;
        private int gripColor;

        LBColor(int bladeColorIn, int gripColorIn) {
            bladeColor = bladeColorIn;
            gripColor = gripColorIn;
        }

        public int getBladeColor() {
            return bladeColor;
        }

        public int getGripColor() {
            return gripColor;
        }
    }
}
