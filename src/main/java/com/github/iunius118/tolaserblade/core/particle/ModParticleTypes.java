package com.github.iunius118.tolaserblade.core.particle;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ToLaserBlade.MOD_ID)
public class ModParticleTypes {
    public static final SimpleParticleType LASER_TRAP_X = null;
    public static final SimpleParticleType LASER_TRAP_Y = null;
    public static final SimpleParticleType LASER_TRAP_Z = null;

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
