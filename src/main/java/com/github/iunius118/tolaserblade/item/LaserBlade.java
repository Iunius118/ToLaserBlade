package com.github.iunius118.tolaserblade.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.Pair;

public class LaserBlade {
    // Blade color table
    public final static LBColor[] colors = {LBColor.WHITE, LBColor.ORANGE, LBColor.MAGENTA, LBColor.LIGHT_BLUE, LBColor.YELLOW, LBColor.LIME, LBColor.PINK, LBColor.GRAY, LBColor.LIGHT_GRAY, LBColor.CYAN, LBColor.PURPLE, LBColor.BLUE, LBColor.BROWN, LBColor.GREEN, LBColor.RED, LBColor.BLACK, LBColor.TEMP_DESERT, LBColor.TEMP_SAVANNA, LBColor.TEMP_JUNGLE, LBColor.TEMP_TAIGA, LBColor.TEMP_ICE_PLAIN, LBColor.TEMP_COLD_TAIGA};

    public static final int DEFAULT_COLOR_INNER = LBColor.WHITE.getBladeColor();
    public static final int DEFAULT_COLOR_OUTER = LBColor.RED.getBladeColor();
    public static final int DEFAULT_COLOR_GRIP = LBColor.WHITE.getGripColor();

    public static final String KEY_ATK = "ATK";
    public static final String KEY_SPD_OLD = "SPD";

    public static final String KEY_INNER_COLOR_24 = "CII";
    public static final String KEY_OUTER_COLOR_24 = "COI";
    public static final String KEY_GRIP_COLOR_24 = "CGI";

    public static final String KEY_INNER_COLOR_8 = "CIB";
    public static final String KEY_OUTER_COLOR_8 = "COB";
    public static final String KEY_GRIP_COLOR_8 = "CGB";

    public static final String KEY_INNER_COLOR_OLD = "colorC";
    public static final String KEY_OUTER_COLOR_OLD = "colorH";
    public static final String KEY_IS_INNER_SUB_COLOR_OLD = "isSubC";
    public static final String KEY_IS_OUTER_SUB_COLOR_OLD = "isSubH";

    public static final float MOD_SPD_MIN = 0.0F;
    public static final float MOD_SPD_MAX = 1.2F;

    public static final float MOD_ATK_MIN = 0.0F;
    public static final float MOD_ATK_CLASS_1 = -1.0F;
    public static final float MOD_ATK_CLASS_2 = 0.0F;
    public static final float MOD_ATK_CLASS_3 = 3.0F;
    public static final float MOD_ATK_CLASS_4 = 7.0F;
    public static final float MOD_ATK_MAX = 2041.0F;

    public static final float MOD_CRITICAL_VS_WITHER = 2.0F;

    public static final int LVL_SMITE_CLASS_1 = 1;
    public static final int LVL_SMITE_CLASS_2 = 2;
    public static final int LVL_SMITE_CLASS_3 = 5;
    public static final int LVL_SMITE_CLASS_4 = 10;

    public static final int COST_LVL_CLASS_2 = 5;
    public static final int COST_LVL_CLASS_4 = 30;

    public static final int MAX_USES = 32000;

    public static int getGripColor(ItemStack stack) {
        int gripColor = DEFAULT_COLOR_GRIP;
        CompoundNBT nbt = stack.getTag();

        if (nbt != null) {
            if (nbt.contains(KEY_GRIP_COLOR_24, Constants.NBT.TAG_INT)) {
                gripColor = nbt.getInt(KEY_GRIP_COLOR_24);
            } else if (nbt.contains(KEY_GRIP_COLOR_8, Constants.NBT.TAG_BYTE)) {
                int bladeGripColorIndex = nbt.getByte(KEY_GRIP_COLOR_8);
                gripColor = getGripColorFromTintIndex(bladeGripColorIndex & 0x7F);
            }
        }

        return gripColor;
    }

    public static Pair<Integer, Boolean> getBladeInnerColor(ItemStack stack) {
        int bladeInnerColor = DEFAULT_COLOR_INNER;
        boolean isBladeInnerSubColor = false;
        CompoundNBT nbt = stack.getTag();

        if (nbt != null) {
            if (nbt.contains(KEY_INNER_COLOR_8, Constants.NBT.TAG_BYTE)) {
                int bladeInnerColorIndex = nbt.getByte(KEY_INNER_COLOR_8);
                isBladeInnerSubColor = (bladeInnerColorIndex & 0x80) != 0;
                bladeInnerColor = getBladeColorFromTintIndex(bladeInnerColorIndex & 0x7F, true);

                if (nbt.contains(KEY_INNER_COLOR_24, Constants.NBT.TAG_INT)) {
                    bladeInnerColor = nbt.getInt(KEY_INNER_COLOR_24);
                }

            } else if (nbt.contains(KEY_INNER_COLOR_OLD, Constants.NBT.TAG_INT)) {
                // From old nbt
                bladeInnerColor = nbt.getInt(KEY_INNER_COLOR_OLD);
                nbt.putInt(KEY_INNER_COLOR_24, bladeInnerColor);
                isBladeInnerSubColor = nbt.getBoolean(KEY_IS_INNER_SUB_COLOR_OLD);
                nbt.putByte(KEY_INNER_COLOR_8, (byte)(isBladeInnerSubColor ? 0x80 : 0));
                nbt.remove(KEY_INNER_COLOR_OLD);
                nbt.remove(KEY_IS_INNER_SUB_COLOR_OLD);
            }
        }

        return Pair.of(bladeInnerColor, isBladeInnerSubColor);
    }

    public static Pair<Integer, Boolean> getBladeOuterColor(ItemStack stack) {
        int bladeOuterColor = DEFAULT_COLOR_OUTER;
        boolean isBladeOuterSubColor = false;
        CompoundNBT nbt = stack.getTag();

        if (nbt != null) {
            if (nbt.contains(KEY_OUTER_COLOR_8, Constants.NBT.TAG_BYTE)) {
                int bladeOuterColorIndex = nbt.getByte(KEY_OUTER_COLOR_8);
                isBladeOuterSubColor = (bladeOuterColorIndex & 0x80) != 0;
                bladeOuterColor = getBladeColorFromTintIndex(bladeOuterColorIndex & 0x7F, false);

                if (nbt.contains(KEY_OUTER_COLOR_24, Constants.NBT.TAG_INT)) {
                    bladeOuterColor = nbt.getInt(KEY_OUTER_COLOR_24);
                }

            } else if (nbt.contains(KEY_OUTER_COLOR_OLD, Constants.NBT.TAG_INT)) {
                // From old nbt
                bladeOuterColor = nbt.getInt(KEY_OUTER_COLOR_OLD);
                nbt.putInt(LaserBlade.KEY_OUTER_COLOR_24, bladeOuterColor);
                isBladeOuterSubColor = nbt.getBoolean(KEY_IS_OUTER_SUB_COLOR_OLD);
                nbt.putByte(KEY_OUTER_COLOR_8, (byte)(isBladeOuterSubColor ? 0x80 : 0));
                nbt.remove(KEY_OUTER_COLOR_OLD);
                nbt.remove(KEY_IS_OUTER_SUB_COLOR_OLD);
            }
        }

        return Pair.of(bladeOuterColor, isBladeOuterSubColor);
    }

    public static Pair<Float, Float> getAttackDamageAndSpeed(ItemStack stack) {
        float attackDamage = 0;
        float attackSpeed = 0;
        CompoundNBT nbt = stack.getTag();

        if (nbt != null) {
            attackDamage = nbt.getFloat(KEY_ATK);

            if (attackDamage < MOD_ATK_MIN) {
                attackDamage = MOD_ATK_MIN;
            } else if (attackDamage > MOD_ATK_MAX) {
                attackDamage = MOD_ATK_MAX;
            }

            int efficiency = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            attackSpeed = (float)efficiency * 0.4F;

            if (attackSpeed < MOD_SPD_MIN) {
                attackSpeed = MOD_SPD_MIN;
            } else if (attackSpeed > MOD_SPD_MAX) {
                attackSpeed = MOD_SPD_MAX;
            }
        }

        return Pair.of(attackDamage, attackSpeed);
    }

    public static float getDestroySpeedRate(ItemStack stack) {
        float rate = 0;
        CompoundNBT nbt = stack.getTag();

        if (nbt != null) {
            rate = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) / 5;

            if (rate < 0) {
                rate = 0;
            } else if (rate > 1) {
                rate = 1;
            }
        }

        return rate;
    }

    public static int getBladeColorFromTintIndex(int index, boolean isInner) {
        return getColorFromTintIndex(index, true, isInner);
    }

    public static int getGripColorFromTintIndex(int index) {
        return getColorFromTintIndex(index, false, false);
    }

    private static int getColorFromTintIndex(int index, boolean isBlade, boolean isInner) {
        if (index >= 0 && index < colors.length) {
           return isBlade ? colors[index].getBladeColor() : colors[index].getGripColor();
        }

        return isBlade ? (isInner ? DEFAULT_COLOR_INNER : DEFAULT_COLOR_OUTER) : DEFAULT_COLOR_GRIP;
    }

    public enum LBColor {
        WHITE(0xFFFFFFFF, 0xFFF9FFFE),
        ORANGE(0xFFFF681F, 0xFFF9801D),
        MAGENTA(0xFFFF0080, 0xFFC74EBD),
        LIGHT_BLUE(0xFF00AAFF, 0xFF3AB3DA),
        YELLOW(0xFFFFD500, 0xFFFED83D),
        LIME(0xFFA9FF32, 0xFF80C71F),
        PINK(0xFFFF004C, 0xFFF38BAA),
        GRAY(0xFF555555, 0xFF474F52),
        LIGHT_GRAY(0xFFAAAAAA, 0xFF9D9D97),
        CYAN(0xFF00FFFF, 0xFF169C9C),
        PURPLE(0xFFFF00FF, 0xFF8932B8),
        BLUE(0xFF0013FF, 0xFF3C44AA),
        BROWN(0xFFFF6B00, 0xFF835432),
        GREEN(0xFFB4FF00, 0xFF5E7C16),
        RED(0xFFFF0000, 0xFFB02E26),
        BLACK(0xFF020202, 0xFF1D1D21),
        TEMP_DESERT(0xFFA000FF, 0xFFFFFFFF),
        TEMP_SAVANNA(0xFFFF00CC, 0xFFFFFFFF),
        TEMP_JUNGLE(0xFFFFC400, 0xFFFFFFFF),
        TEMP_TAIGA(0xFF00FF00, 0xFFFFFFFF),
        TEMP_ICE_PLAIN(0xFF0080FF, 0xFFFFFFFF),
        TEMP_COLD_TAIGA(0xFF0000FF, 0xFFFFFFFF);

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
