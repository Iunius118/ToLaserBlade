package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.tags.ModItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

public class TLBItemTagsProvider extends ItemTagsProvider {
    public TLBItemTagsProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerTags() {
        getBuilder(ModItemTags.ATTACK_SPEED_UPGRADE).add(Tags.Items.INGOTS_GOLD);
        getBuilder(ModItemTags.EFFICIENCY_UPGRADE).add(Tags.Items.STORAGE_BLOCKS_REDSTONE);
        getBuilder(ModItemTags.EFFICIENCY_REMOVER).add(Tags.Items.DUSTS_REDSTONE);
        getBuilder(ModItemTags.ATTACK_DAMAGE_UPGRADE).add(Tags.Items.GEMS_DIAMOND);
        getBuilder(ModItemTags.LIGHT_ELEMENT_UPGRADE).add(Items.GLOWSTONE);
        getBuilder(ModItemTags.FIRE_ASPECT_UPGRADE).add(Tags.Items.RODS_BLAZE);
        getBuilder(ModItemTags.SWEEPING_EDGE_UPGRADE).add(Items.ENDER_EYE);
        getBuilder(ModItemTags.LOOTING_UPGRADE).add(Items.NAUTILUS_SHELL).add(Tags.Items.STORAGE_BLOCKS_EMERALD);
        getBuilder(ModItemTags.MENDING_UPGRADE).add(Items.NETHER_STAR, Items.DRAGON_HEAD, Items.TOTEM_OF_UNDYING);
        getBuilder(ModItemTags.CASING_REPAIR).add(Tags.Items.INGOTS_IRON);
    }

    @Override
    public String getName() {
        return "ToLaserBlade " + super.getName();
    }
}
