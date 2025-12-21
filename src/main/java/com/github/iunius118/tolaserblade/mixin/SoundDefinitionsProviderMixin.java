package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.resources.Identifier;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SoundDefinitionsProvider.class, remap = false)
public abstract class SoundDefinitionsProviderMixin {
    @Shadow @Final private String modId;

    @Inject(method = "validateSound(Ljava/lang/String;Lnet/minecraft/resources/Identifier;)Z", at = @At("HEAD"), cancellable = true)
    private void onValidateSound(String soundName, Identifier name, CallbackInfoReturnable<Boolean> cir) {
        // Only for providers of this mod
        if (ToLaserBlade.MOD_ID.equals(modId)) {
            // Avoid checking for the existence of sound files
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
