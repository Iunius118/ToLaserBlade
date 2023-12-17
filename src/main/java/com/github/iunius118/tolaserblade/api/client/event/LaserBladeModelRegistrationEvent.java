package com.github.iunius118.tolaserblade.api.client.event;

import com.github.iunius118.tolaserblade.api.ToLaserBladeAPI;
import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import net.neoforged.bus.api.Event;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

/**
 * Event that is fired to register laser blade models at the appropriate time.
 * Use {@link ToLaserBladeAPI#registerModelRegistrationListener} to register instances.
 */
public class LaserBladeModelRegistrationEvent extends Event {
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
