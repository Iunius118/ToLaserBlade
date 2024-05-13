package com.github.iunius118.tolaserblade.world.item.component;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Map;
import java.util.Objects;

public record LaserBladeModelData(int modelType, Map<String, PartData> parts) {
    public static final LaserBladeModelData DEFAULT = new LaserBladeModelData(LaserBlade.TYPE_DEFAULT, Map.of());
    public static final Codec<LaserBladeModelData> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.optionalFieldOf("type", LaserBlade.TYPE_DEFAULT).forGetter(LaserBladeModelData::modelType),
                    Codec.unboundedMap(Codec.STRING, PartData.CODEC).optionalFieldOf("parts", Map.of()).forGetter(LaserBladeModelData::parts)
            ).apply(instance, LaserBladeModelData::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, LaserBladeModelData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, LaserBladeModelData::modelType,
            ByteBufCodecs.map(Object2ObjectOpenHashMap::new, ByteBufCodecs.STRING_UTF8, PartData.STREAM_CODEC), LaserBladeModelData::parts,
            LaserBladeModelData::new
    );

    public LaserBladeModelData(int modelType, Map<String, PartData> parts) {
        this.modelType = modelType;
        this.parts = Objects.requireNonNullElse(parts, Map.of());
    }

    public record PartData(boolean exists, int color, boolean isSubtractiveColor) {
        public static final PartData ABSENT = new PartData(false, -1, false);
        public static final Codec<PartData> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.INT.fieldOf("color").forGetter(PartData::color),
                        Codec.BOOL.fieldOf("is_sub").forGetter(PartData::isSubtractiveColor)
                ).apply(instance, PartData::create)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, PartData> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, PartData::color,
                ByteBufCodecs.BOOL, PartData::isSubtractiveColor,
                PartData::create
        );

        public static PartData create(int color, boolean isSubtractiveColor) {
            return new PartData(true, color, isSubtractiveColor);
        }
    }
}
