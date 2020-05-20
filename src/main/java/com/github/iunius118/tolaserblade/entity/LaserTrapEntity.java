package com.github.iunius118.tolaserblade.entity;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public class LaserTrapEntity extends Entity implements IEntityAdditionalSpawnData {
    public static final ResourceLocation ID = new ResourceLocation(ToLaserBlade.MOD_ID, "laser_trap");
    private int life = 3;
    private int color = LaserBladeItemBase.DEFAULT_COLOR_OUTER;

    public static final String KEY_LIFE = "life";
    public static final String KEY_COLOR = "color";

    public LaserTrapEntity(EntityType<LaserTrapEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public LaserTrapEntity(World worldIn, BlockPos pos, Direction direction, int bladeColor) {
        this(ModEntities.LASER_TRAP, worldIn);
        setPosition((double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D);
        setRotation(direction.getHorizontalAngle(), (float)(direction.getDirectionVec().getY() * -90));
        color = bladeColor;
    }

    @Override
    protected void registerData() {
        // Init data manager
    }

    @Override
    public void tick() {
        super.tick();

        if (--life < 0) {
            remove();
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        life = compound.getInt(KEY_LIFE);
        color = compound.getInt(KEY_COLOR);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt(KEY_LIFE, life);
        compound.putInt(KEY_COLOR, color);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeInt(color);
    }

    @Override
    public void readSpawnData(PacketBuffer buffer) {
        color = buffer.readInt();
    }

    public int getColor() {
        return color;
    }
}
