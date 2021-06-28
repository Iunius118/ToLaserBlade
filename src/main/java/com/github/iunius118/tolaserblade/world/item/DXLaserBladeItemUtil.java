package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.common.util.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DXLaserBladeItemUtil {
    private DXLaserBladeItemUtil() {

    }

    public static ActionResultType useOn(ItemUseContext context, IItemTier tier) {
        World world = context.getLevel();
        PlayerEntity player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getClickedFace();
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        int costDamage = tier.getUses() / 2 + 1;

        if ((block == Blocks.REDSTONE_TORCH || block == Blocks.REDSTONE_WALL_TORCH) && player.mayUseItemAt(pos, facing, itemStack)) {
            // Redstone Torch -> Repairing/Collecting
            return removeRedstoneTorch(context, costDamage);
        } else {
            // Damage -> Redstone Torch
            return placeRedstoneTorch(context, costDamage);
        }
    }

    private static ActionResultType removeRedstoneTorch(ItemUseContext context, int costDamage) {
        PlayerEntity player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        int itemDamage = itemStack.getDamageValue();

        if (itemDamage >= costDamage || player.abilities.instabuild) {
            // Repair DX Laser B1ade
            itemStack.setDamageValue(itemDamage - costDamage);
        } else {
            // Collect a Redstone Torch
            if (!player.inventory.add(new ItemStack(Blocks.REDSTONE_TORCH))) {
                // Cannot collect because player's inventory is full
                return ActionResultType.PASS;
            }
        }

        return destroyRedstoneTorch(context);
    }

    private static ActionResultType destroyRedstoneTorch(ItemUseContext context) {
        // Destroy the Redstone Torch block
        World world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        PlayerEntity player = context.getPlayer();
        BlockState blockState = world.getBlockState(pos);

        blockState.removedByPlayer(world, pos, player, false, world.getFluidState(pos));
        return ActionResultType.SUCCESS;
    }

    private static ActionResultType placeRedstoneTorch(ItemUseContext context, int costDamage) {
        PlayerEntity player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();

        if (!player.abilities.instabuild && itemStack.getDamageValue() >= costDamage) {
            // DX Laser B1ade is too damaged to place Redstone Torch
            return ActionResultType.PASS;
        }

        // Place Redstone Torch and damage DX Laser B1ade
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getClickedFace();
        ItemStack redstoneTorch = new ItemStack(Blocks.REDSTONE_TORCH);
        ItemUseContext contextRS = new BlockItemUseContext(player, context.getHand(), redstoneTorch,
                new BlockRayTraceResult(context.getClickLocation(), facing, pos, context.isInside()));

        if (player.isSteppingCarefully() && redstoneTorch.useOn(contextRS).consumesAction()) {
            itemStack.setCount(1);
            itemStack.hurtAndBreak(costDamage, player, playerEntity -> {});
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

    public static void playSwingSound(World world, LivingEntity entity) {
        Vector3d pos = entity.position().add(0, entity.getEyeHeight(), 0).add(entity.getLookAngle());
        world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.ITEM_DX_LASER_BLADE_SWING, SoundCategory.PLAYERS, 0.5F, 1.0F);
    }
}
