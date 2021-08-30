package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradeManager {
    private static final Map<ResourceLocation, Upgrade> upgrades = new HashMap<>();
    static {
        // Add new upgrade:
        // 1. Add upgrade-ID to UpgradeID
        // 2. Add item-tag to ModItemTags and TLBItemTagsProvider
        // 3. Add upgrade here
        registerEnchantment(UpgradeID.EFFICIENCY_UPGRADE, ModItemTags.EFFICIENCY_UPGRADE, Enchantments.BLOCK_EFFICIENCY);
        registerEnchantment(UpgradeID.LIGHT_ELEMENT_UPGRADE, ModItemTags.LIGHT_ELEMENT_UPGRADE, ModEnchantments.LIGHT_ELEMENT);
        registerEnchantment(UpgradeID.FIRE_ASPECT_UPGRADE, ModItemTags.FIRE_ASPECT_UPGRADE, Enchantments.FIRE_ASPECT);
        registerEnchantment(UpgradeID.SWEEPING_EDGE_UPGRADE, ModItemTags.SWEEPING_EDGE_UPGRADE, Enchantments.SWEEPING_EDGE);
        registerEnchantment(UpgradeID.SILK_TOUCH_UPGRADE, ModItemTags.SILK_TOUCH_UPGRADE, Enchantments.SILK_TOUCH);
        registerEnchantment(UpgradeID.LOOTING_UPGRADE, ModItemTags.LOOTING_UPGRADE, Enchantments.MOB_LOOTING);
        registerEnchantment(UpgradeID.MENDING_UPGRADE, ModItemTags.MENDING_UPGRADE, Enchantments.MENDING);

        register(UpgradeID.EFFICIENCY_REMOVER, ModItemTags.EFFICIENCY_REMOVER, RemoveEfficiencyUpgrade.class);

        register(UpgradeID.ATTACK_DAMAGE_UPGRADE, ModItemTags.ATTACK_DAMAGE_UPGRADE, DamageUpgrade.class);
        register(UpgradeID.ATTACK_SPEED_UPGRADE, ModItemTags.ATTACK_SPEED_UPGRADE, SpeedUpgrade.class);
    }

    private static void register(UpgradeID id, Tag.Named<Item> tag, Class<? extends Upgrade> upgradeClass) {
        Upgrade upgrade = Upgrade.of(upgradeClass, () -> Ingredient.of(tag), id.getShortName());

        if (upgrade != null) {
            upgrades.put(id.getID(), upgrade);
        }
    }

    private static void registerEnchantment(UpgradeID id, Tag.Named<Item> tag, Enchantment enchantment) {
        Upgrade upgrade = EnchantmentUpgrade.of(() -> Ingredient.of(tag), enchantment, id.getShortName());
        upgrades.put(id.getID(), upgrade);
    }

    public static Map<ResourceLocation, Upgrade> getUpgrades() {
        return upgrades;
    }

    public static Upgrade get(ResourceLocation key) {
        Upgrade upgrade = upgrades.get(key);

        if (upgrade == null) {
            ToLaserBlade.LOGGER.warn("[ToLaserBlade] Upgrade {} was not found in UpgradeManager", key.toString());
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
