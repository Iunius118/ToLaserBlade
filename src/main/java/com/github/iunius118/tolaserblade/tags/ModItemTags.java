package com.github.iunius118.tolaserblade.tags;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.UpgradeID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> ATTACK_SPEED_UPGRADE = makeWrapperTag(UpgradeID.ATTACK_SPEED_UPGRADE);
    public static final TagKey<Item> EFFICIENCY_UPGRADE = makeWrapperTag(UpgradeID.EFFICIENCY_UPGRADE);
    public static final TagKey<Item> EFFICIENCY_REMOVER = makeWrapperTag(UpgradeID.EFFICIENCY_REMOVER);

    public static final TagKey<Item> ATTACK_DAMAGE_UPGRADE = makeWrapperTag(UpgradeID.ATTACK_DAMAGE_UPGRADE);
    public static final TagKey<Item> LIGHT_ELEMENT_UPGRADE = makeWrapperTag(UpgradeID.LIGHT_ELEMENT_UPGRADE);

    public static final TagKey<Item> FIRE_ASPECT_UPGRADE = makeWrapperTag(UpgradeID.FIRE_ASPECT_UPGRADE);
    public static final TagKey<Item> SWEEPING_EDGE_UPGRADE = makeWrapperTag(UpgradeID.SWEEPING_EDGE_UPGRADE);
    public static final TagKey<Item> SILK_TOUCH_UPGRADE = makeWrapperTag(UpgradeID.SILK_TOUCH_UPGRADE);

    public static final TagKey<Item> LOOTING_UPGRADE = makeWrapperTag(UpgradeID.LOOTING_UPGRADE);
    public static final TagKey<Item> MENDING_UPGRADE = makeWrapperTag(UpgradeID.MENDING_UPGRADE);

    public static final TagKey<Item> CASING_REPAIR = makeWrapperTag("casing_repair");

    private static TagKey<Item> makeWrapperTag(String id) {
        return ItemTags.create(new ResourceLocation(ToLaserBlade.MOD_ID, id));
    }

    private static TagKey<Item> makeWrapperTag(UpgradeID upgradeID) {
        ResourceLocation id = upgradeID.getID();
        return ItemTags.create(id);
    }
}
