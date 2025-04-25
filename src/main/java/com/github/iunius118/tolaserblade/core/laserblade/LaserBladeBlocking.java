package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.common.util.ModSoundEventRegistry;
import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.config.TLBServerConfig;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.BlocksAttacks;

import java.util.List;
import java.util.Optional;

public class LaserBladeBlocking {
    public static boolean isShield() {
        return TLBServerConfig.isEnabledBlockingWithLaserBlade;
    }

    public static boolean isBlockingSound(Holder<SoundEvent> sound) {
        if (sound != null) {
            return sound.is(ModSoundEvents.ITEM_LASER_BLADE_BLOCK.location()) || sound.is(ModSoundEvents.ITEM_LASER_BLADE_FP_BLOCK.location());
        }

        return false;
    }

    public static BlocksAttacks getBlocksAttackComponent(boolean isFireResistant) {
        var blockingSoundEvent = isFireResistant ? ModSoundEventRegistry.ITEM_LASER_BLADE_FP_BLOCK : ModSoundEventRegistry.ITEM_LASER_BLADE_BLOCK;
        return new BlocksAttacks(
                0.25F,
                1.0F,
                List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                blockingSoundEvent.getHolder(),
                Optional.of(SoundEvents.SHIELD_BREAK)
        );
    }

    public static ItemUseAnimation getUseAction() {
        if (isShield()) {
            return ItemUseAnimation.BLOCK;
        } else {
            return ItemUseAnimation.NONE;
        }
    }

    public static int getUseDuration() {
        if (isShield()) {
            // 1 hour
            return 72000;
        } else {
            return 0;
        }
    }

    public static void start(Player player, InteractionHand hand) {
        if (isShield()) {
            ItemUseAnimation offhandItemAction = player.getOffhandItem().getUseAnimation();

            if (offhandItemAction != ItemUseAnimation.BOW && offhandItemAction != ItemUseAnimation.SPEAR) {
                player.startUsingItem(hand);
            }
        }
    }
}
