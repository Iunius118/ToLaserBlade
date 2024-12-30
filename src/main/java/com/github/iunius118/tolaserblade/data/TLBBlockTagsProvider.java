package com.github.iunius118.tolaserblade.data;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class TLBBlockTagsProvider extends BlockTagsProvider {
    public TLBBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ToLaserBlade.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        // Nothing to add
    }
}
