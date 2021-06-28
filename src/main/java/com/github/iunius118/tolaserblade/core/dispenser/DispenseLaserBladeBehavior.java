package com.github.iunius118.tolaserblade.core.dispenser;

import com.github.iunius118.tolaserblade.common.util.LaserTrapPlayer;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import com.github.iunius118.tolaserblade.world.entity.LaserTrapEntity;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
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

import java.util.List;
import java.util.function.Predicate;

public class DispenseLaserBladeBehavior implements IDispenseItemBehavior {
    public static final IDispenseItemBehavior DEFAULT_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior();
    public static final Predicate<Entity> LASER_TRAP_TARGETS =
            EntityPredicates.NO_SPECTATORS
                    .and(EntityPredicates.ENTITY_STILL_ALIVE)
                    .and(Entity::isPickable)
                    .and(entity -> ToLaserBladeConfig.SERVER.canLaserTrapAttackPlayer.get() || !(entity instanceof PlayerEntity));

    @Override
    public ItemStack dispense(IBlockSource source, ItemStack stack) {
        if (!ToLaserBladeConfig.SERVER.isEnabledLaserTrap.get()) {
            return DEFAULT_ITEM_BEHAVIOR.dispense(source, stack);
        }

        World world = source.getLevel();

        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            BlockPos pos = source.getPos();
            Direction dir = source.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos targetPos = pos.relative(dir);
            TileEntity tile = world.getBlockEntity(targetPos);

            if (ToLaserBladeConfig.SERVER.canLaserTrapHeatUpFurnace.get() && tile instanceof AbstractFurnaceTileEntity) {
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
            boolean isNotBurning = furnaceTile.litTime < 1;

            if (isNotBurning || furnaceTile.litTime < 201) {
                // Set burnTime to 200 (10 seconds)
                furnaceTile.litTime = 201;
                furnaceTile.litDuration = 200;
                furnaceTile.setChanged();

                if (isNotBurning) {
                    // Lit furnace block
                    World world = furnaceTile.getLevel();
                    BlockPos pos = furnaceTile.getBlockPos();
                    world.setBlock(pos, world.getBlockState(pos).setValue(AbstractFurnaceBlock.LIT, Boolean.TRUE), 3);
                }
            }
        }
    }

    private void attackEntities(ServerWorld world, BlockPos pos, Direction dir, ItemStack stack) {
        // Create fake player to attack entities
        LaserTrapPlayer laserTrapPlayer = LaserTrapPlayer.get(world);
        laserTrapPlayer.setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        laserTrapPlayer.initInventory(stack.copy());
        BlockPos targetPos = pos.relative(dir);

        // Attack entities
        attackEntitiesInPos(laserTrapPlayer, targetPos);

        // Remove fake player
        laserTrapPlayer.remove();

        // Spawn laser entity for laser effect
        LaserBladeVisual visual = LaserBlade.visualOf(stack);
        LaserBladeVisual.PartColor outerPartColor = visual.getOuterColor();
        int outerColor = outerPartColor.color;
        outerColor = (outerPartColor.isSubtractColor ? ~outerColor : outerColor) | 0xFF000000;

        LaserTrapEntity laserTrapEntity = new LaserTrapEntity(world, targetPos, dir, outerColor);
        world.addFreshEntity(laserTrapEntity);
    }

    private void attackEntitiesInPos(LaserTrapPlayer laserTrapPlayer, BlockPos pos) {
        // Get target entities
        AxisAlignedBB boundingBox = new AxisAlignedBB(pos).inflate(0.5D);
        List<Entity> targetEntities = laserTrapPlayer.level.getEntities((Entity) null, boundingBox, LASER_TRAP_TARGETS);
        // Get attack damage
        ItemStack stack = laserTrapPlayer.getMainHandItem();
        int level = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.LIGHT_ELEMENT, stack);
        float attackDamage = (float)laserTrapPlayer.getAttribute(Attributes.ATTACK_DAMAGE).getValue() + level * 0.5F;

        // Attack entities
        for (Entity targetEntity : targetEntities) {
            targetEntity.hurt(DamageSource.playerAttack(laserTrapPlayer), attackDamage);
        }
    }
}
