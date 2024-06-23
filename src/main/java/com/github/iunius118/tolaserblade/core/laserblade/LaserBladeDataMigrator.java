package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.core.component.ModDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/**
 * Class to migrate laser blade data from CompoundTag in CUSTOM_DATA component
 * */
public class LaserBladeDataMigrator {
    public static final String OLD_KEY_ATK = "ATK";
    public static final String OLD_KEY_SPD = "SPD";
    public static final String OLD_KEY_MODEL_TYPE = "type";
    public static final String OLD_KEY_INNER_COLOR = "colorC";
    public static final String OLD_KEY_IS_INNER_SUB_COLOR = "isSubC";
    public static final String OLD_KEY_OUTER_COLOR = "colorH";
    public static final String OLD_KEY_IS_OUTER_SUB_COLOR = "isSubH";
    public static final String OLD_KEY_GRIP_COLOR = "colorG";

    private LaserBladeDataMigrator() { }

    public static float getAttack(ItemStack itemStack) {
        var customData = itemStack.get(DataComponents.CUSTOM_DATA);

        if (customData == null) {
            // Laser blade does not have custom data
            return LaserBlade.MOD_ATK_MIN;
        }

        var tag = customData.copyTag();

        if (!tag.contains(OLD_KEY_ATK, Tag.TAG_ANY_NUMERIC)) {
            // CompoundTag does not have attack damage
            return LaserBlade.MOD_ATK_MIN;
        }

        // Get attack damage from CompoundTag
        float attack = Mth.clamp(tag.getFloat(OLD_KEY_ATK), LaserBlade.MOD_ATK_MIN, LaserBlade.MOD_ATK_MAX);
        // Migrate laser blade data
        itemStack.set(ModDataComponents.LASER_BLADE_ATTACK, attack);
        tag.remove(OLD_KEY_ATK);

        if (tag.isEmpty()) {
            // Remove empty CUSTOM_DATA
            itemStack.remove(DataComponents.CUSTOM_DATA);
        } else {
            itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }

        return attack;
    }

    public static float getSpeed(ItemStack itemStack) {
        var customData = itemStack.get(DataComponents.CUSTOM_DATA);

        if (customData == null) {
            // Laser blade does not have custom data
            return LaserBlade.MOD_SPD_MIN;
        }

        var tag = customData.copyTag();

        if (!tag.contains(OLD_KEY_SPD, Tag.TAG_ANY_NUMERIC)) {
            // CompoundTag does not have attack speed
            return LaserBlade.MOD_SPD_MIN;
        }

        // Get attack speed from CompoundTag
        float speed = Mth.clamp(tag.getFloat(OLD_KEY_SPD), LaserBlade.MOD_SPD_MIN, LaserBlade.MOD_SPD_MAX);
        // Migrate laser blade data
        itemStack.set(ModDataComponents.LASER_BLADE_SPEED, speed);
        tag.remove(OLD_KEY_SPD);

        if (tag.isEmpty()) {
            // Remove empty CUSTOM_DATA
            itemStack.remove(DataComponents.CUSTOM_DATA);
        } else {
            itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }

        return speed;
    }

    public static LaserBladeAppearance getAppearance(ItemStack itemStack) {
        var customData = itemStack.get(DataComponents.CUSTOM_DATA);
        var appearance = LaserBladeAppearance.of();

        if (customData == null) {
            // Laser blade does not have custom data
            return appearance;
        }

        boolean isDataUpdated = false;
        var tag = customData.copyTag();

        if (tag.contains(OLD_KEY_MODEL_TYPE, Tag.TAG_ANY_NUMERIC)) {
            isDataUpdated = true;
            appearance.setType(tag.getInt(OLD_KEY_MODEL_TYPE));
            tag.remove(OLD_KEY_MODEL_TYPE);
        }

        if (tag.contains(OLD_KEY_OUTER_COLOR, Tag.TAG_ANY_NUMERIC)) {
            isDataUpdated = true;
            appearance.setOuterColor(tag.getInt(OLD_KEY_OUTER_COLOR));
            tag.remove(OLD_KEY_OUTER_COLOR);
        }

        if (tag.contains(OLD_KEY_IS_OUTER_SUB_COLOR, Tag.TAG_BYTE)) {
            isDataUpdated = true;
            appearance.setOuterSubColor(tag.getBoolean(OLD_KEY_IS_OUTER_SUB_COLOR));
            tag.remove(OLD_KEY_IS_OUTER_SUB_COLOR);
        }

        if (tag.contains(OLD_KEY_INNER_COLOR, Tag.TAG_ANY_NUMERIC)) {
            isDataUpdated = true;
            appearance.setInnerColor(tag.getInt(OLD_KEY_INNER_COLOR));
            tag.remove(OLD_KEY_INNER_COLOR);
        }

        if (tag.contains(OLD_KEY_IS_INNER_SUB_COLOR, Tag.TAG_BYTE)) {
            isDataUpdated = true;
            appearance.setInnerSubColor(tag.getBoolean(OLD_KEY_IS_INNER_SUB_COLOR));
            tag.remove(OLD_KEY_IS_INNER_SUB_COLOR);
        }

        if (tag.contains(OLD_KEY_GRIP_COLOR, Tag.TAG_ANY_NUMERIC)) {
            isDataUpdated = true;
            appearance.setGripColor(tag.getInt(OLD_KEY_GRIP_COLOR));
            tag.remove(OLD_KEY_GRIP_COLOR);
        }

        if (!isDataUpdated) {
            // Custom data does not have any laser blade data
            return appearance;
        }

        appearance.setTo(itemStack);

        if (tag.isEmpty()) {
            // Remove empty CUSTOM_DATA
            itemStack.remove(DataComponents.CUSTOM_DATA);
        } else {
            itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }

        return appearance;
    }
}
