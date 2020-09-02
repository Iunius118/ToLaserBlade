package com.github.iunius118.tolaserblade.laserblade.upgrade;

import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.crafting.Ingredient;

import java.util.HashMap;
import java.util.Map;

public class UpgradeManager {
    private static final Map<Ingredient, Upgrade> upgrades = new HashMap<>();
    static {
        upgrades.put(Ingredient.fromTag(ModItemTags.EFFICIENCY_UPGRADE), EnchantmentUpgrade.of(Enchantments.EFFICIENCY));
        upgrades.put(Ingredient.fromTag(ModItemTags.LIGHT_ELEMENT_UPGRADE), EnchantmentUpgrade.of(ModEnchantments.LIGHT_ELEMENT));
        upgrades.put(Ingredient.fromTag(ModItemTags.FIRE_ASPECT_UPGRADE), EnchantmentUpgrade.of(Enchantments.FIRE_ASPECT));
        upgrades.put(Ingredient.fromTag(ModItemTags.SWEEPING_EDGE_UPGRADE), EnchantmentUpgrade.of(Enchantments.SWEEPING));
        upgrades.put(Ingredient.fromTag(ModItemTags.LOOTING_UPGRADE), EnchantmentUpgrade.of(Enchantments.LOOTING));
        upgrades.put(Ingredient.fromTag(ModItemTags.MENDING_UPGRADE), EnchantmentUpgrade.of(Enchantments.MENDING));

        upgrades.put(Ingredient.fromTag(ModItemTags.EFFICIENCY_REMOVER), new RemoveEfficiencyUpgrade());

        upgrades.put(Ingredient.fromTag(ModItemTags.ATTACK_DAMAGE_UPGRADE), new DamageUpgrade());
        upgrades.put(Ingredient.fromTag(ModItemTags.ATTACK_SPEED_UPGRADE), new SpeedUpgrade());
    }
}
