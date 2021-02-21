package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.util.ModSoundEvents;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DXLaserBladeItem extends SwordItem {
    private final IItemTier tier;
    private final float attackDamage;
    private final float attackSpeed;

    public DXLaserBladeItem() {
        super(new DXLaserBladeItem.ItemTier(), 3, -1.2F, (new Item.Properties()).group(ModMainItemGroup.ITEM_GROUP));

        tier = getTier();
        attackDamage = 3.0F + tier.getAttackDamage();
        attackSpeed = -1.2F;
    }

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
        Vector3d pos = entity.getPositionVec().add(0, entity.getEyeHeight(), 0).add(entity.getLookVec());
        world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.ITEM_DX_LASER_BLADE_SWING, SoundCategory.PLAYERS, 0.5F, 1.0F);
    }

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
            blockstate.removedByPlayer(world, pos, player, false, world.getFluidState(pos));
            return ActionResultType.SUCCESS;
        }

        // Damage -> Redstone Torch

        if (!player.abilities.isCreativeMode && itemstack.getDamage() >= costDamage) {
            // This is too damaged to place Redstone Torch
            return ActionResultType.PASS;
        }

        // Place Redstone Torch and Damage this
        ItemStack redstoneTorch = new ItemStack(Blocks.REDSTONE_TORCH);
        ItemUseContext contextRS = new BlockItemUseContext(player, context.getHand(), redstoneTorch,
                new BlockRayTraceResult(context.getHitVec(), facing, pos, context.isInside()));

        if (player.isSteppingCarefully() && redstoneTorch.onItemUse(contextRS).isSuccessOrConsume()) { // player.isSneaking() -> .isSteppingCarefully()
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
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();

        if (slot == EquipmentSlotType.MAINHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED,
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
