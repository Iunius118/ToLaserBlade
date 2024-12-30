package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.tags.ModEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class TLBEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public TLBEntityTypeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider, ToLaserBlade.MOD_ID);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        tag(ModEntityTypeTags.SENSITIVE_TO_LIGHT_ELEMENT).addTag(EntityTypeTags.UNDEAD).addTag(EntityTypeTags.ILLAGER);
    }
}
