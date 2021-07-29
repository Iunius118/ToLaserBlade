package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladePerformance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class LBDisassembledItem extends Item implements LaserBladeItemBase {
    public static Properties properties = (new Properties()).setNoRepair().tab(ModMainItemGroup.ITEM_GROUP);
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
        LaserBlade battery = LaserBlade.of(batteryStack);
        LaserBlade medium = LaserBlade.of(mediumStack);
        LaserBlade emitter = LaserBlade.of(emitterStack);
        LaserBlade casing = LaserBlade.of(casingStack);

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);

        // Process attacks
        LaserBladePerformance.AttackPerformance laserBladeAttack = laserBlade.getAttackPerformance();
        LaserBladePerformance.AttackPerformance batteryAttack = battery.getAttackPerformance();
        LaserBladePerformance.AttackPerformance mediumAttack = medium.getAttackPerformance();

        batteryAttack.changeSpeedSafely(laserBladeAttack.speed);
        mediumAttack.changeDamageSafely(laserBladeAttack.damage);

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
        LaserBladeVisual mediumVisual = medium.getVisual();
        LaserBladeVisual emitterVisual = emitter.getVisual();
        LaserBladeVisual casingVisual = casing.getVisual();

        LaserBladeVisual.PartColor laserBladeOuterColor = laserBladeVisual.getOuterColor();
        LaserBladeVisual.PartColor laserBladeInnerColor = laserBladeVisual.getInnerColor();
        LaserBladeVisual.PartColor laserBladeGripColor = laserBladeVisual.getGripColor();

        LaserBladeVisual.PartColor mediumOuterColor = mediumVisual.getOuterColor();
        LaserBladeVisual.PartColor emitterInnerColor = emitterVisual.getInnerColor();
        LaserBladeVisual.PartColor casingGripColor = casingVisual.getGripColor();

        mediumOuterColor.color = laserBladeOuterColor.color;
        mediumOuterColor.isSubtractColor = laserBladeOuterColor.isSubtractColor;

        emitterInnerColor.color = laserBladeInnerColor.color;
        emitterInnerColor.isSubtractColor = laserBladeInnerColor.isSubtractColor;

        casingGripColor.color = laserBladeGripColor.color;
        casingVisual.setModelType(laserBladeVisual.getModelType());

        // Process display name
        if (itemStack.hasCustomHoverName()) {
            casingStack.setHoverName(itemStack.getHoverName());
        }

        // Write to stack
        battery.write(batteryStack);
        medium.write(mediumStack);
        emitter.write(emitterStack);
        casing.write(casingStack);

        // Drop items
        dropItem(batteryStack, player);
        dropItem(mediumStack, player);
        dropItem(emitterStack, player);
        dropItem(casingStack, player);
    }

    private void dropItem(ItemStack itemStack, Player player) {
        ItemEntity itemEntity = new ItemEntity(player.level, player.getX(), player.getY() + 0.5, player.getZ(), itemStack);
        player.level.addFreshEntity(itemEntity);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        super.fillItemCategory(group, items);
        if (group != ModMainItemGroup.ITEM_GROUP) return;

        if (isFireResistant()) {
            items.add(LaserBladeItemStack.DISASSEMBLED_FULL_MOD_FP.getCopy());
        } else {
            items.add(LaserBladeItemStack.DISASSEMBLED_FULL_MOD.getCopy());
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(stack, level, tooltip, flag, upgradeType);
    }
}
