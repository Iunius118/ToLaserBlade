package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.item.LBSwordItem;
import com.github.iunius118.tolaserblade.sounds.ModSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class, remap = false)
public abstract class LivingEntityMixin {

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"))
    private void onSwing(InteractionHand hand, boolean sendToSwingingEntity, CallbackInfo ci) {
        var livingEntity = (LivingEntity)(Object) this;
        var itemStack = livingEntity.getItemInHand(hand);

        if (!itemStack.isEmpty()) {
            toLaserBlade$onEntitySwing(livingEntity, itemStack);
        }
    }

    @Unique
    private void toLaserBlade$onEntitySwing(LivingEntity livingEntity, ItemStack itemStack) {
        var level = livingEntity.level();

        // Play the laser blade swing sound effect when the entity swings a laser blade item
        if (!level.isClientSide() && itemStack.getItem() instanceof LBSwordItem && !livingEntity.swinging) {
            Vec3 pos = livingEntity.position();
            level.playSound(null, pos.x, pos.y, pos.z,
                    ModSoundEvents.ITEM_LASER_BLADE_SWING, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}
