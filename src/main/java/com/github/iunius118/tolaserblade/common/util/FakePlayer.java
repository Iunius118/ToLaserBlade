package com.github.iunius118.tolaserblade.common.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalInt;

// Temporary FakePlayer until Forge's FakePlayer is rewritten
public class FakePlayer extends ServerPlayer {
    public FakePlayer(ServerLevel level, GameProfile name) {
        super(level.getServer(), level, name, ClientInformation.createDefault());
    }

    @Override public void displayClientMessage(Component chatComponent, boolean actionBar) { }
    @Override public void awardStat(Stat stat, int amount) { }
    @Override public void resetStat(Stat<?> stat) { }
    @Override public boolean isInvulnerableTo(DamageSource source) { return true; }
    @Override public void die(DamageSource source) {}
    @Override public void tick() {}
    @Override public void updateOptions(ClientInformation information) { }
    @Override public boolean startRiding(Entity entity, boolean b) { return false; }
    @Override public void startSleeping(BlockPos pos) { }
    @Override public OptionalInt openMenu(@Nullable MenuProvider p_9033_) { return OptionalInt.empty(); }
}
