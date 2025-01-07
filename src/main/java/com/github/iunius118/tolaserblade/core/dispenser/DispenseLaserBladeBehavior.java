package com.github.iunius118.tolaserblade.core.dispenser;

import com.github.iunius118.tolaserblade.common.util.LaserTrapPlayer;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.mixin.AbstractFurnaceBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;


public class DispenseLaserBladeBehavior implements DispenseItemBehavior {
    public static final DispenseItemBehavior DEFAULT_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior();
    public static final int LASER_BURN_TIME = 200;


    @Override
    public ItemStack dispense(BlockSource blockSource, ItemStack itemStack) {
        if (!ToLaserBladeConfig.SERVER.isEnabledLaserTrap.get()) {
            return DEFAULT_ITEM_BEHAVIOR.dispense(blockSource, itemStack);
        }

        var serverLevel = blockSource.getLevel();
        var pos = blockSource.getPos();
        var dir = blockSource.getBlockState().getValue(DispenserBlock.FACING);
        var targetBlockEntity = serverLevel.getBlockEntity(pos.relative(dir));

        if (ToLaserBladeConfig.SERVER.canLaserTrapHeatUpFurnace.get()
                && targetBlockEntity instanceof AbstractFurnaceBlockEntity furnace) {
            litFurnace(furnace, itemStack);
        } else {
            attackEntities(serverLevel, pos, dir, itemStack);
        }

        return itemStack;
    }

    private void litFurnace(AbstractFurnaceBlockEntity furnace, ItemStack stack) {
        var furnaceAccessor = (AbstractFurnaceBlockEntityAccessor) furnace;
        final int litTime = furnaceAccessor.getLitTime();

        if (litTime < LASER_BURN_TIME + 1) {
            boolean isNotLit = litTime < 1;

            // Set burnTime to 200 (10 seconds)
            furnaceAccessor.setLitTime(LASER_BURN_TIME + 1);
            furnaceAccessor.setLitDuration(LASER_BURN_TIME);
            furnace.setChanged();

            if (isNotLit) {
                // Lit furnace block
                Level level = furnace.getLevel();
                BlockPos pos = furnace.getBlockPos();
                level.setBlock(pos, level.getBlockState(pos).setValue(AbstractFurnaceBlock.LIT, Boolean.TRUE), 3);
            }
        }
    }

    private void attackEntities(ServerLevel serverLevel, BlockPos trapPos, Direction dir, ItemStack stack) {
        var laserTrapPlayer = LaserTrapPlayer.get(serverLevel, trapPos, stack);
        laserTrapPlayer.attackEntities(dir);
        laserTrapPlayer.remove(Entity.RemovalReason.DISCARDED);
    }
}
