package com.github.iunius118.tolaserblade.client.particle;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class LaserTrapParticle extends Particle {
    public static final RenderType RENDER_TYPE = RenderType.beaconBeam(LaserTrapParticleModel.TEXTURE_LOCATION, true);
    public static final ParticleRenderType PARTICLE_RENDER_TYPE = new ParticleRenderType(ToLaserBlade.makeId("laser_trap").toString());
    public static final LaserTrapParticleModel MODEL_X = new LaserTrapParticleModel(Direction.Axis.X);
    public static final LaserTrapParticleModel MODEL_Y = new LaserTrapParticleModel(Direction.Axis.Y);
    public static final LaserTrapParticleModel MODEL_Z = new LaserTrapParticleModel(Direction.Axis.Z);

    public final LaserTrapParticleModel model;
    public final float rCol;
    public final float gCol;
    public final float bCol;

    protected LaserTrapParticle(ClientLevel clientLevel, double x, double y, double z, float r, float g, float b, Direction.Axis trapAxis) {
        super(clientLevel, x, y, z);
        this.setSize(1F, 1F);
        this.setLifetime(3);

        this.model = switch (trapAxis) {
            case Y -> MODEL_Y;
            case Z -> MODEL_Z;
            default -> MODEL_X;
        };
        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
    }

    public Vec3 getPosition() {
        return new Vec3(this.x, this.y, this.z);
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) remove();
    }

    @Override
    public ParticleRenderType getGroup() {
        return PARTICLE_RENDER_TYPE;
    }

    public record Provider(Direction.Axis axis) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double dx, double dy, double dz, double dr, double dg, double db, RandomSource randomSource) {
            float r = Mth.clamp((float) dr, 0F, 1F);
            float g = Mth.clamp((float) dg, 0F, 1F);
            float b = Mth.clamp((float) db, 0F, 1F);
            return new LaserTrapParticle(clientLevel, dx, dy, dz, r, g, b, axis);
        }
    }
}
