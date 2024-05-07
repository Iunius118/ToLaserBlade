package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TLBItemTagsProvider extends ItemTagsProvider {
    private Set<ResourceLocation> filter = null;

    public TLBItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, blockTagProvider, ToLaserBlade.MOD_ID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        filter = new HashSet<>(this.builders.keySet());
        tag(ModItemTags.ATTACK_SPEED_UPGRADE).addTag(Tags.Items.INGOTS_GOLD);
        tag(ModItemTags.EFFICIENCY_UPGRADE).addTag(Tags.Items.STORAGE_BLOCKS_REDSTONE);
        tag(ModItemTags.EFFICIENCY_REMOVER).addTag(Tags.Items.DUSTS_REDSTONE);
        tag(ModItemTags.ATTACK_DAMAGE_UPGRADE).addTag(Tags.Items.STORAGE_BLOCKS_DIAMOND);
        tag(ModItemTags.LIGHT_ELEMENT_UPGRADE).add(Items.GLOWSTONE);
        tag(ModItemTags.FIRE_ASPECT_UPGRADE).addTag(Tags.Items.RODS_BLAZE);
        tag(ModItemTags.SWEEPING_EDGE_UPGRADE).add(Items.ENDER_EYE);
        tag(ModItemTags.SILK_TOUCH_UPGRADE).add(Items.PRISMARINE_CRYSTALS).add(Items.AMETHYST_SHARD).add(Items.ECHO_SHARD);
        tag(ModItemTags.LOOTING_UPGRADE).add(Items.NAUTILUS_SHELL).addTag(Tags.Items.STORAGE_BLOCKS_EMERALD);
        tag(ModItemTags.MENDING_UPGRADE).add(Items.NETHER_STAR, Items.DRAGON_HEAD, Items.TOTEM_OF_UNDYING);
        tag(ModItemTags.CASING_REPAIR).addTag(Tags.Items.INGOTS_IRON);
    }

    @Override
    protected Path getPath(ResourceLocation id) {
        return (filter != null && filter.contains(id)) ? null : super.getPath(id);
    }
}
