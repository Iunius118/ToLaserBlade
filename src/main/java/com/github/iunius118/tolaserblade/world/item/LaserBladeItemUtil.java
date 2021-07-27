package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladePerformance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.mojang.math.Vector3d;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class LaserBladeItemUtil {
    private LaserBladeItemUtil() {

    }

    public static float getDestroySpeed(ItemStack itemStack, Tier tier) {
        float rate = (float) EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, itemStack) / 5.0F;
        return tier.getSpeed() * Mth.clamp(rate, 0.0F, 1.0F);
    }

    public static void playSwingSound(Level level, LivingEntity entity, boolean isFireResistant) {
        SoundEvent soundEvent = isFireResistant ? ModSoundEvents.ITEM_LASER_BLADE_FP_SWING : ModSoundEvents.ITEM_LASER_BLADE_SWING;
        Vec3 pos = entity.position().add(0, entity.getEyeHeight(), 0).add(entity.getLookAngle());
        level.playSound(null, pos.x, pos.y, pos.z, soundEvent, SoundSource.PLAYERS, 0.5F, 1.0F);
    }

    public static void addItemStacks(NonNullList<ItemStack> items, boolean isFireResistant) {
        if (isFireResistant) {
            items.add(LaserBladeItemStack.UPGRADED_FP.getCopy());
            items.add(LaserBladeItemStack.DAMAGED_FP.getCopy());
            items.add(LaserBladeItemStack.FULL_MOD_FP.getCopy());
        } else {
            items.add(LaserBladeItemStack.MODEL_TYPE_526.getCopy());
            items.add(LaserBladeItemStack.LIGHT_ELEMENT_1.getCopy());
            items.add(LaserBladeItemStack.LIGHT_ELEMENT_2.getCopy());
            items.add(LaserBladeItemStack.GIFT.getCopy());
            items.add(LaserBladeItemStack.UPGRADED.getCopy());
            items.add(LaserBladeItemStack.DAMAGED.getCopy());
            items.add(LaserBladeItemStack.FULL_MOD.getCopy());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void addLaserBladeInformation(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag, Upgrade.Type upgradeType) {
        LaserBlade laserBlade = LaserBlade.of(itemStack);
        boolean isFireproof = laserBlade.isFireproof();

        if (isFireproof) {
            tooltip.add(LaserBladeTextKey.KEY_TOOLTIP_FIREPROOF.translate().withStyle(ChatFormatting.GOLD));
        }

        switch (upgradeType) {
            case BATTERY -> addAttackSpeed(tooltip, laserBlade.getAttackPerformance().speed);
            case MEDIUM -> addAttackDamage(tooltip, laserBlade.getAttackPerformance().damage);
            case EMITTER -> {}
            case CASING, OTHER -> addModelType(tooltip, laserBlade);
            case REPAIR -> {
                addModelType(tooltip, laserBlade);
                LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
                addAttackDamage(tooltip, attack.damage);
                addAttackSpeed(tooltip, attack.speed);
            }
            default -> {}
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void addBrandNewText(List<Component> tooltip) {
        tooltip.add(new TranslatableComponent("tooltip.tolaserblade.brandNew1").withStyle(ChatFormatting.YELLOW));
        tooltip.add(new TranslatableComponent("tooltip.tolaserblade.brandNew2").withStyle(ChatFormatting.YELLOW));
        tooltip.add(new TranslatableComponent("tooltip.tolaserblade.brandNew3").withStyle(ChatFormatting.YELLOW));
    }

    @OnlyIn(Dist.CLIENT)
    private static void addModelType(List<Component> tooltip, LaserBlade laserBlade) {
            LaserBladeVisual visual = laserBlade.getVisual();
            int modelType = visual.getModelType();

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
        return new TranslatableComponent(key, (value < 0 ? "" : "+") + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(value)).withStyle(ChatFormatting.DARK_GREEN);
    }
}
