package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class LBDisassembledItem extends Item implements LaserBladeItemBase {
    public static Properties properties = (new Properties()).setNoRepair().group(ModMainItemGroup.ITEM_GROUP);
    public final Upgrade.Type upgradeType = Upgrade.Type.REPAIR;

    public LBDisassembledItem(boolean isFireproof) {
        super(LaserBladeItemBase.setFireproof(properties, isFireproof));
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote()) {
            disassembleLaserBlade(worldIn, playerIn, itemStack);
            itemStack.shrink(1);
            return ActionResult.resultSuccess(itemStack);
        }

        return ActionResult.resultSuccess(itemStack);
    }

    private void disassembleLaserBlade(World worldIn, PlayerEntity playerIn, ItemStack itemStack) {
        ItemStack battery = new ItemStack(ModItems.LB_BATTERY);
        ItemStack medium = new ItemStack(ModItems.LB_MEDIUM);
        ItemStack emitter = new ItemStack(ModItems.LB_EMITTER);
        ItemStack casing = new ItemStack(itemStack.getItem().isBurnable() ? ModItems.LB_CASING_FP : ModItems.LB_CASING);    // TODO: isBurnable = isFireproof?
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);

        // Process attacks
        setLaserBladeSPD(battery, getLaserBladeSPD(itemStack));
        setLaserBladeATK(medium, getLaserBladeATK(itemStack));

        // Process enchantments
        enchantments.forEach((enchantment, lvl) -> {
            if (enchantment == Enchantments.EFFICIENCY) {
                battery.addEnchantment(enchantment, lvl);

            } else if (enchantment instanceof DamageEnchantment) {
                medium.addEnchantment(enchantment, lvl);

            } else if (enchantment == Enchantments.FIRE_ASPECT ||
                    enchantment == Enchantments.SWEEPING ||
                    enchantment == Enchantments.KNOCKBACK ) {
                emitter.addEnchantment(enchantment, lvl);

            } else if (enchantment == Enchantments.VANISHING_CURSE) {
                battery.addEnchantment(enchantment, lvl);
                medium.addEnchantment(enchantment, lvl);
                emitter.addEnchantment(enchantment, lvl);
                casing.addEnchantment(enchantment, lvl);

            } else {
                casing.addEnchantment(enchantment, lvl);
            }
        });

        // Process colors
        Pair<Integer, Boolean> outerColor = getBladeOuterColor(itemStack);
        setBladeOuterColor(medium, outerColor.getLeft());
        setBladeOuterSubColorFlag(medium, outerColor.getRight());

        Pair<Integer, Boolean> innerColor = getBladeInnerColor(itemStack);
        setBladeInnerColor(emitter, innerColor.getLeft());
        setBladeInnerSubColorFlag(emitter, innerColor.getRight());

        setGripColor(casing, getGripColor(itemStack));

        // Process display name
        if (itemStack.hasDisplayName()) {
            casing.setDisplayName(itemStack.getDisplayName());
        }

        // Drop items
        dropItem(battery, playerIn);
        dropItem(medium, playerIn);
        dropItem(emitter, playerIn);
        dropItem(casing, playerIn);

        if (!playerIn.abilities.isCreativeMode) {
            dropItem(new ItemStack(ModItems.LB_BLUEPRINT), playerIn);
        }
    }

    private void dropItem(ItemStack itemStack, PlayerEntity playerIn) {
        ItemEntity itemEntity = new ItemEntity(playerIn.world, playerIn.getPosX(), playerIn.getPosY() + 0.5, playerIn.getPosZ(), itemStack);
        playerIn.world.addEntity(itemEntity);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        addLaserBladeInformation(stack, worldIn, tooltip, flagIn, upgradeType);
    }
}
