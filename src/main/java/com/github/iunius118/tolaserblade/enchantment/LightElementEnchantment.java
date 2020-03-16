package com.github.iunius118.tolaserblade.enchantment;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LightElementEnchantment extends DamageEnchantment {
    public static final ResourceLocation ID = new ResourceLocation(ToLaserBlade.MOD_ID, "light_element");
    private static final int MAX_LEVEL = 10;

    public LightElementEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, 1, EquipmentSlotType.MAINHAND);
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public float calcDamageByCreature(int level, CreatureAttribute creatureType) {
        if (creatureType == CreatureAttribute.UNDEAD || creatureType == CreatureAttribute.ILLAGER) {
            return (float)level * 2.5F;
        }

        return 0.0F;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof LaserBladeItemBase;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {

    }
}
