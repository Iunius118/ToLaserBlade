package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public record NeoForgeModObjectRegistry<V, T extends V>(DeferredRegister<V> deferredRegister, String namespace)
        implements ModObjectRegistry<V, T> {

    @Override
    public ModRegistryObject<V> register(String name, Supplier<T> object) {
        return ModRegistryObject.of(deferredRegister.register(name, object));
    }

    @Override
    public void register() {
        if (ToLaserBlade.modEventBus != null) {
            deferredRegister.register(ToLaserBlade.modEventBus);
        }
    }
}
