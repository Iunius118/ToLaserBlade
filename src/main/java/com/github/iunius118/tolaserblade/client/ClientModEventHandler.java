package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeTintSource;
import com.github.iunius118.tolaserblade.client.extensions.LBSwordItemExtensions;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.client.particle.LaserTrapParticle;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladePipelines;
import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordSpecialRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.properties.Blocking;
import com.github.iunius118.tolaserblade.client.renderer.item.properties.UsingOriginalModel;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.VersionChecker;
import net.neoforged.fml.VersionChecker.CheckResult;
import net.neoforged.fml.VersionChecker.Status;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import java.net.URI;
import java.net.URL;

public class ClientModEventHandler {
    @SubscribeEvent
    public static void onRegisterItemTintSourceEvent(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(ToLaserBlade.makeId("laser_blade"), LaserBladeTintSource.MAP_CODEC);
    }

    @SubscribeEvent
    public static void onRegisterConditionalItemModelPropertyEvent(RegisterConditionalItemModelPropertyEvent event) {
        event.register(ToLaserBlade.makeId("using_original_model"), UsingOriginalModel.MAP_CODEC);
        event.register(ToLaserBlade.makeId("blocking"), Blocking.MAP_CODEC);
    }

    @SubscribeEvent
    public static void onModifyBakingResultEvent(ModelEvent.ModifyBakingResult event) {
        // Reset internal model manager
        LaserBladeModelManager.getInstance().reload();
    }

    @SubscribeEvent
    public static void onBakingCompletedEvent(ModelEvent.BakingCompleted event) {
        LaserBladeModelManager.getInstance().logLoadedModelCount();
    }

    @SubscribeEvent
    public static void onRegisterSpecialModelRendererEvent(RegisterSpecialModelRendererEvent event) {
        event.register(ToLaserBlade.makeId("laser_blade"), LBSwordSpecialRenderer.Unbaked.MAP_CODEC);
    }

    @SubscribeEvent
    public static void onRegisterClientExtensionsEvent(RegisterClientExtensionsEvent event) {
        event.registerItem(new LBSwordItemExtensions(), ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP);
    }

    @SubscribeEvent
    public static void onParticleFactoryRegisterEvent(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ModParticleTypes.LASER_TRAP_X, new LaserTrapParticle.Provider(Direction.Axis.X));
        event.registerSpecial(ModParticleTypes.LASER_TRAP_Y, new LaserTrapParticle.Provider(Direction.Axis.Y));
        event.registerSpecial(ModParticleTypes.LASER_TRAP_Z, new LaserTrapParticle.Provider(Direction.Axis.Z));
    }

    @SubscribeEvent
    public static void onRegisterRenderPipelinesEvent(RegisterRenderPipelinesEvent event) {
        LaserBladePipelines.onRegisterRenderPipelinesEvent(event);
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

    private static ResourceLocation getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
