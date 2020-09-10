package com.github.iunius118.tolaserblade.laserblade.upgrade;

import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradeManager {
    private static final Map<ResourceLocation, Upgrade> upgrades = new HashMap<>();
    static {
        registerEnchantment(ModItemTags.EFFICIENCY_UPGRADE, Enchantments.EFFICIENCY);
        registerEnchantment(ModItemTags.LIGHT_ELEMENT_UPGRADE, ModEnchantments.LIGHT_ELEMENT);
        registerEnchantment(ModItemTags.FIRE_ASPECT_UPGRADE, Enchantments.FIRE_ASPECT);
        registerEnchantment(ModItemTags.SWEEPING_EDGE_UPGRADE, Enchantments.SWEEPING);
        registerEnchantment(ModItemTags.LOOTING_UPGRADE, Enchantments.LOOTING);
        registerEnchantment(ModItemTags.MENDING_UPGRADE, Enchantments.MENDING);

        register(ModItemTags.EFFICIENCY_REMOVER, RemoveEfficiencyUpgrade.class);

        register(ModItemTags.ATTACK_DAMAGE_UPGRADE, DamageUpgrade.class);
        register(ModItemTags.ATTACK_SPEED_UPGRADE, SpeedUpgrade.class);
    }

    private static void register(ITag.INamedTag<Item> tag, Class<? extends Upgrade> upgradeClass) {
        ResourceLocation key = tag.getName();
        Ingredient ingredient = Ingredient.fromTag(tag);
        Upgrade upgrade = Upgrade.of(upgradeClass, ingredient);

        if (upgrade != null) {
            upgrades.put(key, upgrade);
        }
    }

    private static void registerEnchantment(ITag.INamedTag<Item> tag, Enchantment enchantment) {
        ResourceLocation key = tag.getName();
        Ingredient ingredient = Ingredient.fromTag(tag);
        Upgrade upgrade = EnchantmentUpgrade.of(ingredient, enchantment);
        upgrades.put(key, upgrade);
    }

    public static Upgrade get(ResourceLocation key) {
        return upgrades.get(key);
    }

    public static List<Upgrade> get(ItemStack item) {
        List<Upgrade> list = new ArrayList<>();
        upgrades.forEach((key, upgrade)->{
            Ingredient ingredient = upgrade.getIngredient();
            if (ingredient.test(item)) list.add(upgrade);
        });
        return list;
    }
}
