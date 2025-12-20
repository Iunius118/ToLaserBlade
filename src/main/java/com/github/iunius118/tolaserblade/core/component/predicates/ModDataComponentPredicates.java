package com.github.iunius118.tolaserblade.core.component.predicates;

import net.minecraft.core.component.predicates.DataComponentPredicate;

public class ModDataComponentPredicates {
    public static final DataComponentPredicate.Type<LaserBladeAttackPredicate> LASER_BLADE_ATTACK = new DataComponentPredicate.ConcreteType<>(LaserBladeAttackPredicate.CODEC);
    public static final DataComponentPredicate.Type<LaserBladeSpeedPredicate> LASER_BLADE_SPEED = new DataComponentPredicate.ConcreteType<>(LaserBladeSpeedPredicate.CODEC);
}
