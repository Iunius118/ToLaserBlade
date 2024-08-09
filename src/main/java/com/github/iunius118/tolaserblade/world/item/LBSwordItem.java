package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.dispenser.DispenseLaserBladeBehavior;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeBlocking;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.List;

public class LBSwordItem extends SwordItem implements LaserBladeItemBase {
    public LBSwordItem(boolean isFireproof) {
        super(ModItemTiers.getLBSwordTier(isFireproof),
                LaserBladeItemBase.setFireproof(new Item.Properties(), isFireproof)
                        .attributes(SwordItem.createAttributes(ModItemTiers.getLBSwordTier(isFireproof), 3, LaserBlade.BASE_SPEED)));

        // Register dispense behavior
        DispenserBlock.registerBehavior(this, new DispenseLaserBladeBehavior());
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack stack) {
        LaserBlade.updateItemAttributeModifiers(stack);
        LaserBladeAppearance.of(stack);
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return true;
    }

    /* Shield Functions */

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return super.canPerformAction(stack, itemAbility) || (LaserBladeBlocking.isShield() && ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(itemAbility));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return LaserBladeBlocking.getUseAction();
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return LaserBladeBlocking.getUseDuration();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        LaserBladeBlocking.start(player, hand);

        if (LaserBladeBlocking.isShield()) {
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.pass(itemstack);
        }
    }

    /* Sounds */

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        Level level = entity.getCommandSenderWorld();

        if (!level.isClientSide && entity instanceof Player player) {
            if (!player.swinging) {
                LaserBladeItemUtil.playSwingSound(level, entity, stack.has(DataComponents.FIRE_RESISTANT));
            }
        }

        return super.onEntitySwing(stack, entity);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.getCommandSenderWorld();

        if (!level.isClientSide) {
            LaserBladeItemUtil.playSwingSound(level, attacker, stack.has(DataComponents.FIRE_RESISTANT));
            hurtAndBreak(stack, 1, attacker);
        }

        return true;
    }

    /* Handling Events */

    public void onCriticalHit(CriticalHitEvent event) {
        Entity target = event.getTarget();
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();

        if (target instanceof WitherBoss || LaserBlade.getAttack(stack) >= LaserBlade.MOD_ATK_CRITICAL_BONUS) {
            event.setDamageMultiplier(event.getDamageMultiplier() + LaserBlade.MOD_CRITICAL_BONUS_VS_WITHER);
        }
    }

    /* Item Characterizing */

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            hurtAndBreak(stack, 1, entityLiving);
        }

        return true;
    }

    private void hurtAndBreak(ItemStack stack, int damage, LivingEntity entityLiving) {
        stack.hurtAndBreak(damage, entityLiving, EquipmentSlot.MAINHAND);
        int count = stack.getCount();

        if (count > 0 /* || level.isClientSide */) {
            return;
        }

        // This item has been broken
        stack.setCount(1);
        ItemStack brokenLaserBlade;

        if (stack.has(DataComponents.FIRE_RESISTANT)) {
            brokenLaserBlade = stack.transmuteCopy(ModItems.LB_BROKEN_FP, 1);
        } else {
            brokenLaserBlade = stack.transmuteCopy(ModItems.LB_BROKEN, 1);
        }

        // Remove attribute modifiers from old stack
        brokenLaserBlade.remove(DataComponents.ATTRIBUTE_MODIFIERS);
        // Restore old stack count (and make old stack disappear)
        stack.setCount(count);

        // Drop Broken Laser Blade
        Level level = entityLiving.level();
        ItemEntity itemEntity = new ItemEntity(level, entityLiving.getX(), entityLiving.getY() + 0.5, entityLiving.getZ(), brokenLaserBlade);
        level.addFreshEntity(itemEntity);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return this.getTier().getSpeed();
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return true;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, tooltipContext, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, tooltipContext, tooltip, flag, Upgrade.Type.OTHER);
    }
}
