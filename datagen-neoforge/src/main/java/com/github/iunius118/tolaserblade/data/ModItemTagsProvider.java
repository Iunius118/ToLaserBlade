package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;

import java.util.concurrent.CompletableFuture;


public class ModItemTagsProvider extends BlockTagCopyingItemTagProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        Item laserBlade = ModItems.LASER_BLADE;
        Item laserBladeFp = ModItems.LASER_BLADE_FP;

        this.tag(ModTags.Items.LASER_BLADES).add(laserBlade, laserBladeFp);
        this.tag(ModTags.Items.BLUEPRINT_CAN_CHANGE_COLOR).addTag(ModTags.Items.LASER_BLADES)
                .addTag(ModTags.Items.COLORIZER_CAN_CHANGE_COLOR);

        this.tag(ModTags.Items.LASER_BLADE_UPGRADE).addTag(Tags.Items.STORAGE_BLOCKS_DIAMOND);
        this.tag(ModTags.Items.LIGHT_ELEMENT_UPGRADE).add(Items.GLOWSTONE);
        this.tag(ModTags.Items.LOOTING_UPGRADE).add(Items.NAUTILUS_SHELL).addTag(Tags.Items.STORAGE_BLOCKS_EMERALD);

        // Common tags
        this.tag(Tags.Items.MINING_TOOL_TOOLS).addTag(ModTags.Items.LASER_BLADES);
        this.tag(Tags.Items.MELEE_WEAPON_TOOLS).addTag(ModTags.Items.LASER_BLADES);

        // Enchantable tags
        this.tag(ItemTags.MINING_ENCHANTABLE).addTag(ModTags.Items.LASER_BLADES);
        this.tag(ItemTags.MINING_LOOT_ENCHANTABLE).addTag(ModTags.Items.LASER_BLADES);
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).addTag(ModTags.Items.LASER_BLADES);
        this.tag(ItemTags.FIRE_ASPECT_ENCHANTABLE).addTag(ModTags.Items.LASER_BLADES);
        this.tag(ModTags.Items.LASER_BLADE_ENCHANTABLE).addTag(ModTags.Items.LASER_BLADES);

        // LaserBlade-Tools tags
        this.tag(ModTags.Items.COLORIZER_CAN_CHANGE_COLOR).addTag(ModTags.Items.LASER_BLADES);
    }
}
