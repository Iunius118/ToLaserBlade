package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladePerformance;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeStack;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeVisual;
import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
        ItemStack batteryStack = new ItemStack(ModItems.LB_BATTERY);
        ItemStack mediumStack = new ItemStack(ModItems.LB_MEDIUM);
        ItemStack emitterStack = new ItemStack(ModItems.LB_EMITTER);
        ItemStack casingStack = new ItemStack(itemStack.getItem().isBurnable() ? ModItems.LB_CASING_FP : ModItems.LB_CASING);    // TODO: isBurnable = isFireproof?

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
            if (enchantment == Enchantments.EFFICIENCY) {
                batteryStack.addEnchantment(enchantment, lvl);

            } else if (enchantment instanceof DamageEnchantment) {
                mediumStack.addEnchantment(enchantment, lvl);

            } else if (enchantment == Enchantments.FIRE_ASPECT ||
                    enchantment == Enchantments.SWEEPING ||
                    enchantment == Enchantments.KNOCKBACK ) {
                emitterStack.addEnchantment(enchantment, lvl);

            } else if (enchantment == Enchantments.VANISHING_CURSE) {
                batteryStack.addEnchantment(enchantment, lvl);
                mediumStack.addEnchantment(enchantment, lvl);
                emitterStack.addEnchantment(enchantment, lvl);
                casingStack.addEnchantment(enchantment, lvl);

            } else {
                casingStack.addEnchantment(enchantment, lvl);
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

        // Process display name
        if (itemStack.hasDisplayName()) {
            casingStack.setDisplayName(itemStack.getDisplayName());
        }

        // Write to stack
        battery.write(batteryStack);
        medium.write(mediumStack);
        emitter.write(emitterStack);
        casing.write(casingStack);

        // Drop items
        dropItem(batteryStack, playerIn);
        dropItem(mediumStack, playerIn);
        dropItem(emitterStack, playerIn);
        dropItem(casingStack, playerIn);
    }

    private void dropItem(ItemStack itemStack, PlayerEntity playerIn) {
        ItemEntity itemEntity = new ItemEntity(playerIn.world, playerIn.getPosX(), playerIn.getPosY() + 0.5, playerIn.getPosZ(), itemStack);
        playerIn.world.addEntity(itemEntity);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        super.fillItemGroup(group, items);
        if (group != ModMainItemGroup.ITEM_GROUP) return;

        if (isBurnable()) { // TODO: isBurnable = isNotBurnable?
            items.add(LaserBladeStack.DISASSEMBLED_FULL_MOD_FP.getCopy());
        } else {
            items.add(LaserBladeStack.DISASSEMBLED_FULL_MOD.getCopy());
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        addLaserBladeInformation(stack, worldIn, tooltip, flagIn, upgradeType);
    }
}
