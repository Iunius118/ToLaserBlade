package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SmithingMenu;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingMenu.class)
public abstract class MixinSmithingMenu extends ItemCombinerMenu {
    private MixinSmithingMenu(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(menuType, i, inventory, containerLevelAccess);
    }

    @Inject(method = "shrinkStackInSlot(I)V", at = @At("HEAD"), cancellable = true)
    private void onShrinkStack(int slot, CallbackInfo ci) {
        // When a laser blade blueprint is in the template slot, it is not consumed.
        if (slot == SmithingMenu.TEMPLATE_SLOT && super.inputSlots.getItem(slot).is(ModItems.LB_BLUEPRINT)) {
            ci.cancel();
        }
    }
}
