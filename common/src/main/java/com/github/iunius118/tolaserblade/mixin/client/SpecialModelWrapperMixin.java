package com.github.iunius118.tolaserblade.mixin.client;

import com.github.iunius118.tolaserblade.client.renderer.LBSwordRenderContext;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.SpecialModelWrapper;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SpecialModelWrapper.class)
public abstract class SpecialModelWrapperMixin<T> implements ItemModel {

    @Inject(method = "update",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;" +
                            "setExtents(Ljava/util/function/Supplier;)V"),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onUpdate(ItemStackRenderState output, ItemStack item, ItemModelResolver resolver,
                          ItemDisplayContext displayContext, ClientLevel level, ItemOwner owner, int seed,
                          CallbackInfo ci, ItemStackRenderState.LayerRenderState layer, T argument) {
        // Set the ItemDisplayContext in the RenderContext for use in laser blade rendering
        if (argument instanceof LBSwordRenderContext context) {
            context.displayContext = displayContext;
        }
    }
}
