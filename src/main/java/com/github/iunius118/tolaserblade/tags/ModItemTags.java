package com.github.iunius118.tolaserblade.tags;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.laserblade.upgrade.UpgradeID;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModItemTags {
    public static final ITag.INamedTag<Item> ATTACK_SPEED_UPGRADE = makeWrapperTag(UpgradeID.ATTACK_SPEED_UPGRADE);
    public static final ITag.INamedTag<Item> EFFICIENCY_UPGRADE = makeWrapperTag(UpgradeID.EFFICIENCY_UPGRADE);
    public static final ITag.INamedTag<Item> EFFICIENCY_REMOVER = makeWrapperTag(UpgradeID.EFFICIENCY_REMOVER);

    public static final ITag.INamedTag<Item> ATTACK_DAMAGE_UPGRADE = makeWrapperTag(UpgradeID.ATTACK_DAMAGE_UPGRADE);
    public static final ITag.INamedTag<Item> LIGHT_ELEMENT_UPGRADE = makeWrapperTag(UpgradeID.LIGHT_ELEMENT_UPGRADE);

    public static final ITag.INamedTag<Item> FIRE_ASPECT_UPGRADE = makeWrapperTag(UpgradeID.FIRE_ASPECT_UPGRADE);
    public static final ITag.INamedTag<Item> SWEEPING_EDGE_UPGRADE = makeWrapperTag(UpgradeID.SWEEPING_EDGE_UPGRADE);
    public static final ITag.INamedTag<Item> SILK_TOUCH_UPGRADE = makeWrapperTag(UpgradeID.SILK_TOUCH_UPGRADE);

    public static final ITag.INamedTag<Item> LOOTING_UPGRADE = makeWrapperTag(UpgradeID.LOOTING_UPGRADE);
    public static final ITag.INamedTag<Item> MENDING_UPGRADE = makeWrapperTag(UpgradeID.MENDING_UPGRADE);

    public static final ITag.INamedTag<Item> CASING_REPAIR = makeWrapperTag("casing_repair");

    private static ITag.INamedTag<Item> makeWrapperTag(String id) {
        return ItemTags.makeWrapperTag(ToLaserBlade.MOD_ID + ":" + id);
    }

    private static ITag.INamedTag<Item> makeWrapperTag(UpgradeID upgradeID) {
        ResourceLocation id = upgradeID.getID();
        return ItemTags.makeWrapperTag(id.toString());
    }
}
