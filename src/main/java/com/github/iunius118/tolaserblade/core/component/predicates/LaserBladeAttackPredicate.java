package com.github.iunius118.tolaserblade.core.component.predicates;

import com.github.iunius118.tolaserblade.core.component.ModDataComponents;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.SingleComponentItemPredicate;
import net.minecraft.core.component.DataComponentType;

public record LaserBladeAttackPredicate(Float attackDamage) implements SingleComponentItemPredicate<Float> {
    public static final Codec<LaserBladeAttackPredicate> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.FLOAT.fieldOf("attack_damage").forGetter(LaserBladeAttackPredicate::attackDamage)
            ).apply(instance, LaserBladeAttackPredicate::new)
    );

    @Override
    public DataComponentType<Float> componentType() {
        return ModDataComponents.LASER_BLADE_ATTACK;
    }

    @Override
    public boolean matches(Float other) {
        return attackDamage.equals(other);
    }
}
