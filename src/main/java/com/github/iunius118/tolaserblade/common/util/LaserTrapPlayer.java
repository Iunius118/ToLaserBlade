package com.github.iunius118.tolaserblade.common.util;

import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;

import java.util.List;
import java.util.UUID;

public class LaserTrapPlayer extends FakePlayer {
    private static final GameProfile PROFILE = new GameProfile(UUID.fromString("2BDD19A3-9616-417A-8797-EE805F5FF9E3"), "[LaserBlade]");

    private LaserTrapPlayer(ServerLevel level, GameProfile name) {
        super(level, name);
    }

    public static void attackEntities(ServerLevel serverLevel, BlockPos trapPos, ItemStack itemStackHeld, Direction dir) {
        var laserTrapPlayer = LaserTrapPlayer.get(serverLevel, trapPos, itemStackHeld);
        laserTrapPlayer.attackEntities(dir);
        laserTrapPlayer.remove(Entity.RemovalReason.DISCARDED);
    }

    public static LaserTrapPlayer get(ServerLevel serverLevel, BlockPos trapPos, ItemStack itemStackHeld) {
        var laserTrapPlayer = new LaserTrapPlayer(serverLevel, PROFILE);
        laserTrapPlayer.setPos(trapPos.getX() + 0.5D, trapPos.getY(), trapPos.getZ() + 0.5D);
        laserTrapPlayer.initInventory(itemStackHeld.copy());
        return laserTrapPlayer;
    }

    public void initInventory(ItemStack currentStack) {
        final var inventory = getInventory();
        inventory.clearContent();

        // Set given item stack in main hand
        inventory.selected = 0;
        inventory.setItem(0, currentStack);

        // Apply attack damage from main hand item
        AttributeMap attributeMap = this.getAttributes();
        currentStack.forEachModifier(EquipmentSlot.MAINHAND, (holder, attributeModifier) -> {
            AttributeInstance attributeInstance = attributeMap.getInstance(holder);

            if (attributeInstance != null) {
                attributeInstance.removeModifier(attributeModifier.id());
                attributeInstance.addTransientModifier(attributeModifier);
            }
        });
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
            if (canBurn(targetEntity, fireLevel)) targetEntity.igniteForSeconds(Math.min(fireLevel, 1));
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
            return EnchantmentHelper.getDamageBonus(itemStack, livingEntity.getType());
        } else {
            return 0;
        }
    }

    private boolean canBurn(Entity entity, int fireAspectLevel) {
        return fireAspectLevel > 0 && (entity instanceof Mob || entity instanceof Player);
    }

    private void spawnParticle(Direction dir, BlockPos effectPos, ItemStack itemStack) {
        if (!(level() instanceof ServerLevel serverLevel)) return;

        // Create laser trap particle
        var laserTrapParticleType = ModParticleTypes.getLaserTrapParticleType(dir.getAxis());
        var vecPos = new Vec3(effectPos.getX(), effectPos.getY(), effectPos.getZ()).add(0.5, 0.5, 0.5);
        var color = getParticleColor(itemStack);
        // Spawn particle
        serverLevel.sendParticles(laserTrapParticleType, vecPos.x, vecPos.y, vecPos.z, 0, color.r(), color.g(), color.b(), 1);
    }

    private Color4F getParticleColor(ItemStack itemStack) {
        // Get laser beam color from laser blade
        var appearance = LaserBladeAppearance.of(itemStack);
        int outerColor = appearance.getOuterColor();

        if (appearance.isOuterSubColor()) {
            outerColor = ~outerColor;
        }

        return Color4F.of(outerColor | 0xFF000000);
    }
}
