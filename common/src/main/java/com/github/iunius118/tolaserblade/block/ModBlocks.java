package com.github.iunius118.tolaserblade.block;

import com.github.iunius118.tolaserblade.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {
    public static final LBBlueprintBlock BL_BLUEPRINT = new LBBlueprintBlock(
            createProperties(Constants.Blocks.BL_BLUEPRINT)
                    .mapColor(MapColor.COLOR_BLUE)
                    .strength(0.1F)
                    .sound(SoundType.MOSS_CARPET)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
    );

    private static BlockBehaviour.Properties createProperties(Identifier id) {
        return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, id));
    }
}
