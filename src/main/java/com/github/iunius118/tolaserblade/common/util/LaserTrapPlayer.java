package com.github.iunius118.tolaserblade.common.util;

import com.github.iunius118.tolaserblade.config.TLBServerConfig;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
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
import net.minecraftforge.common.util.FakePlayer;

import java.util.List;
import java.util.UUID;

public class LaserTrapPlayer extends FakePlayer {
    private static final GameProfile PROFILE = new GameProfile(UUID.fromString("2BDD19A3-9616-417A-8797-EE805F5FF9E3"), "[LaserBlade]");
    // Block-position without FakePlayer
    private BlockPos blockPosition;

    private LaserTrapPlayer(ServerLevel serverLevel) {
        super(serverLevel, PROFILE);
    }

    public static LaserTrapPlayer get(ServerLevel serverLevel, BlockPos trapPos, ItemStack itemStackHeld) {
        final var laserTrapPlayer = new LaserTrapPlayer(serverLevel);
        laserTrapPlayer.setPos(trapPos.getX() + 0.5D, trapPos.getY(), trapPos.getZ() + 0.5D);
        laserTrapPlayer.blockPosition = new BlockPos(trapPos);
        laserTrapPlayer.initInventory(itemStackHeld.copy());
        return laserTrapPlayer;
    }

    public void initInventory(ItemStack currentStack) {
        var inventory = getInventory();
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

        addEffect(dir, targetPos, itemStack);
    }

    @Override
    public BlockPos blockPosition(){
        return blockPosition;
    }

    private boolean canHitEntity(Entity entity) {
        if (!entity.isSpectator() && entity.isAlive() && entity.isPickable()) {
            // Check if the trap can attack players
            return TLBServerConfig.canLaserTrapAttackPlayer || !(entity instanceof Player);
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

    private void addEffect(Direction dir, BlockPos effectPos, ItemStack itemStack) {
        if (!(level() instanceof ServerLevel serverLevel)) return;

        // Create laser trap particle
        var laserTrapParticleType = ModParticleTypes.getLaserTrapParticleType(dir.getAxis());
        var vecPos = new Vec3(effectPos.getX(), effectPos.getY(), effectPos.getZ()).add(0.5, 0.5, 0.5);
        var color = getParticleColor(itemStack);
        // Spawn particle
        serverLevel.sendParticles(laserTrapParticleType, vecPos.x, vecPos.y, vecPos.z, 0, color.r(), color.g(), color.b(), 1);

        // Play sound effect
        serverLevel.playSound(null, vecPos.x, vecPos.y, vecPos.z, ModSoundEvents.ITEM_LASER_TRAP_ACTIVATE, SoundSource.BLOCKS, 0.5F, 1.0F);
    }

    private Color4F getParticleColor(ItemStack itemStack) {
        // Get laser beam color from laser blade
        var visual = LaserBladeVisual.of(itemStack);
        int outerColor = visual.getOuterColor();

        if (visual.isOuterSubColor()) {
            outerColor = ~outerColor;
        }

        return Color4F.of(outerColor | 0xFF000000);
    }

    @Override public void onEnterCombat() {}
    @Override public void onLeaveCombat() {}
}
