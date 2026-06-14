package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
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

        tag(ModTags.Items.LASER_BLADES).add(laserBlade, laserBladeFp);

        // Common tags
        tag(Tags.Items.MINING_TOOL_TOOLS).addTag(ModTags.Items.LASER_BLADES);
        tag(Tags.Items.MELEE_WEAPON_TOOLS).addTag(ModTags.Items.LASER_BLADES);

        // Enchantable tags
        tag(ItemTags.MINING_ENCHANTABLE).addTag(ModTags.Items.LASER_BLADES);
        tag(ItemTags.MINING_LOOT_ENCHANTABLE).addTag(ModTags.Items.LASER_BLADES);
        tag(ItemTags.DURABILITY_ENCHANTABLE).addTag(ModTags.Items.LASER_BLADES);
        tag(ItemTags.FIRE_ASPECT_ENCHANTABLE).addTag(ModTags.Items.LASER_BLADES);
        tag(ModTags.Items.LASER_BLADE_ENCHANTABLE).addTag(ModTags.Items.LASER_BLADES);

        // LaserBlade-Tools tags
        tag(ModTags.Items.COLORIZER_CAN_CHANGE_COLOR).addTag(ModTags.Items.LASER_BLADES);
    }
}
