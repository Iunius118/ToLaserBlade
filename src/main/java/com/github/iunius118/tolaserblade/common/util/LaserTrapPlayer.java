package com.github.iunius118.tolaserblade.common.util;

import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;

// Temporary "extends FakePlayer" due to dummy connection bug in Forge
public class LaserTrapPlayer extends FakePlayer {
    private static final GameProfile PROFILE = new GameProfile(UUID.fromString("2BDD19A3-9616-417A-8797-EE805F5FF9E3"), "[LaserBlade]");

    private LaserTrapPlayer(ServerLevel level, GameProfile name, ClientInformation info) {
        super(level, name, info);
    }

    public static void attackEntities(ServerLevel serverLevel, BlockPos trapPos, ItemStack itemStackHeld, Direction dir) {
        var laserTrapPlayer = LaserTrapPlayer.get(serverLevel, trapPos, itemStackHeld);
        laserTrapPlayer.attackEntities(dir);
    }

    public static LaserTrapPlayer get(ServerLevel serverLevel, BlockPos trapPos, ItemStack itemStackHeld) {
        // Temporary fake player generation due to dummy connection bug in Forge
        var cookie  = CommonListenerCookie.createInitial(PROFILE);
        var laserTrapPlayer = new LaserTrapPlayer(serverLevel, cookie.gameProfile(), cookie.clientInformation());
        new LaserTrapPlayer.NetHandler(laserTrapPlayer);

        laserTrapPlayer.setPos(trapPos.getX() + 0.5D, trapPos.getY(), trapPos.getZ() + 0.5D);
        laserTrapPlayer.initInventory(itemStackHeld.copy());
        return laserTrapPlayer;
    }

    // Temporary network handler due to dummy connection bug in Forge
    private static class NetHandler extends ServerGamePacketListenerImpl {
        private static final Connection DUMMY_CONNECTION = new DummyConnection();

        public NetHandler(ServerPlayer player) {
            super(player.server, DUMMY_CONNECTION, player, CommonListenerCookie.createInitial(player.getGameProfile()));
        }

        private static class DummyConnection extends Connection {
            public DummyConnection() {
                super(PacketFlow.CLIENTBOUND);
            }

            @Override
            public void setListener(PacketListener packetListener) {}
        }
    }

    public void initInventory(ItemStack currentStack) {
        final var inventory = getInventory();
        inventory.clearContent();

        // Set given item stack in main hand
        inventory.selected = 0;
        inventory.setItem(0, currentStack);

        // Apply attack damage from main hand item
        getAttributes().addTransientAttributeModifiers(currentStack.getAttributeModifiers(EquipmentSlot.MAINHAND));
    }

    public void attackEntities(Direction dir) {
        BlockPos trapPos = blockPosition();
        BlockPos targetPos = trapPos.relative(dir);
        AABB aabb = new AABB(targetPos).inflate(0.5D);
        List<Entity> targetEntities = level().getEntities((Entity) null, aabb, this::canHitEntity);

        float attackDamage = (float) getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        int fireLevel = EnchantmentHelper.getFireAspect(this);
        ItemStack itemStack = getMainHandItem();

        for (var targetEntity : targetEntities) {
            float totalDamage = attackDamage + getDamageBonus(itemStack, targetEntity);
            if (canBurn(targetEntity, fireLevel)) targetEntity.setSecondsOnFire(Math.min(fireLevel, 1));
            targetEntity.hurt(damageSources().playerAttack(this), totalDamage);
            EnchantmentHelper.doPostDamageEffects(this, targetEntity);
        }

        spawnParticle(dir, targetPos, itemStack);
    }

    private boolean canHitEntity(Entity entity) {
        if (!entity.isSpectator() && entity.isAlive() && entity.isPickable()) {
            // Check if the trap can attack players
            boolean canAttackPlayers = ToLaserBladeConfig.SERVER.canLaserTrapAttackPlayer.get();
            return canAttackPlayers || !(entity instanceof Player);
        } else {
            return false;
        }
    }

    private float getDamageBonus(ItemStack itemStack, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return EnchantmentHelper.getDamageBonus(itemStack, livingEntity.getMobType());
        } else {
            return 0;
        }
    }

    private boolean canBurn(Entity entity, int fireAspectLevel) {
        return fireAspectLevel > 0 && (entity instanceof Mob || entity instanceof Player);
    }

    private void spawnParticle(Direction dir, BlockPos effectPos, ItemStack itemStack) {
        if (!(level() instanceof ServerLevel serverLevel)) return;

        var laserTrapParticleType = ModParticleTypes.getLaserTrapParticleType(dir.getAxis());
        var vecPos = new Vec3(effectPos.getX(), effectPos.getY(), effectPos.getZ()).add(0.5, 0.5, 0.5);
        var visual = LaserBladeVisual.of(itemStack);
        int outerColor = visual.getOuterColor();
        var color4F = Color4F.of((visual.isOuterSubColor() ? ~outerColor : outerColor) | 0xFF000000);
        serverLevel.sendParticles(laserTrapParticleType, vecPos.x, vecPos.y, vecPos.z, 0, color4F.r(), color4F.g(), color4F.b(), 1);
    }
}
