package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.world.item.LBSwordItem;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, remap = false)
public abstract class MixinLivingEntity extends Entity implements Attackable, ILivingEntityExtension {
    private MixinLivingEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "isDamageSourceBlocked(Lnet/minecraft/world/damagesource/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    private void onIsDamageSourceBlocked(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        // Provide blocking function to laser blades
        var livingEntity = LivingEntity.class.cast(this);
        var useItem = livingEntity.getUseItem().getItem() ;

        if (!(useItem instanceof LBSwordItem)) {
            return;
        }

        var entity = damageSource.getDirectEntity();
        boolean flag = (entity instanceof AbstractArrow abstractarrow) && (abstractarrow.getPierceLevel() > 0);
        var itemStack = livingEntity.getItemBlockingWith();

        if (!damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && itemStack != null && itemStack.getItem() instanceof LBSwordItem && !flag) {
            Vec3 damageSourcePos = damageSource.getSourcePosition();

            if (damageSourcePos != null) {
                Vec3 blockingDir = livingEntity.calculateViewVector(0.0F, livingEntity.getYHeadRot());
                Vec3 damageSourceDir = damageSourcePos.vectorTo(livingEntity.position());
                damageSourceDir = new Vec3(damageSourceDir.x, 0.0, damageSourceDir.z).normalize();
                boolean canBlock = damageSourceDir.dot(blockingDir) < 0.0;
                cir.setReturnValue(canBlock);
                cir.cancel();
            }
        }
    }
}
