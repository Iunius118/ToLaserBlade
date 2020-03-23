package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.util.ModSoundEvents;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DXLaserBladeItem extends SwordItem implements ModMainItemGroup {
    private final IItemTier tier;
    private final float attackDamage;
    private final float attackSpeed;

    public DXLaserBladeItem() {
        super(new DXLaserBladeItem.ItemTier(), 3, -1.2F, (new Item.Properties()).group(ItemGroup.TOOLS));

        tier = getTier();
        attackDamage = 3.0F + tier.getAttackDamage();
        attackSpeed = -1.2F;
    }

    /*  DX Laser B1ade could sound--if you had a battery :P
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getEntityWorld();

        if (!world.isRemote && attacker instanceof PlayerEntity) {
            playSwingSound(world, attacker);
        }

        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        World world = entity.getEntityWorld();

        if (!world.isRemote && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;

            if (!player.isSwingInProgress) {
                playSwingSound(world, entity);
            }
        }

        return super.onEntitySwing(stack, entity);
    }

    private void playSwingSound(World world, LivingEntity entity) {
        Vec3d pos = entity.getPositionVec().add(0, entity.getEyeHeight(), 0).add(entity.getLookVec());
        world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.ITEM_DX_LASER_BLADE_SWING, SoundCategory.PLAYERS, 0.5F, 1.0F);
    }
    //*/

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        ItemStack itemstack = context.getItem();
        BlockPos pos = context.getPos();
        Direction facing = context.getFace();

        if (!(itemstack.getItem() instanceof DXLaserBladeItem)) {
            return ActionResultType.PASS;
        }

        BlockState blockstate = world.getBlockState(pos);
        Block block = blockstate.getBlock();
        int costDamage = tier.getMaxUses() / 2 + 1;

        // Redstone Torch -> Repairing/Collecting

        if ((block == Blocks.REDSTONE_TORCH || block == Blocks.REDSTONE_WALL_TORCH) && player.canPlayerEdit(pos, facing, itemstack)) {
            int itemDamage = itemstack.getDamage();
            if (itemDamage >= costDamage || player.abilities.isCreativeMode) {
                // Repair this
                itemstack.setDamage(itemDamage - costDamage);
            } else {
                // Collect a Redstone Torch
                if (!player.inventory.addItemStackToInventory(new ItemStack(Blocks.REDSTONE_TORCH))) {
                    // Cannot collect because player's inventory is full
                    return ActionResultType.PASS;
                }
            }

            // Destroy the Redstone Torch block
            if (!world.isRemote) {
                world.destroyBlock(pos, false);
            }

            return ActionResultType.SUCCESS;
        }

        // Damage -> Redstone Torch

        if (!player.abilities.isCreativeMode && itemstack.getDamage() >= costDamage) {
            // This is too damaged to place Redstone Torch
            return ActionResultType.PASS;
        }

        // Place Redstone Torch and Damage this
        if (player.isSteppingCarefully() && Blocks.REDSTONE_TORCH.asItem().onItemUse(context) == ActionResultType.SUCCESS) { // player.isSneaking() -> .isSteppingCarefully()
            itemstack.setCount(1);
            itemstack.damageItem(costDamage, player, playerEntity -> {});
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

        if (slot == EquipmentSlotType.MAINHAND) {
            multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                    new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                    new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    public static class ItemTier implements IItemTier {
        @Override
        public int getHarvestLevel() {
            return 3;
        }

        @Override
        public int getMaxUses() {
            return 255;
        }

        @Override
        public float getEfficiency() {
            return 12.0F;
        }

        @Override
        public float getAttackDamage() {
            return 1.0F;
        }

        @Override
        public int getEnchantability() {
            return 15;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return Ingredient.fromItems(Blocks.REDSTONE_TORCH);
        }
    }
}
