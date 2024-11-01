package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.api.core.laserblade.LaserBladeState;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.core.component.ModDataComponents;
import com.github.iunius118.tolaserblade.world.item.LaserBladeItemUtil;
import com.github.iunius118.tolaserblade.world.item.ModToolMaterials;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.Objects;

public class LaserBlade {
    public static final float BASE_ATTACK = 3F;
    public static final float BASE_SPEED  = -1.2F;
    public static final float MOD_ATK_MIN = 0.0F;
    public static final float MOD_ATK_MAX = 2040.0F;
    public static final float MOD_ATK_GIFT = 3.0F;
    public static final float MOD_ATK_CRITICAL_BONUS = 8.0F;
    public static final float MOD_CRITICAL_BONUS_VS_WITHER = 0.5F;
    public static final float MOD_SPD_MIN = 0.0F;
    public static final float MOD_SPD_MAX = 1.2F;
    public static final int TYPE_DEFAULT  = 0;

    private LaserBlade() { }

    public static float getAttack(ItemStack itemStack) {
        float attack = Objects.requireNonNullElseGet(itemStack.get(ModDataComponents.LASER_BLADE_ATTACK),
                () -> LaserBladeDataMigrator.getAttack(itemStack)); // Attempt to get and migrate data from CUSTOM_DATA
        return Mth.clamp(attack, MOD_ATK_MIN, MOD_ATK_MAX);
    }

    public static void setAttack(ItemStack itemStack, float damage) {
        itemStack.set(ModDataComponents.LASER_BLADE_ATTACK, damage);
    }

    public static float getSpeed(ItemStack itemStack) {
        float speed = Objects.requireNonNullElseGet(itemStack.get(ModDataComponents.LASER_BLADE_SPEED),
                () -> LaserBladeDataMigrator.getSpeed(itemStack));  // Attempt to get and migrate data from CUSTOM_DATA
        return Mth.clamp(speed, MOD_SPD_MIN, MOD_SPD_MAX);
    }

    public static void setSpeed(ItemStack itemStack, float speed) {
        itemStack.set(ModDataComponents.LASER_BLADE_SPEED, speed);
    }

    public static boolean canUpgradeAttack(float attack) {
        return attack < getMaxAttackUpgradeCount();
    }

    private static float getMaxAttackUpgradeCount() {
        return (float) ToLaserBladeConfig.SERVER.maxAttackDamageUpgradeCount.get();
    }

    public static boolean canUpgradeSpeed(float speed) {
        return speed < MOD_SPD_MAX;
    }

    public static void updateItemAttributeModifiers(ItemStack itemStack) {
        // Attack damage and speed from item compounds and base value
        float attackDamage = LaserBlade.getAttack(itemStack) + BASE_ATTACK;
        float attackSpeed  = LaserBlade.getSpeed(itemStack) + BASE_SPEED;
        Item item = itemStack.getItem();

        // Add attack damage bonus to attack damage
        boolean isFireResistant = LaserBladeItemUtil.isFireResistant(itemStack);
        attackDamage += ModToolMaterials.getLBSwordMaterial(isFireResistant).attackDamageBonus();

        // Update item attribute modifiers
        var itemAttributeModifiers = createAttributes(attackDamage, attackSpeed);
        itemStack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemAttributeModifiers);
    }

    private static ItemAttributeModifiers createAttributes(float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    public static LaserBladeState getState(ItemStack itemStack) {
        float attack = getAttack(itemStack);
        float speed  = getSpeed(itemStack);
        var appearance = LaserBladeAppearance.of(itemStack);
        int type = appearance.getType();
        LaserBladeState.Part outer = appearance.getOuter();
        LaserBladeState.Part inner = appearance.getInner();
        LaserBladeState.Part grip  = appearance.getGrip();
        return new LaserBladeStateImpl(attack, speed, type, outer, inner, grip);
    }

    public record LaserBladeStateImpl(float attack, float speed, int modelType, Part outer, Part inner, Part grip) implements LaserBladeState { }
}
