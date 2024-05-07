package com.github.iunius118.tolaserblade.core.component;

import com.github.iunius118.tolaserblade.world.item.component.LaserBladeModelData;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;

public class ModDataComponents {
    public static final DataComponentType<Float> LASER_BLADE_ATTACK = new DataComponentType.Builder<Float>()
            .persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build();
    public static final DataComponentType<Float> LASER_BLADE_SPEED = new DataComponentType.Builder<Float>()
            .persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT).build();
    public static final DataComponentType<LaserBladeModelData> LASER_BLADE_MODEL = new DataComponentType.Builder<LaserBladeModelData>()
            .persistent(LaserBladeModelData.CODEC).networkSynchronized(LaserBladeModelData.STREAM_CODEC).build();
}
