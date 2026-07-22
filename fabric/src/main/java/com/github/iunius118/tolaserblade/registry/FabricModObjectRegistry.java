package com.github.iunius118.tolaserblade.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import java.util.function.Supplier;

public record FabricModObjectRegistry<V, T extends V>(Registry<V> registry, String namespace)
        implements ModObjectRegistry<V, T> {

    @Override
    public Holder<V> register(String name, Supplier<T> object) {
        return Registry.registerForHolder(registry, Identifier.fromNamespaceAndPath(namespace, name), object.get());
    }

    @Override
    public void register() {
        // Do nothing
    }
}
