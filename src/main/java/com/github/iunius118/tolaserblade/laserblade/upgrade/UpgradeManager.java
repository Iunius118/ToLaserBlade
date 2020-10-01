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
        // Add upgrade ID to UpgradeID, add item tag to ModItemTags and TLBItemTagsProvider, and add upgrade here
        registerEnchantment(UpgradeID.EFFICIENCY_UPGRADE, ModItemTags.EFFICIENCY_UPGRADE, Enchantments.EFFICIENCY);
        registerEnchantment(UpgradeID.LIGHT_ELEMENT_UPGRADE, ModItemTags.LIGHT_ELEMENT_UPGRADE, ModEnchantments.LIGHT_ELEMENT);
        registerEnchantment(UpgradeID.FIRE_ASPECT_UPGRADE, ModItemTags.FIRE_ASPECT_UPGRADE, Enchantments.FIRE_ASPECT);
        registerEnchantment(UpgradeID.SWEEPING_EDGE_UPGRADE, ModItemTags.SWEEPING_EDGE_UPGRADE, Enchantments.SWEEPING);
        registerEnchantment(UpgradeID.SILK_TOUCH_UPGRADE, ModItemTags.SILK_TOUCH_UPGRADE, Enchantments.SILK_TOUCH);
        registerEnchantment(UpgradeID.LOOTING_UPGRADE, ModItemTags.LOOTING_UPGRADE, Enchantments.LOOTING);
        registerEnchantment(UpgradeID.MENDING_UPGRADE, ModItemTags.MENDING_UPGRADE, Enchantments.MENDING);

        register(UpgradeID.EFFICIENCY_REMOVER, ModItemTags.EFFICIENCY_REMOVER, RemoveEfficiencyUpgrade.class);

        register(UpgradeID.ATTACK_DAMAGE_UPGRADE, ModItemTags.ATTACK_DAMAGE_UPGRADE, DamageUpgrade.class);
        register(UpgradeID.ATTACK_SPEED_UPGRADE, ModItemTags.ATTACK_SPEED_UPGRADE, SpeedUpgrade.class);
    }

    private static void register(UpgradeID id, ITag.INamedTag<Item> tag, Class<? extends Upgrade> upgradeClass) {
        Upgrade upgrade = Upgrade.of(upgradeClass, () -> Ingredient.fromTag(tag), id.getShortName());

        if (upgrade != null) {
            upgrades.put(id.getID(), upgrade);
        }
    }

    private static void registerEnchantment(UpgradeID id, ITag.INamedTag<Item> tag, Enchantment enchantment) {
        Upgrade upgrade = EnchantmentUpgrade.of(() -> Ingredient.fromTag(tag), enchantment, id.getShortName());
        upgrades.put(id.getID(), upgrade);
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

    public static Upgrade get(UpgradeID id) {
        return get(id.getID());
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
