package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
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
import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class LaserBladeItemUtil {
    public static float getDestroySpeed(ItemStack itemStack, Tier tier) {
        float rate = (float) EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, itemStack) / 5.0F;
        return tier.getSpeed() * Mth.clamp(rate, 0.0F, 1.0F);
    }

    public static void playSwingSound(Level level, LivingEntity entity, boolean isFireResistant) {
        var soundEvent = isFireResistant ? ModSoundEvents.ITEM_LASER_BLADE_FP_SWING : ModSoundEvents.ITEM_LASER_BLADE_SWING;
        Vec3 pos = entity.position();
        level.playSound(null, pos.x, pos.y, pos.z, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    public static void playHitSound(Level level, Entity target, ItemStack itemStack, boolean isFireResistant) {
        var soundEvent = isFireResistant ? ModSoundEvents.ITEM_LASER_BLADE_FP_HIT : ModSoundEvents.ITEM_LASER_BLADE_HIT;
        Vec3 pos = target.position().add(0, target.getEyeHeight(), 0);
        level.playSound(null, pos.x, pos.y, pos.z, soundEvent, target.getSoundSource(), 1.0F, 1.0F);
    }

    public static void playBlockSound(PlayLevelSoundEvent.AtEntity event, ItemStack itemStack, boolean isFireResistant) {
        var soundEvent = isFireResistant ? ModSoundEvents.ITEM_LASER_BLADE_FP_BLOCK : ModSoundEvents.ITEM_LASER_BLADE_BLOCK;
        Optional<Holder<SoundEvent>> soundEventHolder = ForgeRegistries.SOUND_EVENTS.getHolder(soundEvent);
        soundEventHolder.ifPresent(s -> {
            event.setSound(s);
            event.setNewVolume(1.0F);
            event.setNewPitch(1.0F);
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void addLaserBladeInformation(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag, Upgrade.Type upgradeType) {
        boolean isFireResistant = itemStack.getItem().isFireResistant();
        var laserBlade = LaserBlade.of(itemStack);

        if (isFireResistant) {
            tooltip.add(LaserBladeTextKey.KEY_TOOLTIP_FIREPROOF.translate().withStyle(ChatFormatting.GOLD));
        }

        switch (upgradeType) {
            case BATTERY -> addAttackSpeed(tooltip, laserBlade.getSpeed());
            case MEDIUM -> addAttackDamage(tooltip, laserBlade.getDamage());
            case EMITTER -> {}
            case CASING, OTHER -> addModelType(tooltip, laserBlade);
            case REPAIR -> {
                addModelType(tooltip, laserBlade);
                addAttackDamage(tooltip, laserBlade.getDamage());
                addAttackSpeed(tooltip, laserBlade.getSpeed());
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
    private static void addModelType(List<Component> tooltip, LaserBlade laserBlade) {
        int modelType = laserBlade.getType();

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
        return Component.translatable(key, (value < 0 ? "" : "+") + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(value)).withStyle(ChatFormatting.DARK_GREEN);
    }

    private LaserBladeItemUtil() {}
}
