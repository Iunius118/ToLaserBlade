package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.item.LBSwordItem;
import com.github.iunius118.tolaserblade.sounds.ModSoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixin {

    @Inject(method = "attack", at = @At("HEAD"))
    public void onAttack(Entity entity, CallbackInfo info) {
        var player = (Player)(Object) this;
        toLaserBlade$onAttackEntity(player, entity);
    }

    @Unique
    private void toLaserBlade$onAttackEntity(Player player, Entity entity) {
        var level = player.level();

        if (!level.isClientSide() && !player.isSpectator()) {
            var itemStack = player.getMainHandItem();

            // Play the laser blade hit sound effect when the player attacks an entity
            if (itemStack.getItem() instanceof LBSwordItem) {
                Vec3 pos = entity.position().add(0, entity.getEyeHeight(), 0);
                level.playSound(null, pos.x, pos.y, pos.z,
                        ModSoundEvents.ITEM_LASER_BLADE_HIT, entity.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }
}
