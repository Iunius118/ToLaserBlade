package com.github.iunius118.tolaserblade.common.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

// Temporary FakePlayer until Forge's FakePlayer is rewritten
public class FakePlayer extends ServerPlayer {
    public FakePlayer(ServerLevel level, GameProfile name, ClientInformation info) {
        super(level.getServer(), level, name, info);
    }

    @Override public void displayClientMessage(Component chatComponent, boolean actionBar) { }
    @Override public void awardStat(Stat stat, int amount) { }
    @Override public void resetStat(Stat<?> p_9024_) { }
    @Override public boolean isInvulnerableTo(DamageSource source) { return true; }
    @Override public void die(DamageSource source) {}
    @Override public void tick() {}
    @Override public void updateOptions(ClientInformation information) { }
    @Override @Nullable public MinecraftServer getServer() { return ServerLifecycleHooks.getCurrentServer(); }
}
