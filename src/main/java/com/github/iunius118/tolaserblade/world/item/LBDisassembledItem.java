package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class LBDisassembledItem extends Item implements LaserBladeItemBase {
    public static Properties properties = (new Properties()).setNoRepair();
    public final Upgrade.Type upgradeType = Upgrade.Type.REPAIR;

    public LBDisassembledItem(boolean isFireproof) {
        super(LaserBladeItemBase.setFireproof(properties, isFireproof));
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            disassembleLaserBlade(level, player, itemStack);
            itemStack.shrink(1);
        }

        return InteractionResultHolder.success(itemStack);
    }

    private void disassembleLaserBlade(Level level, Player player, ItemStack itemStack) {
        ItemStack batteryStack = new ItemStack(ModItems.LB_BATTERY);
        ItemStack mediumStack = new ItemStack(ModItems.LB_MEDIUM);
        ItemStack emitterStack = new ItemStack(ModItems.LB_EMITTER);
        ItemStack casingStack = new ItemStack(itemStack.getItem().isFireResistant() ? ModItems.LB_CASING_FP : ModItems.LB_CASING);

        LaserBlade laserBlade = LaserBlade.of(itemStack);
        LaserBlade.Writer battery = LaserBlade.Writer.of(batteryStack);
        LaserBlade.Writer medium = LaserBlade.Writer.of(mediumStack);
        LaserBlade.Writer emitter = LaserBlade.Writer.of(emitterStack);
        LaserBlade.Writer casing = LaserBlade.Writer.of(casingStack);

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);

        // Process attacks
        battery.writeSpeed(laserBlade.getSpeed());
        medium.writeDamage(laserBlade.getDamage());

        // Process enchantments
        enchantments.forEach((enchantment, lvl) -> {
            if (enchantment == Enchantments.BLOCK_EFFICIENCY) {
                batteryStack.enchant(enchantment, lvl);

            } else if (enchantment instanceof DamageEnchantment ||
                    enchantment == Enchantments.KNOCKBACK ) {
                mediumStack.enchant(enchantment, lvl);

            } else if (enchantment == Enchantments.FIRE_ASPECT ||
                    enchantment == Enchantments.SWEEPING_EDGE ||
                    enchantment == Enchantments.SILK_TOUCH) {
                emitterStack.enchant(enchantment, lvl);

            } else if (enchantment == Enchantments.VANISHING_CURSE) {
                batteryStack.enchant(enchantment, lvl);
                mediumStack.enchant(enchantment, lvl);
                emitterStack.enchant(enchantment, lvl);
                casingStack.enchant(enchantment, lvl);

            } else {
                casingStack.enchant(enchantment, lvl);
            }
        });

        // Process colors
        LaserBladeVisual laserBladeVisual = laserBlade.getVisual();
        LaserBladeVisual.Writer mediumVisual = LaserBladeVisual.Writer.of(mediumStack);
        LaserBladeVisual.Writer emitterVisual = LaserBladeVisual.Writer.of(emitterStack);
        LaserBladeVisual.Writer casingVisual = LaserBladeVisual.Writer.of(casingStack);

        mediumVisual
                .writeOuterColor(laserBladeVisual.getOuterColor())
                .writeIsOuterSubColor(laserBladeVisual.isOuterSubColor());
        emitterVisual
                .writeInnerColor(laserBladeVisual.getInnerColor())
                .writeIsInnerSubColor(laserBladeVisual.isInnerSubColor());
        casingVisual
                .writeGripColor(laserBladeVisual.getGripColor())
                .writeModelType(laserBladeVisual.getModelType());

        // Process display name
        if (itemStack.hasCustomHoverName()) {
            casingStack.setHoverName(itemStack.getHoverName());
        }

        // Drop items
        dropItem(batteryStack, player);
        dropItem(mediumStack, player);
        dropItem(emitterStack, player);
        dropItem(casingStack, player);
    }

    private void dropItem(ItemStack itemStack, Player player) {
        ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY() + 0.5, player.getZ(), itemStack);
        player.level().addFreshEntity(itemEntity);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(stack, level, tooltip, flag, upgradeType);
    }
}
