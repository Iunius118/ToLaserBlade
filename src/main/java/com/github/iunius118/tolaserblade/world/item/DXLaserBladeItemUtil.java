package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class DXLaserBladeItemUtil {
    private DXLaserBladeItemUtil() {

    }

    public static InteractionResult useOn(UseOnContext context, Tier tier) {
        var level = context.getLevel();
        var player = context.getPlayer();
        var itemStack = context.getItemInHand();
        var blockPos = context.getClickedPos();
        var direction = context.getClickedFace();
        var blockState = level.getBlockState(blockPos);
        var block = blockState.getBlock();
        int costDamage = tier.getUses() / 2 + 1;

        if ((block == Blocks.REDSTONE_TORCH || block == Blocks.REDSTONE_WALL_TORCH) && player.mayUseItemAt(blockPos, direction, itemStack)) {
            // Redstone Torch -> Repairing/Collecting
            return removeRedstoneTorch(context, costDamage);
        } else {
            // Damage -> Redstone Torch
            return placeRedstoneTorch(context, costDamage);
        }
    }

    private static InteractionResult removeRedstoneTorch(UseOnContext context, int costDamage) {
        var player = context.getPlayer();
        var itemStack = context.getItemInHand();
        int itemDamage = itemStack.getDamageValue();

        if (itemDamage >= costDamage || player.getAbilities().instabuild) {
            // Repair DX Laser B1ade
            itemStack.setDamageValue(itemDamage - costDamage);
        } else {
            // Collect a Redstone Torch
            if (!player.getInventory().add(new ItemStack(Blocks.REDSTONE_TORCH))) {
                // Cannot collect because player's inventory is full
                return InteractionResult.PASS;
            }
        }

        return destroyRedstoneTorch(context);
    }

    private static InteractionResult destroyRedstoneTorch(UseOnContext context) {
        // Destroy the Redstone Torch block
        var level = context.getLevel();
        var blockPos = context.getClickedPos();
        var player = context.getPlayer();
        var blockState = level.getBlockState(blockPos);

        blockState.removedByPlayer(level, blockPos, player, false, level.getFluidState(blockPos));
        return InteractionResult.SUCCESS;
    }

    private static InteractionResult placeRedstoneTorch(UseOnContext context, int costDamage) {
        var player = context.getPlayer();
        var itemStack = context.getItemInHand();

        if (!player.getAbilities().instabuild && itemStack.getDamageValue() >= costDamage) {
            // DX Laser B1ade is too damaged to place Redstone Torch
            return InteractionResult.PASS;
        }

        // Place Redstone Torch and damage DX Laser B1ade
        var blockPos = context.getClickedPos();
        var direction = context.getClickedFace();
        ItemStack redstoneTorch = new ItemStack(Blocks.REDSTONE_TORCH);
        UseOnContext contextRS = new BlockPlaceContext(player, context.getHand(), redstoneTorch,
                new BlockHitResult(context.getClickLocation(), direction, blockPos, context.isInside()));

        if (player.isSteppingCarefully() && redstoneTorch.useOn(contextRS).consumesAction()) {
            itemStack.setCount(1);
            itemStack.hurtAndBreak(costDamage, player, playerEntity -> {});
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static void playSwingSound(Level level, LivingEntity entity) {
        Vec3 pos = entity.position().add(0, entity.getEyeHeight(), 0).add(entity.getLookAngle());
        level.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.ITEM_DX_LASER_BLADE_SWING, SoundSource.PLAYERS, 0.5F, 1.0F);
    }
}
