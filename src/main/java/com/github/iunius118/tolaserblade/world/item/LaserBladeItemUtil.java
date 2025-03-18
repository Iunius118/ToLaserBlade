package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.List;

public class LaserBladeItemUtil {
    public static boolean isFireResistant(ItemStack itemStack) {
        var damageResistant = itemStack.get(DataComponents.DAMAGE_RESISTANT);
        return (damageResistant != null) && (damageResistant.types() == DamageTypeTags.IS_FIRE);
    }

    public static void changeDestroySpeed(PlayerEvent.BreakSpeed event) {
        var player = event.getEntity();
        var itemStack = player.getMainHandItem();
        var item = itemStack.getItem();

        if (!(item instanceof LBSwordItem)) {
            return;
        }

        float destroySpeed = item.getDestroySpeed(itemStack, event.getState());
        float additionalSpeed = event.getOriginalSpeed() - destroySpeed;
        float efficiency = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.EFFICIENCY)
                .map(e -> (float) EnchantmentHelper.getItemEnchantmentLevel(e, itemStack)).orElse(1.0F);
        float rate = Mth.clamp(efficiency / 5.0F, 0.0F, 1.0F);
        float newSpeed = rate * destroySpeed + additionalSpeed;
        event.setNewSpeed(newSpeed);
    }

    public static void playSwingSound(Level level, LivingEntity entity, boolean isFireResistant) {
        var soundEvent = isFireResistant ? ModSoundEvents.ITEM_LASER_BLADE_FP_SWING : ModSoundEvents.ITEM_LASER_BLADE_SWING;
        Vec3 pos = entity.position();
        level.playSound(null, pos.x, pos.y, pos.z, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    public static void playHitSound(Level level, Entity target, ItemStack itemStack) {
        var soundEvent = isFireResistant(itemStack) ? ModSoundEvents.ITEM_LASER_BLADE_FP_HIT : ModSoundEvents.ITEM_LASER_BLADE_HIT;
        Vec3 pos = target.position().add(0, target.getEyeHeight(), 0);
        level.playSound(null, pos.x, pos.y, pos.z, soundEvent, target.getSoundSource(), 1.0F, 1.0F);
    }

    public static void playBlockSound(PlayLevelSoundEvent.AtEntity event, ItemStack itemStack) {
        var soundEvent = isFireResistant(itemStack) ? ModSoundEvents.ITEM_LASER_BLADE_FP_BLOCK : ModSoundEvents.ITEM_LASER_BLADE_BLOCK;
        event.setSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(soundEvent));
        event.setNewVolume(1.0F);
        event.setNewPitch(1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public static void addLaserBladeInformation(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag, Upgrade.Type upgradeType) {
        if (isFireResistant(itemStack)) {
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

    private LaserBladeItemUtil() {}
}
