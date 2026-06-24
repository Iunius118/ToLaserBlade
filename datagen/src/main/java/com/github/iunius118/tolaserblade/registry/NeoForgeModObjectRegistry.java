package com.github.iunius118.tolaserblade.registry;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public record NeoForgeModObjectRegistry<V, T extends V>(DeferredRegister<V> deferredRegister, String namespace)
        implements ModObjectRegistry<V, T> {

    @Override
    public Holder<V> register(String name, Supplier<T> object) {
        return deferredRegister.register(name, object);
    }

    @Override
    public ModObjectRegistry<V, T> registerObjects(Consumer<BiFunction<String, Supplier<T>, Holder<V>>> registry) {
        registry.accept(this::register);
        return this;
    }

    @Override
    public void register() {
        if (ToLaserBlade.modEventBus != null) {
            deferredRegister.register(ToLaserBlade.modEventBus);
        }
    }
}
