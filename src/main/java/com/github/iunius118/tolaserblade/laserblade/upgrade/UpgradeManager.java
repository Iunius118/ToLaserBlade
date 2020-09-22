package com.github.iunius118.tolaserblade.laserblade.upgrade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
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
        registerEnchantment(ModItemTags.EFFICIENCY_UPGRADE, Enchantments.EFFICIENCY, "efc");
        registerEnchantment(ModItemTags.LIGHT_ELEMENT_UPGRADE, ModEnchantments.LIGHT_ELEMENT, "lte");
        registerEnchantment(ModItemTags.FIRE_ASPECT_UPGRADE, Enchantments.FIRE_ASPECT, "fra");
        registerEnchantment(ModItemTags.SWEEPING_EDGE_UPGRADE, Enchantments.SWEEPING, "swp");
        registerEnchantment(ModItemTags.LOOTING_UPGRADE, Enchantments.LOOTING, "ltn");
        registerEnchantment(ModItemTags.MENDING_UPGRADE, Enchantments.MENDING, "mnd");

        register(ModItemTags.EFFICIENCY_REMOVER, RemoveEfficiencyUpgrade.class, "efr");

        register(ModItemTags.ATTACK_DAMAGE_UPGRADE, DamageUpgrade.class, "adm");
        register(ModItemTags.ATTACK_SPEED_UPGRADE, SpeedUpgrade.class, "asp");
    }

    private static void register(ITag.INamedTag<Item> tag, Class<? extends Upgrade> upgradeClass, String shortName) {
        ResourceLocation key = tag.getName();
        Ingredient ingredient = Ingredient.fromTag(tag);
        Upgrade upgrade = Upgrade.of(upgradeClass, ingredient, shortName);

        if (upgrade != null) {
            upgrades.put(key, upgrade);
        }
    }

    private static void registerEnchantment(ITag.INamedTag<Item> tag, Enchantment enchantment, String shortName) {
        ResourceLocation key = tag.getName();
        Ingredient ingredient = Ingredient.fromTag(tag);
        Upgrade upgrade = EnchantmentUpgrade.of(ingredient, enchantment, shortName);
        upgrades.put(key, upgrade);
    }

    public static Map<ResourceLocation, Upgrade> getUpgrades() {
        return upgrades;
    }

    public static Upgrade get(ResourceLocation key) {
        Upgrade upgrade = upgrades.get(key);

        if (upgrade == null) {
            ToLaserBlade.LOGGER.warn("Upgrade {} was not found in UpgradeManager", key.toString());
            upgrade = Upgrade.NONE;
        }

        return upgrade;
    }

    public static List<Upgrade> get(ItemStack additionalItem) {
        List<Upgrade> list = new ArrayList<>();
        upgrades.forEach((key, upgrade)->{
            Ingredient ingredient = upgrade.getIngredient();
            if (ingredient.test(additionalItem)) list.add(upgrade);
        });
        return list;
    }
}
