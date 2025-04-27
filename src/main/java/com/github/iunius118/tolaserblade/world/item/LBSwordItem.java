package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.config.TLBServerConfig;
import com.github.iunius118.tolaserblade.core.dispenser.DispenseLaserBladeBehavior;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeBlocking;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.mixin.ItemAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.ArrayList;
import java.util.List;

public class LBSwordItem extends Item implements LaserBladeItemBase {
    public LBSwordItem(Properties properties, boolean isFireResistant) {
        // Apply sword properties
        super(ModToolMaterials.getLBSwordMaterial(isFireResistant).applySwordProperties(properties, LaserBlade.BASE_ATTACK, LaserBlade.BASE_SPEED));
        // ... and overwrite the tool component
        overwriteToolComponent(isFireResistant);

        // Register dispense behavior
        DispenserBlock.registerBehavior(this, new DispenseLaserBladeBehavior());
    }

    private void overwriteToolComponent(boolean isFireResistant) {
        DataComponentMap.Builder builder = DataComponentMap.builder().addAll(this.components());
        // Remove some components to handle mining speed and repairability on the mod side
        float speed = ModToolMaterials.getLBSwordMaterial(isFireResistant).speed();
        builder.set(DataComponents.TOOL, new Tool(List.of(), speed, 1, false));
        builder.set(DataComponents.REPAIRABLE, null);
        // Add ability to block attacks to laser blade
        builder.set(DataComponents.BLOCKS_ATTACKS, LaserBladeBlocking.getBlocksAttackComponent(isFireResistant));
        ((ItemAccessor) this).setComponents(builder.build());
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
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        var consumable = stack.get(DataComponents.CONSUMABLE);

        if (consumable != null) {
            return consumable.animation();
        } else {
            return LaserBladeBlocking.getUseAnimation(stack);
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Consumable consumable = itemstack.get(DataComponents.CONSUMABLE);

        if (consumable != null) {
            return consumable.startConsuming(player, itemstack, hand);
        } else {
            Equippable equippable = itemstack.get(DataComponents.EQUIPPABLE);

            if (equippable != null && equippable.swappable()) {
                return equippable.swapWithEquipmentSlot(itemstack, player);
            } else {
                return LaserBladeBlocking.use(player, hand);
            }
        }
    }

    /* Sounds */

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        Level level = entity.getCommandSenderWorld();

        if (!level.isClientSide && entity instanceof Player player) {
            if (!player.swinging) {
                LaserBladeItemUtil.playSwingSound(level, entity, LaserBladeItemUtil.isFireResistant(stack));
            }
        }

        return super.onEntitySwing(stack, entity, hand);
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.getCommandSenderWorld();

        if (!level.isClientSide) {
            LaserBladeItemUtil.playSwingSound(level, attacker, LaserBladeItemUtil.isFireResistant(stack));
            hurtAndBreak(stack, 1, attacker);
        }
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

        if (LaserBladeItemUtil.isFireResistant(stack)) {
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
        // Normally the config value is used, but it can be overwritten by Tool component
        if (!stack.has(DataComponents.TOOL)) {
            return (float) TLBServerConfig.laserBladeEfficiency;
        } else {
            return super.getDestroySpeed(stack, state);
        }
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
    public void appendTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipFlag flag, List<Component> tooltip) {
        ArrayList<Component> myTooltip = new ArrayList<>();
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, tooltipContext, myTooltip, flag, Upgrade.Type.OTHER);
        tooltip.addAll(1, myTooltip);
    }
}
