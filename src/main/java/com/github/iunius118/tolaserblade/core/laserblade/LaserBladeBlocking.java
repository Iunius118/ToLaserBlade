package com.github.iunius118.tolaserblade.core.laserblade;

import com.github.iunius118.tolaserblade.common.util.ModSoundEventRegistry;
import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import com.github.iunius118.tolaserblade.config.TLBServerConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
            return sound.is(ModSoundEvents.ITEM_LASER_BLADE_BLOCK.location())
                    || sound.is(ModSoundEvents.ITEM_LASER_BLADE_FP_BLOCK.location());
        }

        return false;
    }

    public static BlocksAttacks getBlocksAttackComponent(boolean isFireResistant) {
        var blockingSoundEvent = isFireResistant ?
                ModSoundEventRegistry.ITEM_LASER_BLADE_FP_BLOCK : ModSoundEventRegistry.ITEM_LASER_BLADE_BLOCK;
        return new BlocksAttacks(
                0.25F,
                1.0F,
                List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                Optional.of(blockingSoundEvent),
                Optional.of(SoundEvents.SHIELD_BREAK)
        );
    }

    public static ItemUseAnimation getUseAnimation(ItemStack stack) {
        if (LaserBladeBlocking.isShield()) {
            // Return BLOCK only when blocking is allowed in config...
            return ItemUseAnimation.BLOCK;
        }

        Optional<? extends BlocksAttacks> patched = stack.getComponentsPatch().get(DataComponents.BLOCKS_ATTACKS);

        if (patched != null && patched.isPresent()) {
            // ... or BLOCKS_ATTACKS was given in command
            return ItemUseAnimation.BLOCK;
        } else {
            return ItemUseAnimation.NONE;
        }
    }

    public static InteractionResult use(Player player, InteractionHand hand) {
        if (LaserBladeBlocking.isShield()) {
            // Start using item only when blocking is allowed in config...
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }

        ItemStack stack = player.getItemInHand(hand);
        Optional<? extends BlocksAttacks> patched = stack.getComponentsPatch().get(DataComponents.BLOCKS_ATTACKS);

        if (patched != null && patched.isPresent()) {
            // ... or BLOCKS_ATTACKS was given in command
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.PASS;
        }
    }
}
