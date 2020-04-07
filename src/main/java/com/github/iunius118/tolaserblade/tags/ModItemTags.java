package com.github.iunius118.tolaserblade.tags;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.item.upgrade.LaserBladeUpgrade.Type;
import com.github.iunius118.tolaserblade.item.upgrade.UpgradeFunctions;
import com.github.iunius118.tolaserblade.item.upgrade.UpgradeResult;
import com.google.common.collect.ImmutableList;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModItemTags {
    private static List<Triple<Tag<Item>, Type, Function<ItemStack, UpgradeResult>>> tags = new ArrayList<>();

    public static final Tag<Item> ATTACK_SPEED_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/speed"), Type.BATTERY, UpgradeFunctions.getUpgradeSpeedFunction());
    public static final Tag<Item> EFFICIENCY_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/efficiency"), Type.BATTERY, UpgradeFunctions.getUpgradeEnchantmentFunction(Enchantments.EFFICIENCY));
    public static final Tag<Item> EFFICIENCY_REMOVER = addUpgradeTag(makeWrapperTag("upgrade/efficiency_remover"), Type.BATTERY, UpgradeFunctions.getRemoveEfficiencyFunction());

    public static final Tag<Item> ATTACK_DAMAGE_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/damage"), Type.MEDIUM, UpgradeFunctions.getUpgradeDamageFunction());
    public static final Tag<Item> LIGHT_ELEMENT_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/light_element"), Type.MEDIUM, UpgradeFunctions.getUpgradeEnchantmentFunction(ModEnchantments.LIGHT_ELEMENT));

    public static final Tag<Item> FIRE_ASPECT_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/fire_aspect"), Type.EMITTER, UpgradeFunctions.getUpgradeEnchantmentFunction(Enchantments.FIRE_ASPECT));
    public static final Tag<Item> SWEEPING_EDGE_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/sweeping_edge"), Type.EMITTER, UpgradeFunctions.getUpgradeEnchantmentFunction(Enchantments.SWEEPING));

    public static final Tag<Item> LOOTING_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/looting"), Type.CASING, UpgradeFunctions.getUpgradeEnchantmentFunction(Enchantments.LOOTING));
    public static final Tag<Item> MENDING_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/mending"), Type.CASING, UpgradeFunctions.getUpgradeEnchantmentFunction(Enchantments.MENDING));

    public static final Tag<Item> CASING_REPAIR = addUpgradeTag(makeWrapperTag("casing_repair"), Type.REPAIR, UpgradeFunctions.getRepairFunction());

    private static Tag<Item> makeWrapperTag(String id) {
        Tag<Item> tag = new ItemTags.Wrapper(new ResourceLocation(ToLaserBlade.MOD_ID, id));
        return tag;
    }

    private static Tag<Item> addUpgradeTag(Tag<Item> tag, Type part, Function<ItemStack, UpgradeResult> function) {
        tags.add(Triple.of(tag, part, function));
        return tag;
    }

    public static List<Triple<Tag<Item>, Type, Function<ItemStack, UpgradeResult>>> getTags() {
        return ImmutableList.copyOf(tags);
    }
}
