package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladePerformance;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeTextKey;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeVisual;
import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.util.ModSoundEvents;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class LaserBladeItemUtil {
    private LaserBladeItemUtil() {

    }

    public static float getDestroySpeed(ItemStack itemStack, IItemTier tier) {
        float rate = (float) EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, itemStack) / 5.0F;
        return tier.getSpeed() * MathHelper.clamp(rate, 0.0F, 1.0F);
    }

    public static void playSwingSound(World world, LivingEntity entity, boolean isFireResistant) {
        SoundEvent soundEvent = isFireResistant ? ModSoundEvents.ITEM_LASER_BLADE_FP_SWING : ModSoundEvents.ITEM_LASER_BLADE_SWING;
        Vector3d pos = entity.position().add(0, entity.getEyeHeight(), 0).add(entity.getLookAngle());
        world.playSound(null, pos.x, pos.y, pos.z, soundEvent, SoundCategory.PLAYERS, 0.5F, 1.0F);
    }

    public static void addItemStacks(NonNullList<ItemStack> items, boolean isFireResistant) {
        if (isFireResistant) {
            items.add(LaserBladeItemStack.UPGRADED_FP.getCopy());
            items.add(LaserBladeItemStack.DAMAGED_FP.getCopy());
            items.add(LaserBladeItemStack.FULL_MOD_FP.getCopy());
        } else {
            items.add(LaserBladeItemStack.MODEL_TYPE_526.getCopy());
            items.add(LaserBladeItemStack.LIGHT_ELEMENT_1.getCopy());
            items.add(LaserBladeItemStack.LIGHT_ELEMENT_2.getCopy());
            items.add(LaserBladeItemStack.GIFT.getCopy());
            items.add(LaserBladeItemStack.UPGRADED.getCopy());
            items.add(LaserBladeItemStack.DAMAGED.getCopy());
            items.add(LaserBladeItemStack.FULL_MOD.getCopy());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void addLaserBladeInformation(ItemStack itemStack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn, Upgrade.Type upgradeType) {
        LaserBlade laserBlade = LaserBlade.of(itemStack);
        boolean isFireproof = laserBlade.isFireproof();

        if (isFireproof) {
            tooltip.add(LaserBladeTextKey.KEY_TOOLTIP_FIREPROOF.translate().withStyle(TextFormatting.GOLD));
        }

        switch (upgradeType) {
            case BATTERY:
                addAttackSpeed(tooltip, laserBlade.getAttackPerformance().speed);
                break;
            case MEDIUM:
                addAttackDamage(tooltip, laserBlade.getAttackPerformance().damage);
                break;
            case EMITTER:
                break;
            case CASING:
            case OTHER:
                addModelType(tooltip, laserBlade);
                break;
            case REPAIR:
                addModelType(tooltip, laserBlade);

                LaserBladePerformance.AttackPerformance attack = laserBlade.getAttackPerformance();
                addAttackDamage(tooltip, attack.damage);
                addAttackSpeed(tooltip, attack.speed);
                break;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void addBrandNewText(List<ITextComponent> tooltip) {
        tooltip.add(new TranslationTextComponent("tooltip.tolaserblade.brandNew1").withStyle(TextFormatting.YELLOW));
        tooltip.add(new TranslationTextComponent("tooltip.tolaserblade.brandNew2").withStyle(TextFormatting.YELLOW));
        tooltip.add(new TranslationTextComponent("tooltip.tolaserblade.brandNew3").withStyle(TextFormatting.YELLOW));
    }

    @OnlyIn(Dist.CLIENT)
    private static void addModelType(List<ITextComponent> tooltip, LaserBlade laserBlade) {
            LaserBladeVisual visual = laserBlade.getVisual();
            int modelType = visual.getModelType();

            if (modelType >= 0) {
                tooltip.add(LaserBladeTextKey.KEY_TOOLTIP_MODEL.translate(modelType).withStyle(TextFormatting.DARK_GRAY));
            }
    }

    @OnlyIn(Dist.CLIENT)
    private static void addAttackDamage(List<ITextComponent> tooltip, float atk) {
        if (atk <= -0.005F || atk >= 0.005) {
            tooltip.add(getUpgradeTextComponent(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_DAMAGE.getKey(), atk));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void addAttackSpeed(List<ITextComponent> tooltip, float spd) {
        if (spd <= -0.005F || spd >= 0.005) {
            tooltip.add(getUpgradeTextComponent(LaserBladeTextKey.KEY_TOOLTIP_ATTACK_SPEED.getKey(), spd));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static ITextComponent getUpgradeTextComponent(String key, float value) {
        return new TranslationTextComponent(key, (value < 0 ? "" : "+") + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(value)).withStyle(TextFormatting.DARK_GREEN);
    }
}
