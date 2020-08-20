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
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModItemTags {
    // Tags for Anvil
    private static final List<Triple<ITag.INamedTag<Item>, Type, Function<ItemStack, UpgradeResult>>> TAGS = new ArrayList<>();

    public static final ITag.INamedTag<Item> ATTACK_SPEED_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/speed"), Type.BATTERY, UpgradeFunctions.getUpgradeSpeedFunction());
    public static final ITag.INamedTag<Item> EFFICIENCY_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/efficiency"), Type.BATTERY, UpgradeFunctions.getUpgradeEnchantmentFunction(Enchantments.EFFICIENCY));
    public static final ITag.INamedTag<Item> EFFICIENCY_REMOVER = addUpgradeTag(makeWrapperTag("upgrade/efficiency_remover"), Type.BATTERY, UpgradeFunctions.getRemoveEfficiencyFunction());

    public static final ITag.INamedTag<Item> ATTACK_DAMAGE_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/damage"), Type.MEDIUM, UpgradeFunctions.getUpgradeDamageFunction());
    public static final ITag.INamedTag<Item> LIGHT_ELEMENT_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/light_element"), Type.MEDIUM, UpgradeFunctions.getUpgradeEnchantmentFunction(ModEnchantments.LIGHT_ELEMENT));

    public static final ITag.INamedTag<Item> FIRE_ASPECT_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/fire_aspect"), Type.EMITTER, UpgradeFunctions.getUpgradeEnchantmentFunction(Enchantments.FIRE_ASPECT));
    public static final ITag.INamedTag<Item> SWEEPING_EDGE_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/sweeping_edge"), Type.EMITTER, UpgradeFunctions.getUpgradeEnchantmentFunction(Enchantments.SWEEPING));

    public static final ITag.INamedTag<Item> LOOTING_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/looting"), Type.CASING, UpgradeFunctions.getUpgradeEnchantmentFunction(Enchantments.LOOTING));
    public static final ITag.INamedTag<Item> MENDING_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/mending"), Type.CASING, UpgradeFunctions.getUpgradeEnchantmentFunction(Enchantments.MENDING));
    public static final ITag.INamedTag<Item> FIREPROOF_UPGRADE = addUpgradeTag(makeWrapperTag("upgrade/fireproof"), Type.CASING, UpgradeFunctions.getUpgradeFireproofFunction());

    public static final ITag.INamedTag<Item> CASING_REPAIR = addUpgradeTag(makeWrapperTag("casing_repair"), Type.REPAIR, UpgradeFunctions.getRepairFunction());

    // Tags only for crafting or smithing table
    public static final ITag.INamedTag<Item> LB_DISASSEMBLER = makeWrapperTag("lb_disassembler");

    private static ITag.INamedTag<Item> makeWrapperTag(String id) {
        return ItemTags.makeWrapperTag(ToLaserBlade.MOD_ID + ":" + id);
    }

    private static ITag.INamedTag<Item> addUpgradeTag(ITag.INamedTag<Item> tag, Type part, Function<ItemStack, UpgradeResult> function) {
        TAGS.add(Triple.of(tag, part, function));
        return tag;
    }

    public static List<Triple<ITag.INamedTag<Item>, Type, Function<ItemStack, UpgradeResult>>> getTags() {
        return ImmutableList.copyOf(TAGS);
    }
}
