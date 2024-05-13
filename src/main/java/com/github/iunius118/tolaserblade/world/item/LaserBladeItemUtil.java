package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class LaserBladeItemUtil {
    private LaserBladeItemUtil() {

    }

    public static float getDestroySpeed(ItemStack itemStack, Tier tier) {
        float rate = (float) itemStack.getEnchantmentLevel(Enchantments.EFFICIENCY) / 5.0F;
        return tier.getSpeed() * Mth.clamp(rate, 0.0F, 1.0F);
    }

    public static void playSwingSound(Level level, LivingEntity entity, boolean isFireResistant) {
        SoundEvent soundEvent = isFireResistant ? ModSoundEvents.ITEM_LASER_BLADE_FP_SWING : ModSoundEvents.ITEM_LASER_BLADE_SWING;
        Vec3 pos = entity.position().add(0, entity.getEyeHeight(), 0).add(entity.getLookAngle());
        level.playSound(null, pos.x, pos.y, pos.z, soundEvent, SoundSource.PLAYERS, 0.5F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public static void addLaserBladeInformation(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag, Upgrade.Type upgradeType) {
        boolean isFireResistant = itemStack.has(DataComponents.FIRE_RESISTANT);

        if (isFireResistant) {
            tooltip.add(LaserBladeTextKey.KEY_TOOLTIP_FIREPROOF.translate().withStyle(ChatFormatting.GOLD));
        }

        switch (upgradeType) {
            case BATTERY -> addAttackSpeed(tooltip, LaserBlade.getSpeed(itemStack));
            case MEDIUM -> addAttackDamage(tooltip, LaserBlade.getAttack(itemStack));
            case EMITTER -> {}
            case CASING, OTHER -> addModelType(tooltip, itemStack);
            case REPAIR -> {
                addModelType(tooltip, itemStack);
                addAttackDamage(tooltip, LaserBlade.getAttack(itemStack));
                addAttackSpeed(tooltip, LaserBlade.getSpeed(itemStack));
            }
            default -> {}
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void addBrandNewText(List<Component> tooltip) {
        tooltip.add(Component.translatable("tooltip.tolaserblade.brandNew1").withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.translatable("tooltip.tolaserblade.brandNew2").withStyle(ChatFormatting.YELLOW));
        tooltip.add(Component.translatable("tooltip.tolaserblade.brandNew3").withStyle(ChatFormatting.YELLOW));
    }

    @OnlyIn(Dist.CLIENT)
    private static void addModelType(List<Component> tooltip, ItemStack itemStack) {
        int modelType = LaserBladeAppearance.of(itemStack).getType();

        if (modelType >= 0) {
            tooltip.add(LaserBladeTextKey.KEY_TOOLTIP_MODEL.translate(modelType).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void addAttackDamage(List<Component> tooltip, float atk) {
        if (atk <= -0.005F || atk >= 0.005) {
            tooltip.add(getUpgradeTextComponent(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_DAMAGE.getKey(), atk));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void addAttackSpeed(List<Component> tooltip, float spd) {
        if (spd <= -0.005F || spd >= 0.005) {
            tooltip.add(getUpgradeTextComponent(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_SPEED.getKey(), spd));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static Component getUpgradeTextComponent(String key, float value) {
        return Component.translatable(key, (value < 0 ? "" : "+") + ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(value))
                .withStyle(ChatFormatting.DARK_GREEN);
    }
}
