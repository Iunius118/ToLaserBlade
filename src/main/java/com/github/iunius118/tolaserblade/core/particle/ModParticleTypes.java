package com.github.iunius118.tolaserblade.core.particle;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;

public class ModParticleTypes {
    public static final SimpleParticleType LASER_TRAP_X = new SimpleParticleType(true);
    public static final SimpleParticleType LASER_TRAP_Y = new SimpleParticleType(true);
    public static final SimpleParticleType LASER_TRAP_Z = new SimpleParticleType(true);

    public static SimpleParticleType getLaserTrapParticleType(Direction.Axis axis) {
        switch(axis) {
            case X -> {
                return LASER_TRAP_X;
            }
            case Y -> {
                return LASER_TRAP_Y;
            }
            case Z -> {
                return LASER_TRAP_Z;
            }
        }

        return LASER_TRAP_X;
    }
}
