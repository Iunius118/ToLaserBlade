package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

public class LBDisassembledItem extends Item implements LaserBladeItemBase {
    public final Upgrade.Type upgradeType = Upgrade.Type.REPAIR;

    public LBDisassembledItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return false;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            disassembleLaserBlade(level, player, itemStack);
            itemStack.shrink(1);
        }

        return InteractionResult.SUCCESS;
    }

    private void disassembleLaserBlade(Level level, Player player, ItemStack itemStack) {
        ItemStack batteryStack = new ItemStack(ModItems.LB_BATTERY);
        ItemStack mediumStack = new ItemStack(ModItems.LB_MEDIUM);
        ItemStack emitterStack = new ItemStack(ModItems.LB_EMITTER);
        ItemStack casingStack = new ItemStack(LaserBladeItemUtil.isFireResistant(itemStack) ? ModItems.LB_CASING_FP : ModItems.LB_CASING);
        ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(itemStack);

        // Process attacks
        LaserBlade.setSpeed(batteryStack, LaserBlade.getSpeed(itemStack));
        LaserBlade.setAttack(mediumStack, LaserBlade.getAttack(itemStack));

        // Process enchantments
        enchantments.keySet().forEach(e -> {
            if (e.unwrapKey().isEmpty()) {
                return;
            }

            int lvl = enchantments.getLevel(e);
            ResourceKey<Enchantment> key = e.unwrapKey().get();

            if (equals(key, Enchantments.EFFICIENCY)) {
                batteryStack.enchant(e, lvl);
            } else if (e.is(EnchantmentTags.DAMAGE_EXCLUSIVE) ||
                    equals(key, Enchantments.KNOCKBACK)) {
                mediumStack.enchant(e, lvl);
            } else if (equals(key, Enchantments.FIRE_ASPECT) ||
                    equals(key, Enchantments.SWEEPING_EDGE) ||
                    equals(key, Enchantments.SILK_TOUCH)) {
                emitterStack.enchant(e, lvl);
            } else if (equals(key, Enchantments.VANISHING_CURSE)) {
                batteryStack.enchant(e, lvl);
                mediumStack.enchant(e, lvl);
                emitterStack.enchant(e, lvl);
                casingStack.enchant(e, lvl);
            } else {
                casingStack.enchant(e, lvl);
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
                .setTo(mediumStack);
        emitterAppearance
                .setInnerColor(appearance.getInnerColor())
                .setInnerSubColor(appearance.isInnerSubColor())
                .setTo(emitterStack);
        casingAppearance
                .setGripColor(appearance.getGripColor())
                .setType(appearance.getType())
                .setTo(casingStack);

        // Process display name
        if (itemStack.has(DataComponents.CUSTOM_NAME)) {
            casingStack.set(DataComponents.CUSTOM_NAME, itemStack.getDisplayName());
        }

        player.addItem(batteryStack);
        player.addItem(mediumStack);
        player.addItem(emitterStack);
        player.addItem(casingStack);
    }

    private static boolean equals(ResourceKey<?> e1, ResourceKey<?> e2) {
        return e1.location().equals(e2.location());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, tooltipContext, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, tooltipContext, tooltip, flag, upgradeType);
    }
}
