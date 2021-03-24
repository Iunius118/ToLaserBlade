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
        super(new DXLaserBladeItem.ItemTier(), 3, -1.2F, (new Item.Properties()).tab(ModMainItemGroup.ITEM_GROUP));

        tier = getTier();
        attackDamage = 3.0F + tier.getAttackDamageBonus();
        attackSpeed = -1.2F;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getCommandSenderWorld();

        if (!world.isClientSide && attacker instanceof PlayerEntity) {
            playSwingSound(world, attacker);
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        World world = entity.getCommandSenderWorld();

        if (!world.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;

            if (!player.swinging) {
                playSwingSound(world, entity);
            }
        }

        return super.onEntitySwing(stack, entity);
    }

    private void playSwingSound(World world, LivingEntity entity) {
        Vector3d pos = entity.position().add(0, entity.getEyeHeight(), 0).add(entity.getLookAngle());
        world.playSound(null, pos.x, pos.y, pos.z, ModSoundEvents.ITEM_DX_LASER_BLADE_SWING, SoundCategory.PLAYERS, 0.5F, 1.0F);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        PlayerEntity player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getClickedFace();

        if (!(itemstack.getItem() instanceof DXLaserBladeItem)) {
            return ActionResultType.PASS;
        }

        BlockState blockstate = world.getBlockState(pos);
        Block block = blockstate.getBlock();
        int costDamage = tier.getUses() / 2 + 1;

        // Redstone Torch -> Repairing/Collecting

        if ((block == Blocks.REDSTONE_TORCH || block == Blocks.REDSTONE_WALL_TORCH) && player.mayUseItemAt(pos, facing, itemstack)) {
            int itemDamage = itemstack.getDamageValue();
            if (itemDamage >= costDamage || player.abilities.instabuild) {
                // Repair this
                itemstack.setDamageValue(itemDamage - costDamage);
            } else {
                // Collect a Redstone Torch
                if (!player.inventory.add(new ItemStack(Blocks.REDSTONE_TORCH))) {
                    // Cannot collect because player's inventory is full
                    return ActionResultType.PASS;
                }
            }

            // Destroy the Redstone Torch block
            blockstate.removedByPlayer(world, pos, player, false, world.getFluidState(pos));
            return ActionResultType.SUCCESS;
        }

        // Damage -> Redstone Torch

        if (!player.abilities.instabuild && itemstack.getDamageValue() >= costDamage) {
            // This is too damaged to place Redstone Torch
            return ActionResultType.PASS;
        }

        // Place Redstone Torch and Damage this
        ItemStack redstoneTorch = new ItemStack(Blocks.REDSTONE_TORCH);
        ItemUseContext contextRS = new BlockItemUseContext(player, context.getHand(), redstoneTorch,
                new BlockRayTraceResult(context.getClickLocation(), facing, pos, context.isInside()));

        if (player.isSteppingCarefully() && redstoneTorch.useOn(contextRS).consumesAction()) { // player.isSneaking() -> .isSteppingCarefully()
            itemstack.setCount(1);
            itemstack.hurtAndBreak(costDamage, player, playerEntity -> {});
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
                    new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED,
                    new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    public static class ItemTier implements IItemTier {
        @Override
        public int getLevel() {
            return 3;
        }

        @Override
        public int getUses() {
            return 255;
        }

        @Override
        public float getSpeed() {
            return 12.0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 1.0F;
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Blocks.REDSTONE_TORCH);
        }
    }
}
