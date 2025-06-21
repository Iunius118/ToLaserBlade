package com.github.iunius118.tolaserblade.mixin;

import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingMenu.class)
public abstract class SmithingMenuMixin extends ItemCombinerMenu {
    private SmithingMenuMixin(int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, Level level) {
        // This constructor will not be called
        super(MenuType.SMITHING, i, inventory, containerLevelAccess,
                ItemCombinerMenuSlotDefinition.create().withSlot(0, 0, 0, s -> false).withResultSlot(0, 0, 0).build());
    }

    @Inject(method = "shrinkStackInSlot(I)V", at = @At("HEAD"), cancellable = true)
    private void onShrinkStack(int slot, CallbackInfo ci) {
        // When a laser blade blueprint is in the template slot, it is not consumed.
        if (slot == SmithingMenu.TEMPLATE_SLOT && super.inputSlots.getItem(slot).is(ModItems.LB_BLUEPRINT)) {
            ci.cancel();
        }
    }
}
