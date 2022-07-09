package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LBCasingItemColor;
import com.github.iunius118.tolaserblade.client.color.item.LBEmitterItemColor;
import com.github.iunius118.tolaserblade.client.color.item.LBMediumItemColor;
import com.github.iunius118.tolaserblade.client.color.item.LBSwordItemColor;
import com.github.iunius118.tolaserblade.client.model.LaserBladeInternalModelManager;
import com.github.iunius118.tolaserblade.client.particle.LaserTrapParticle;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeRenderType;
import com.github.iunius118.tolaserblade.client.renderer.item.model.LBSwordItemModel;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.VersionChecker.CheckResult;
import net.minecraftforge.fml.VersionChecker.Status;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;

public class ClientModEventHandler {
    @SubscribeEvent
    public static void onItemColorHandlerEvent(RegisterColorHandlersEvent.Item event) {
        event.register(new LBSwordItemColor(), ModItems.LASER_BLADE);
        event.register(new LBSwordItemColor(), ModItems.LASER_BLADE_FP);
        event.register(new LBSwordItemColor(), ModItems.LB_BRAND_NEW);
        event.register(new LBSwordItemColor(), ModItems.LB_BRAND_NEW_1);
        event.register(new LBSwordItemColor(), ModItems.LB_BRAND_NEW_2);
        event.register(new LBSwordItemColor(), ModItems.LB_BRAND_NEW_FP);
        event.register(new LBSwordItemColor(), ModItems.LB_BROKEN);
        event.register(new LBSwordItemColor(), ModItems.LB_BROKEN_FP);
        event.register(new LBEmitterItemColor(), ModItems.LB_EMITTER);
        event.register(new LBMediumItemColor(), ModItems.LB_MEDIUM);
        event.register(new LBCasingItemColor(), ModItems.LB_CASING);
        event.register(new LBCasingItemColor(), ModItems.LB_CASING_FP);
    }

    @SubscribeEvent
    public static void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
            if (!ToLaserBladeConfig.CLIENT.useInternalModel.get() && ToLaserBladeConfig.CLIENT.externalModelType.get() == 1) {
                // When using external OBJ model, add OBJ model texture to block atlas
                event.addSprite(new ResourceLocation(ToLaserBlade.MOD_ID, "item/laser_blade_obj"));
            }
        }
    }

    @SubscribeEvent
    public static void onModelBakeEvent(ModelEvent.BakingCompleted event) {
        // Reset internal model manager
        var internalModelManager = LaserBladeInternalModelManager.renewInstance();

        if (!internalModelManager.canUseInternalModel() && ToLaserBladeConfig.CLIENT.externalModelType.get() != 1) {
            // Use generated model
            return;
        }

        ModelResourceLocation laserBladeItemID = new ModelResourceLocation(getItemId(ModItems.LASER_BLADE), "inventory");
        ModelResourceLocation laserBladeFPItemID = new ModelResourceLocation(getItemId(ModItems.LASER_BLADE_FP), "inventory");
        ModelResourceLocation lBBrandNewItemID = new ModelResourceLocation(getItemId(ModItems.LB_BRAND_NEW), "inventory");
        ModelResourceLocation lBBrandNew1ItemID = new ModelResourceLocation(getItemId(ModItems.LB_BRAND_NEW_1), "inventory");
        ModelResourceLocation lBBrandNew2ItemID = new ModelResourceLocation(getItemId(ModItems.LB_BRAND_NEW_2), "inventory");
        ModelResourceLocation lBBrandNewFPItemID = new ModelResourceLocation(getItemId(ModItems.LB_BRAND_NEW_FP), "inventory");
        ModelResourceLocation lBBrokenItemID = new ModelResourceLocation(getItemId(ModItems.LB_BROKEN), "inventory");
        ModelResourceLocation lBBrokenFPItemID = new ModelResourceLocation(getItemId(ModItems.LB_BROKEN_FP), "inventory");
        LBSwordItemModel bakedModel = new LBSwordItemModel();

        bakedModel.loadModel(event);
        var models = event.getModels();
        models.put(laserBladeItemID, bakedModel);
        models.put(laserBladeFPItemID, bakedModel);
        models.put(lBBrandNewItemID, bakedModel);
        models.put(lBBrandNew1ItemID, bakedModel);
        models.put(lBBrandNew2ItemID, bakedModel);
        models.put(lBBrandNewFPItemID, bakedModel);
        models.put(lBBrokenItemID, bakedModel);
        models.put(lBBrokenFPItemID, bakedModel);
    }

    private static ResourceLocation getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    @SubscribeEvent
    public static void onRegisterShadersEvent(RegisterShadersEvent event) throws IOException {
        var resourceManager = event.getResourceManager();
        var lbUnlitShaderInstance = new ShaderInstance(resourceManager, new ResourceLocation(ToLaserBlade.MOD_ID, LaserBladeRenderType.UNLIT_SHADER_INSTANCE_NAME), DefaultVertexFormat.NEW_ENTITY);
        event.registerShader(lbUnlitShaderInstance, LaserBladeRenderType::setUnlitShaderInstance);
    }

    @SubscribeEvent
    public static void onParticleFactoryRegisterEvent(RegisterParticleProvidersEvent event) {
        event.register(ModParticleTypes.LASER_TRAP_X, new LaserTrapParticle.Provider(Direction.Axis.X));
        event.register(ModParticleTypes.LASER_TRAP_Y, new LaserTrapParticle.Provider(Direction.Axis.Y));
        event.register(ModParticleTypes.LASER_TRAP_Z, new LaserTrapParticle.Provider(Direction.Axis.Z));
    }

    public static void checkUpdate() {
        // Check update and Notify client
        CheckResult result = VersionChecker.getResult(ModList.get().getModFileById(ToLaserBlade.MOD_ID).getMods().get(0));
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
                    .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result.url())));

            Minecraft.getInstance().gui.getChat().addMessage(message);
        }
    }
}
