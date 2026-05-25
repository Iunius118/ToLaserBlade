package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.registry.ModItems;
import com.github.iunius118.tolaserblade.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ToLaserBlade.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        Item laserBlade = ModItems.LASER_BLADE.get();
        Item laserBladeFp = ModItems.LASER_BLADE_FP.get();

        tag(ModItemTags.LASER_BLADES).add(laserBlade, laserBladeFp);

        // Common tags
        tag(Tags.Items.MINING_TOOL_TOOLS).addTag(ModItemTags.LASER_BLADES);
        tag(Tags.Items.MELEE_WEAPON_TOOLS).addTag(ModItemTags.LASER_BLADES);

        // Enchantable tags
        tag(ItemTags.MINING_ENCHANTABLE).addTag(ModItemTags.LASER_BLADES);
        tag(ItemTags.MINING_LOOT_ENCHANTABLE).addTag(ModItemTags.LASER_BLADES);
        tag(ItemTags.DURABILITY_ENCHANTABLE).addTag(ModItemTags.LASER_BLADES);
        tag(ItemTags.FIRE_ASPECT_ENCHANTABLE).addTag(ModItemTags.LASER_BLADES);
        tag(ModItemTags.LASER_BLADE_ENCHANTABLE).addTag(ModItemTags.LASER_BLADES);
    }
}
