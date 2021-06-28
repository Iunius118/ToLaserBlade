package com.github.iunius118.tolaserblade.common.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

public class LaserTrapPlayer extends FakePlayer {
    private static final GameProfile PROFILE = new GameProfile(UUID.fromString("2BDD19A3-9616-417A-8797-EE805F5FF9E3"), "[LaserBlade]");

    private LaserTrapPlayer(ServerWorld world) {
        super(world, PROFILE);
    }

    public static LaserTrapPlayer get(ServerWorld world) {
        return new LaserTrapPlayer(world);
    }

    public void initInventory(ItemStack currentStack) {
        inventory.clearContent();

        // Set given item stack in main hand
        inventory.selected = 0;
        inventory.setItem(0, currentStack);

        // Apply attack damage from main hand item
        getAttributes().addTransientAttributeModifiers(currentStack.getAttributeModifiers(EquipmentSlotType.MAINHAND));
    }

    @Override
    public void broadcastCarriedItem() {
    }

    @Override
    public void onEnterCombat() {
    }

    @Override
    public void onLeaveCombat() {
    }
}
