package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.tags.ModItemTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class TLBItemTagsProvider extends ForgeItemTagsProvider {
    private Set<ResourceLocation> filter = null;

    public TLBItemTagsProvider(DataGenerator gen, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(gen, blockTagProvider, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags() {
        super.addTags();
        filter = new HashSet<>(this.builders.keySet());
        tag(ModItemTags.ATTACK_SPEED_UPGRADE).addTags(Tags.Items.INGOTS_GOLD);
        tag(ModItemTags.EFFICIENCY_UPGRADE).addTags(Tags.Items.STORAGE_BLOCKS_REDSTONE);
        tag(ModItemTags.EFFICIENCY_REMOVER).addTags(Tags.Items.DUSTS_REDSTONE);
        tag(ModItemTags.ATTACK_DAMAGE_UPGRADE).addTags(Tags.Items.GEMS_DIAMOND);
        tag(ModItemTags.LIGHT_ELEMENT_UPGRADE).add(Items.GLOWSTONE);
        tag(ModItemTags.FIRE_ASPECT_UPGRADE).addTags(Tags.Items.RODS_BLAZE);
        tag(ModItemTags.SWEEPING_EDGE_UPGRADE).add(Items.ENDER_EYE);
        tag(ModItemTags.SILK_TOUCH_UPGRADE).add(Items.PRISMARINE_CRYSTALS);
        tag(ModItemTags.LOOTING_UPGRADE).add(Items.NAUTILUS_SHELL).addTags(Tags.Items.STORAGE_BLOCKS_EMERALD);
        tag(ModItemTags.MENDING_UPGRADE).add(Items.NETHER_STAR, Items.DRAGON_HEAD, Items.TOTEM_OF_UNDYING);
        tag(ModItemTags.CASING_REPAIR).addTags(Tags.Items.INGOTS_IRON);
    }

    @Override
    protected Path getPath(ResourceLocation id) {
        return (filter != null && filter.contains(id)) ? null : super.getPath(id);
    }

    @Override
    public String getName() {
        return "ToLaserBlade " + super.getName();
    }
}
