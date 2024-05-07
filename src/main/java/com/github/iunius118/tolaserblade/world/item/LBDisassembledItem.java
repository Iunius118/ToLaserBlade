package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class LBDisassembledItem extends Item implements LaserBladeItemBase {
    public static Properties properties = new Properties();
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
        ItemStack casingStack = new ItemStack(itemStack.has(DataComponents.FIRE_RESISTANT) ? ModItems.LB_CASING_FP : ModItems.LB_CASING);
        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(itemStack);


        // Process attacks
        LaserBlade.setSpeed(batteryStack, LaserBlade.getSpeed(itemStack));
        LaserBlade.setAttack(mediumStack, LaserBlade.getAttack(itemStack));

        // Process enchantments
        enchantments.keySet().forEach(e -> {
            var enchantment = e.get();
            int lvl = enchantments.getLevel(enchantment);

            if (enchantment == Enchantments.EFFICIENCY) {
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
        var appearance = LaserBladeAppearance.of(itemStack);
        var mediumAppearance = LaserBladeAppearance.of();
        var emitterAppearance = LaserBladeAppearance.of();
        var casingAppearance = LaserBladeAppearance.of();

        mediumAppearance
                .setOuterColor(appearance.getOuterColor())
                .setOuterSubColor(appearance.isOuterSubColor())
                .writeTo(mediumStack);
        emitterAppearance
                .setInnerColor(appearance.getInnerColor())
                .setInnerSubColor(appearance.isInnerSubColor())
                .writeTo(emitterStack);
        casingAppearance
                .setGripColor(appearance.getGripColor())
                .setType(appearance.getType())
                .writeTo(casingStack);

        // Process display name
        if (itemStack.has(DataComponents.CUSTOM_NAME)) {
            casingStack.set(DataComponents.CUSTOM_NAME, itemStack.getDisplayName());
        }

        player.addItem(batteryStack);
        player.addItem(mediumStack);
        player.addItem(emitterStack);
        player.addItem(casingStack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, tooltipContext, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, tooltipContext, tooltip, flag, upgradeType);
    }
}
