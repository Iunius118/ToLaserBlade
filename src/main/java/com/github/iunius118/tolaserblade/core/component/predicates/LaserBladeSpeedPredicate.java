package com.github.iunius118.tolaserblade.core.component.predicates;

import com.github.iunius118.tolaserblade.core.component.ModDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.SingleComponentItemPredicate;
import net.minecraft.core.component.DataComponentType;

public record LaserBladeSpeedPredicate(Float attackSpeed) implements SingleComponentItemPredicate<Float> {
    public static final Codec<LaserBladeSpeedPredicate> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.FLOAT.fieldOf("attack_speed").forGetter(LaserBladeSpeedPredicate::attackSpeed)
            ).apply(instance, LaserBladeSpeedPredicate::new)
    );

    @Override
    public DataComponentType<Float> componentType() {
        return ModDataComponents.LASER_BLADE_SPEED;
    }

    @Override
    public boolean matches(Float other) {
        return attackSpeed.equals(other);
    }
}
