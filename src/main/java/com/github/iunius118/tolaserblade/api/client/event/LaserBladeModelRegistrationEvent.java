package com.github.iunius118.tolaserblade.api.client.event;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.event.IModBusEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

/**
 * Event that is fired to register laser blade models at the appropriate time.
 * This event is fired on the {@linkplain FMLJavaModLoadingContext#getModEventBus() mod-specific event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}.
 */
public class LaserBladeModelRegistrationEvent extends Event implements IModBusEvent {
    private final Consumer<List<LaserBladeModel>> register;

    public LaserBladeModelRegistrationEvent(@Nonnull Consumer<List<LaserBladeModel>> register) {
        this.register = register;
    }

    /**
     * Register laser blade models to ToLaserBlade.
     * @param models List of laser blade models to register.
     */
    public void register(List<LaserBladeModel> models) {
        register.accept(models);
    }
}
