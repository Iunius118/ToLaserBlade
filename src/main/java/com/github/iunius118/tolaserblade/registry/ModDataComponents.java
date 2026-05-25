package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.component.BlendModes;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    private static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ToLaserBlade.MOD_ID);

    public static final Supplier<DataComponentType<Integer>> MODEL =
            DATA_COMPONENT_TYPES.registerComponentType("model",
                    builder -> builder
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT)
            );
    public static final Supplier<DataComponentType<BlendModes>> BLEND_MODES =
            DATA_COMPONENT_TYPES.registerComponentType("blend_modes",
                    builder -> builder
                            .persistent(BlendModes.CODEC)
                            .networkSynchronized(BlendModes.STREAM_CODEC)
            );

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENT_TYPES.register(modEventBus);
    }
}
