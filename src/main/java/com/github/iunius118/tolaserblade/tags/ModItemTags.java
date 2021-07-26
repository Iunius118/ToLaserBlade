package com.github.iunius118.tolaserblade.tags;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final Tag.Named<Item> ATTACK_SPEED_UPGRADE = makeWrapperTag(UpgradeID.ATTACK_SPEED_UPGRADE);
    public static final Tag.Named<Item> EFFICIENCY_UPGRADE = makeWrapperTag(UpgradeID.EFFICIENCY_UPGRADE);
    public static final Tag.Named<Item> EFFICIENCY_REMOVER = makeWrapperTag(UpgradeID.EFFICIENCY_REMOVER);

    public static final Tag.Named<Item> ATTACK_DAMAGE_UPGRADE = makeWrapperTag(UpgradeID.ATTACK_DAMAGE_UPGRADE);
    public static final Tag.Named<Item> LIGHT_ELEMENT_UPGRADE = makeWrapperTag(UpgradeID.LIGHT_ELEMENT_UPGRADE);

    public static final Tag.Named<Item> FIRE_ASPECT_UPGRADE = makeWrapperTag(UpgradeID.FIRE_ASPECT_UPGRADE);
    public static final Tag.Named<Item> SWEEPING_EDGE_UPGRADE = makeWrapperTag(UpgradeID.SWEEPING_EDGE_UPGRADE);
    public static final Tag.Named<Item> SILK_TOUCH_UPGRADE = makeWrapperTag(UpgradeID.SILK_TOUCH_UPGRADE);

    public static final Tag.Named<Item> LOOTING_UPGRADE = makeWrapperTag(UpgradeID.LOOTING_UPGRADE);
    public static final Tag.Named<Item> MENDING_UPGRADE = makeWrapperTag(UpgradeID.MENDING_UPGRADE);

    public static final Tag.Named<Item> CASING_REPAIR = makeWrapperTag("casing_repair");

    private static Tag.Named<Item> makeWrapperTag(String id) {
        return ItemTags.createOptional(new ResourceLocation(ToLaserBlade.MOD_ID, id));
    }

    private static Tag.Named<Item> makeWrapperTag(UpgradeID upgradeID) {
        ResourceLocation id = upgradeID.getID();
        return ItemTags.createOptional(id);
    }
}
