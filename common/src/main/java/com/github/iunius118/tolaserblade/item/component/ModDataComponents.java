package com.github.iunius118.tolaserblade.item.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;

public class ModDataComponents {
    public static final DataComponentType<Integer> MODEL =
            new DataComponentType.Builder<Integer>()
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.INT)
                    .build();
    public static final DataComponentType<BlendModes> BLEND_MODES =
            new DataComponentType.Builder<BlendModes>()
                    .persistent(BlendModes.CODEC)
                    .networkSynchronized(BlendModes.STREAM_CODEC)
                    .build();
}
