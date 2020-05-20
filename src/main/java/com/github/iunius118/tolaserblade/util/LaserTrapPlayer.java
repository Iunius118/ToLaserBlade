package com.github.iunius118.tolaserblade.util;

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
        inventory.clear();

        // Set given item stack in main hand
        inventory.currentItem = 0;
        inventory.setInventorySlotContents(0, currentStack);

        // Apply attack damage from main hand item
        getAttributes().applyAttributeModifiers(currentStack.getAttributeModifiers(EquipmentSlotType.MAINHAND));
    }

    @Override
    public void updateHeldItem() {
    }

    @Override
    public void sendEnterCombat() {
    }

    @Override
    public void sendEndCombat() {
    }
}
