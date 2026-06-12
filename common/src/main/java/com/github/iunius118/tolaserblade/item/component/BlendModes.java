package com.github.iunius118.tolaserblade.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

/**
 * Data component that stores flags indicating whether each laser blade part
 * should be rendered using subtractive color blending.
 *
 * @param blendModes List of flags corresponding to each part: [outer blade, inner blade]
 */
public record BlendModes(List<Boolean> blendModes) {
    public static final BlendModes DEFAULT = new BlendModes(List.of(false, false));
    public static final Codec<BlendModes> CODEC = RecordCodecBuilder.create(
            i -> i.group(
                    Codec.BOOL.listOf().optionalFieldOf("modes", List.of(false, false))
                            .forGetter(BlendModes::blendModes))
                    .apply(i, BlendModes::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, BlendModes> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL.apply(ByteBufCodecs.list()), BlendModes::blendModes,
            BlendModes::new
    );

    public Boolean getMode(int index) {
        return (index >= 0 && index < blendModes.size() && blendModes.get(index) != null) ?
                blendModes.get(index) : false;
    }
}
