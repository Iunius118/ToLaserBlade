package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladePerformance;
import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public interface LaserBladeItemBase {
    String KEY_TOOLTIP_FIREPROOF = "upgrade.tolaserblade.fireproof";
    String KEY_TOOLTIP_ATTACK_DAMAGE = "upgrade.tolaserblade.attackDamage";
    String KEY_TOOLTIP_ATTACK_SPEED = "upgrade.tolaserblade.attackSpeed";
    String KEY_TOOLTIP_REMOVE = "tooltip.tolaserblade.remove";

    float MOD_CRITICAL_BONUS_VS_WITHER = 0.5F;

    int MAX_USES = 32000;

    /* Laser Blade properties */

    static Item.Properties setFireproof(Item.Properties properties, boolean isFireproof) {
        return isFireproof ? properties.isImmuneToFire() : properties;
    }

    default boolean canUpgrade(Upgrade.Type type) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    default void addLaserBladeInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn, Upgrade.Type type) {
        boolean isFireproof = stack.getItem().isImmuneToFire();

        if (isFireproof) {
            tooltip.add(new TranslationTextComponent(KEY_TOOLTIP_FIREPROOF).mergeStyle(TextFormatting.GOLD));
        }

        LaserBladePerformance performance = LaserBlade.performanceOf(stack);
        LaserBladePerformance.AttackPerformance attack = performance.getAttackPerformance();

        if (type == Upgrade.Type.REPAIR || type == Upgrade.Type.MEDIUM) {
            float atk = attack.damage;

            if (atk <= -0.005F || atk >= 0.005) {
                tooltip.add(getUpgradeTextComponent(KEY_TOOLTIP_ATTACK_DAMAGE, atk));
            }
        }

        if (type == Upgrade.Type.REPAIR || type == Upgrade.Type.BATTERY) {
            float spd = attack.speed;

            if (spd <= -0.005F || spd >= 0.005) {
                tooltip.add(getUpgradeTextComponent(KEY_TOOLTIP_ATTACK_SPEED, spd));
            }
        }
    }

    default ITextComponent getUpgradeTextComponent(String key, float value) {
        return new TranslationTextComponent(key, (value < 0 ? "" : "+") + ItemStack.DECIMALFORMAT.format(value)).mergeStyle(TextFormatting.DARK_GREEN);
    }
}
