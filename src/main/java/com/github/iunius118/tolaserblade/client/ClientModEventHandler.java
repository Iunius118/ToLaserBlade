package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeTintSource;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.client.particle.LaserTrapParticle;
import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.properties.Blocking;
import com.github.iunius118.tolaserblade.client.renderer.item.properties.UsingOriginalModel;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.VersionChecker.CheckResult;
import net.minecraftforge.fml.VersionChecker.Status;
import net.minecraftforge.registries.ForgeRegistries;

import java.net.URI;
import java.net.URL;

public class ClientModEventHandler {
    public static void initClient() {
        // Register item model properties
        ConditionalItemModelProperties.ID_MAPPER.put(ToLaserBlade.makeId("using_original_model"), UsingOriginalModel.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(ToLaserBlade.makeId("blocking"), Blocking.MAP_CODEC);

        // Register tint sources
        ItemTintSources.ID_MAPPER.put(ToLaserBlade.makeId("laser_blade"), LaserBladeTintSource.MAP_CODEC);

        // Register special model renderers
        SpecialModelRenderers.ID_MAPPER.put(ToLaserBlade.makeId("laser_blade"), LBSwordSpecialRenderer.Unbaked.MAP_CODEC);
    }

    @SubscribeEvent
    public static void onModifyBakingResultEvent(final ModelEvent.ModifyBakingResult event) {
        // Reset internal model manager
        LaserBladeModelManager.getInstance().reload();
    }

    private static ResourceLocation getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    @SubscribeEvent
    public static void onBakingCompletedEvent(final ModelEvent.BakingCompleted event) {
        LaserBladeModelManager.getInstance().logLoadedModelCount();
    }

    @SubscribeEvent
    public static void onParticleFactoryRegisterEvent(final RegisterParticleProvidersEvent event) {
        event.registerSpecial(ModParticleTypes.LASER_TRAP_X, new LaserTrapParticle.Provider(Direction.Axis.X));
        event.registerSpecial(ModParticleTypes.LASER_TRAP_Y, new LaserTrapParticle.Provider(Direction.Axis.Y));
        event.registerSpecial(ModParticleTypes.LASER_TRAP_Z, new LaserTrapParticle.Provider(Direction.Axis.Z));
    }

    public static void checkUpdate() {
        // Check update and Notify client
        CheckResult result = VersionChecker.getResult(ModList.get().getModFileById(ToLaserBlade.MOD_ID).getMods().getFirst());

        try {
            URI uri = new URL(result.url()).toURI();
            Status status = result.status();

            if (status == Status.PENDING || result.target() == null) {
                // Failed to get update information
                return;
            }

            if (status == Status.OUTDATED || status == Status.BETA_OUTDATED) {
                MutableComponent modNameHighlighted = Component.literal(ToLaserBlade.MOD_NAME).withStyle(ChatFormatting.YELLOW);
                MutableComponent newVersionHighlighted = Component.literal(result.target().toString()).withStyle(ChatFormatting.YELLOW);
                MutableComponent message = Component.translatable("tolaserblade.update.newVersion", modNameHighlighted).append(": ")
                        .append(newVersionHighlighted)
                        .withStyle(style -> style.withClickEvent(new ClickEvent.OpenUrl(uri)));
                Minecraft.getInstance().gui.getChat().addMessage(message);
            }
        } catch (Exception ignored) {
        }
    }
}
