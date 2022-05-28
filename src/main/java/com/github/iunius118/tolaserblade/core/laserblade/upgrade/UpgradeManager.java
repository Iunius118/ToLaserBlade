package com.github.iunius118.tolaserblade.core.laserblade.upgrade;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import com.github.iunius118.tolaserblade.world.item.enchantment.ModEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
        // 2. Add item-tag to ModItemTags and TLBItemTagsProvider(Forge)
        // 3. Add upgrader class
        // 4. Add upgrade here
        registerEnchantment(UpgradeID.EFFICIENCY_UPGRADE, ModItemTags.EFFICIENCY_UPGRADE, Enchantments.BLOCK_EFFICIENCY);
        registerEnchantment(UpgradeID.LIGHT_ELEMENT_UPGRADE, ModItemTags.LIGHT_ELEMENT_UPGRADE, ModEnchantments.LIGHT_ELEMENT);
        registerEnchantment(UpgradeID.FIRE_ASPECT_UPGRADE, ModItemTags.FIRE_ASPECT_UPGRADE, Enchantments.FIRE_ASPECT);
        registerEnchantment(UpgradeID.SWEEPING_EDGE_UPGRADE, ModItemTags.SWEEPING_EDGE_UPGRADE, Enchantments.SWEEPING_EDGE);
        registerEnchantment(UpgradeID.SILK_TOUCH_UPGRADE, ModItemTags.SILK_TOUCH_UPGRADE, Enchantments.SILK_TOUCH);
        registerEnchantment(UpgradeID.LOOTING_UPGRADE, ModItemTags.LOOTING_UPGRADE, Enchantments.MOB_LOOTING);
        registerEnchantment(UpgradeID.MENDING_UPGRADE, ModItemTags.MENDING_UPGRADE, Enchantments.MENDING);

        register(UpgradeID.EFFICIENCY_REMOVER, ModItemTags.EFFICIENCY_REMOVER, new RemoveEfficiencyUpgrader());

        register(UpgradeID.ATTACK_DAMAGE_UPGRADE, ModItemTags.ATTACK_DAMAGE_UPGRADE, new DamageUpgrader());
        register(UpgradeID.ATTACK_SPEED_UPGRADE, ModItemTags.ATTACK_SPEED_UPGRADE, new SpeedUpgrader());
    }

    private static void register(UpgradeID id, TagKey<Item> ingredientItemTag, Upgrader upgrader) {
        var upgrade = new Upgrade(upgrader, ingredientItemTag, id.getShortName());
        upgrades.put(id.getID(), upgrade);
    }

    private static void registerEnchantment(UpgradeID id, TagKey<Item> ingredientItemTag, Enchantment enchantment) {
        var upgrade = new Upgrade(EnchantmentUpgrader.of(enchantment), ingredientItemTag, id.getShortName());
        upgrades.put(id.getID(), upgrade);
    }

    public static Map<ResourceLocation, Upgrade> getUpgrades() {
        return upgrades;
    }

    public static Upgrade getUpgrade(ResourceLocation key) {
        var upgrade = upgrades.get(key);

        if (upgrade == null) {
            ToLaserBlade.LOGGER.warn("[ToLaserBlade] Upgrade {} was not found in UpgradeManager", key.toString());
            upgrade = Upgrade.NONE;
        }

        return upgrade;
    }

    public static Upgrade getUpgrade(UpgradeID id) {
        return getUpgrade(id.getID());
    }

    public static List<Upgrade> getUpgradeList(ItemStack additionalItem) {
        List<Upgrade> list = new ArrayList<>();
        upgrades.forEach((key, upgrade)->{
            var ingredient = upgrade.getIngredient();
            if (ingredient.test(additionalItem)) list.add(upgrade);
        });
        return list;
    }
}
