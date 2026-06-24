package com.github.iunius118.tolaserblade.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public record FabricModObjectRegistry<V, T extends V>(Registry<V> registry, String namespace)
        implements ModObjectRegistry<V, T> {

    @Override
    public Holder<V> register(String name, Supplier<T> object) {
        T registered = Registry.register(registry, Identifier.fromNamespaceAndPath(namespace, name), object.get());
        return Holder.direct(registered);
    }

    @Override
    public ModObjectRegistry<V, T> registerObjects(Consumer<BiFunction<String, Supplier<T>, Holder<V>>> registry) {
        registry.accept(this::register);
        return this;
    }

    @Override
    public void register() {
        // Do nothing
    }
}
