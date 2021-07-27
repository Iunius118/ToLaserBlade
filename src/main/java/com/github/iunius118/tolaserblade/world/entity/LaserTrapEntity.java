package com.github.iunius118.tolaserblade.world.entity;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class LaserTrapEntity extends Entity implements IEntityAdditionalSpawnData {
    public static final ResourceLocation ID = new ResourceLocation(ToLaserBlade.MOD_ID, "laser_trap");
    private int life = 3;
    private int color = LaserBladeColor.RED.getBladeColor();

    public static final String KEY_LIFE = "life";
    public static final String KEY_COLOR = "color";

    public LaserTrapEntity(EntityType<LaserTrapEntity> entityType, Level level) {
        super(entityType, level);
    }

    public LaserTrapEntity(Level level, BlockPos pos, Direction direction, int bladeColor) {
        this(ModEntities.LASER_TRAP, level);
        setPos((double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D);
        setRot(direction.toYRot(), (float)(direction.getNormal().getY() * -90));
        color = bladeColor;
    }

    @Override
    protected float getEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    @Override
    protected void defineSynchedData() {
        // Init data manager
    }

    @Override
    public void tick() {
        super.tick();

        if (--life < 0) {
            remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        life = compound.getInt(KEY_LIFE);
        color = compound.getInt(KEY_COLOR);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt(KEY_LIFE, life);
        compound.putInt(KEY_COLOR, color);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(color);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        color = additionalData.readInt();
    }

    public int getColor() {
        return color;
    }
}
