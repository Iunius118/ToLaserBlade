package com.github.iunius118.tolaserblade.dispenser;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.entity.LaserTrapEntity;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.util.LaserTrapPlayer;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.function.Predicate;

public class DispenseLaserBladeBehavior implements IDispenseItemBehavior {
    public static final IDispenseItemBehavior DEFAULT_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior();
    public static final Predicate<Entity> LASER_TRAP_TARGETS =
            EntityPredicates.NOT_SPECTATING
                    .and(EntityPredicates.IS_ALIVE)
                    .and(Entity::canBeCollidedWith)
                    .and(entity -> ToLaserBladeConfig.COMMON.canLaserTrapAttackPlayer.get() || !(entity instanceof PlayerEntity));

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        if (!ToLaserBladeConfig.COMMON.isEnabledLaserTrap.get()) {
            return DEFAULT_ITEM_BEHAVIOR.dispense(source, stack);
        }

        World world = source.getWorld();

        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            BlockPos pos = source.getBlockPos();
            Direction dir = source.getBlockState().get(DispenserBlock.FACING);
            BlockPos targetPos = pos.offset(dir);
            TileEntity tile = world.getTileEntity(targetPos);

            if (tile instanceof AbstractFurnaceTileEntity) {
                heatFurnace((AbstractFurnaceTileEntity)tile, stack);
            } else {
                attackEntities(serverWorld, pos, dir, stack);
            }
        }

        return stack;
    }

    private void heatFurnace(AbstractFurnaceTileEntity furnaceTile, ItemStack stack) {
        if (stack.getItem() == ModItems.LASER_BLADE_FP) {
            // Only fireproof Laser Blade
            boolean isNotBurning = furnaceTile.burnTime < 1;

            if (isNotBurning || furnaceTile.burnTime < 201) {
                // Set burnTime to 200 (10 seconds)
                furnaceTile.burnTime = 201;
                furnaceTile.recipesUsed = 200;
                furnaceTile.markDirty();

                if (isNotBurning) {
                    // Lit furnace block
                    World world = furnaceTile.getWorld();
                    BlockPos pos = furnaceTile.getPos();
                    world.setBlockState(pos, world.getBlockState(pos).with(AbstractFurnaceBlock.LIT, Boolean.TRUE), 3);
                }
            }
        }
    }

    private void attackEntities(ServerWorld world, BlockPos pos, Direction dir, ItemStack stack) {
        // Create fake player to attack entities
        LaserTrapPlayer laserTrapPlayer = LaserTrapPlayer.get(world);
        laserTrapPlayer.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        laserTrapPlayer.initInventory(stack.copy());
        BlockPos targetPos = pos.offset(dir);

        // Attack entities
        attackEntitiesInPos(laserTrapPlayer, targetPos);

        // Remove fake player
        laserTrapPlayer.remove();

        // Spawn laser entity for laser effect
        Pair<Integer, Boolean> bladeColor = ModItems.LASER_BLADE.getBladeOuterColor(stack);
        int outerColor = ModItems.LASER_BLADE.checkGamingColor(bladeColor.getLeft());
        outerColor = (bladeColor.getRight() ? ~outerColor : outerColor) | 0xFF000000;

        LaserTrapEntity laserTrapEntity = new LaserTrapEntity(world, targetPos, dir, outerColor);
        world.addEntity(laserTrapEntity);
    }

    private void attackEntitiesInPos(LaserTrapPlayer laserTrapPlayer, BlockPos pos) {
        // Get target entities
        AxisAlignedBB boundingBox = new AxisAlignedBB(pos).grow(0.5D);
        List<Entity> targetEntities = laserTrapPlayer.world.getEntitiesInAABBexcluding(null, boundingBox, LASER_TRAP_TARGETS);
        // Get attack damage
        float attackDamage = (float)laserTrapPlayer.getAttribute(Attributes.field_233823_f_).getValue();    // TODO: field_233823_f_ = ATTACK_DAMAGE

        for (Entity targetEntity : targetEntities) {
            // Attack entities
            targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(laserTrapPlayer), attackDamage);
        }
    }
}
