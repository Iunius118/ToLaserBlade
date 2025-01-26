package com.github.iunius118.tolaserblade.core.dispenser;

import com.github.iunius118.tolaserblade.common.util.LaserTrapPlayer;
import com.github.iunius118.tolaserblade.config.TLBServerConfig;
import com.github.iunius118.tolaserblade.mixin.AbstractFurnaceBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
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
        if (!TLBServerConfig.isEnabledLaserTrap) {
            return DEFAULT_ITEM_BEHAVIOR.dispense(blockSource, itemStack);
        }

        var serverLevel = blockSource.level();
        var pos = blockSource.pos();
        var dir = blockSource.state().getValue(DispenserBlock.FACING);
        var targetBlockEntity = serverLevel.getBlockEntity(pos.relative(dir));

        if (TLBServerConfig.canLaserTrapHeatUpFurnace
                && targetBlockEntity instanceof AbstractFurnaceBlockEntity furnace) {
            // Laser furnace mode
            litFurnace(furnace, itemStack);
        } else {
            // Laser trap attacks entities
            LaserTrapPlayer.attackEntities(serverLevel, pos, itemStack, dir);
        }

        return itemStack;
    }

    private void litFurnace(AbstractFurnaceBlockEntity furnace, ItemStack stack) {
        var furnaceAccessor = (AbstractFurnaceBlockEntityAccessor) furnace;
        final int litTimeRemaining = furnaceAccessor.getLitTimeRemaining();

        if (litTimeRemaining < LASER_BURN_TIME + 1) {
            boolean isNotLit = litTimeRemaining < 1;

            // Set burnTime to 200 (10 seconds)
            furnaceAccessor.setLitTimeRemaining(LASER_BURN_TIME + 1);
            furnaceAccessor.setLitTotalTime(LASER_BURN_TIME);
            furnace.setChanged();

            if (isNotLit) {
                // Lit furnace block
                Level level = furnace.getLevel();
                BlockPos pos = furnace.getBlockPos();
                level.setBlock(pos, level.getBlockState(pos).setValue(AbstractFurnaceBlock.LIT, Boolean.TRUE), 3);
            }
        }
    }
}
