package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.world.item.LBSwordItem;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "applyComponentsAndValidate(Lnet/minecraft/core/component/DataComponentPatch;)V", at = @At("TAIL"))
    private void onApplyComponentsAndValidate(DataComponentPatch dataComponentPatch, CallbackInfo ci) {
        LBSwordItem.updateItemAttributeModifiers(ItemStack.class.cast(this));
    }

    @Inject(method = "applyComponents(Lnet/minecraft/core/component/DataComponentPatch;)V", at = @At("TAIL"))
    private void onApplyComponents(DataComponentPatch dataComponentPatch, CallbackInfo ci) {
        LBSwordItem.updateItemAttributeModifiers(ItemStack.class.cast(this));
    }

    @Inject(method = "applyComponents(Lnet/minecraft/core/component/DataComponentMap;)V", at = @At("TAIL"))
    private void onApplyComponents(DataComponentMap dataComponentMap, CallbackInfo ci) {
        LBSwordItem.updateItemAttributeModifiers(ItemStack.class.cast(this));
    }
}
