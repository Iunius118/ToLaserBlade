package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.tags.ModItemTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class TLBItemTagsProvider extends ForgeItemTagsProvider {
    private Set<ResourceLocation> filter = null;

    public TLBItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagProvider) {
        super(generator, blockTagProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void registerTags() {
        super.registerTags();
        filter = new HashSet<>(this.tagToBuilder.keySet());

        //  TODO: func_240521_a_ = getBuilder
        //  TODO: func_240534_a_ = add(Item...)
        func_240522_a_(ModItemTags.ATTACK_SPEED_UPGRADE).addTags(Tags.Items.INGOTS_GOLD);
        func_240522_a_(ModItemTags.EFFICIENCY_UPGRADE).addTags(Tags.Items.STORAGE_BLOCKS_REDSTONE);
        func_240522_a_(ModItemTags.EFFICIENCY_REMOVER).addTags(Tags.Items.DUSTS_REDSTONE);
        func_240522_a_(ModItemTags.ATTACK_DAMAGE_UPGRADE).addTags(Tags.Items.GEMS_DIAMOND);
        func_240522_a_(ModItemTags.LIGHT_ELEMENT_UPGRADE).func_240534_a_(Items.GLOWSTONE);
        func_240522_a_(ModItemTags.FIRE_ASPECT_UPGRADE).addTags(Tags.Items.RODS_BLAZE);
        func_240522_a_(ModItemTags.SWEEPING_EDGE_UPGRADE).func_240534_a_(Items.ENDER_EYE);
        func_240522_a_(ModItemTags.LOOTING_UPGRADE).func_240534_a_(Items.NAUTILUS_SHELL).addTags(Tags.Items.STORAGE_BLOCKS_EMERALD);
        func_240522_a_(ModItemTags.MENDING_UPGRADE).func_240534_a_(Items.NETHER_STAR, Items.DRAGON_HEAD, Items.TOTEM_OF_UNDYING);
        func_240522_a_(ModItemTags.CASING_REPAIR).addTags(Tags.Items.INGOTS_IRON);
    }

    @Override
    protected Path makePath(ResourceLocation id) {
        return (filter != null && filter.contains(id)) ? null : super.makePath(id);
    }

    @Override
    public String getName() {
        return "ToLaserBlade " + super.getName();
    }
}
