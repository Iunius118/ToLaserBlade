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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.VersionChecker.CheckResult;
import net.minecraftforge.fml.VersionChecker.Status;

import java.io.IOException;

public class ClientModEventHandler {
    @SubscribeEvent
    public static void onItemColorHandlerEvent(ColorHandlerEvent.Item event) {
        event.getItemColors().register(new LBSwordItemColor(), ModItems.LASER_BLADE);
        event.getItemColors().register(new LBSwordItemColor(), ModItems.LASER_BLADE_FP);
        event.getItemColors().register(new LBSwordItemColor(), ModItems.LB_BRAND_NEW);
        event.getItemColors().register(new LBSwordItemColor(), ModItems.LB_BRAND_NEW_1);
        event.getItemColors().register(new LBSwordItemColor(), ModItems.LB_BRAND_NEW_2);
        event.getItemColors().register(new LBSwordItemColor(), ModItems.LB_BRAND_NEW_FP);
        event.getItemColors().register(new LBSwordItemColor(), ModItems.LB_BROKEN);
        event.getItemColors().register(new LBSwordItemColor(), ModItems.LB_BROKEN_FP);
        event.getItemColors().register(new LBEmitterItemColor(), ModItems.LB_EMITTER);
        event.getItemColors().register(new LBMediumItemColor(), ModItems.LB_MEDIUM);
        event.getItemColors().register(new LBCasingItemColor(), ModItems.LB_CASING);
        event.getItemColors().register(new LBCasingItemColor(), ModItems.LB_CASING_FP);
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
    public static void onModelBakeEvent(ModelBakeEvent event) {
        LaserBladeInternalModelManager internalModelManager = LaserBladeInternalModelManager.renewInstance();   // Reset internal model manager

        if (!internalModelManager.canUseInternalModel() && ToLaserBladeConfig.CLIENT.externalModelType.get() != 1) {
            return; // Use generated model
        }

        ModelResourceLocation laserBladeItemID = new ModelResourceLocation(ModItems.LASER_BLADE.getRegistryName(), "inventory");
        ModelResourceLocation laserBladeFPItemID = new ModelResourceLocation(ModItems.LASER_BLADE_FP.getRegistryName(), "inventory");
        ModelResourceLocation lBBrandNewItemID = new ModelResourceLocation(ModItems.LB_BRAND_NEW.getRegistryName(), "inventory");
        ModelResourceLocation lBBrandNew1ItemID = new ModelResourceLocation(ModItems.LB_BRAND_NEW_1.getRegistryName(), "inventory");
        ModelResourceLocation lBBrandNew2ItemID = new ModelResourceLocation(ModItems.LB_BRAND_NEW_2.getRegistryName(), "inventory");
        ModelResourceLocation lBBrandNewFPItemID = new ModelResourceLocation(ModItems.LB_BRAND_NEW_FP.getRegistryName(), "inventory");
        ModelResourceLocation lBBrokenItemID = new ModelResourceLocation(ModItems.LB_BROKEN.getRegistryName(), "inventory");
        ModelResourceLocation lBBrokenFPItemID = new ModelResourceLocation(ModItems.LB_BROKEN_FP.getRegistryName(), "inventory");
        LBSwordItemModel bakedModel = new LBSwordItemModel();

        bakedModel.loadModel(event);
        event.getModelRegistry().put(laserBladeItemID, bakedModel);
        event.getModelRegistry().put(laserBladeFPItemID, bakedModel);
        event.getModelRegistry().put(lBBrandNewItemID, bakedModel);
        event.getModelRegistry().put(lBBrandNew1ItemID, bakedModel);
        event.getModelRegistry().put(lBBrandNew2ItemID, bakedModel);
        event.getModelRegistry().put(lBBrandNewFPItemID, bakedModel);
        event.getModelRegistry().put(lBBrokenItemID, bakedModel);
        event.getModelRegistry().put(lBBrokenFPItemID, bakedModel);
    }

    @SubscribeEvent
    public static void onRegisterShadersEvent(RegisterShadersEvent event) throws IOException {
        var resourceManager = event.getResourceManager();
        var lbUnlitShaderInstance = new ShaderInstance(resourceManager, new ResourceLocation(ToLaserBlade.MOD_ID, LaserBladeRenderType.UNLIT_SHADER_INSTANCE_NAME), DefaultVertexFormat.NEW_ENTITY);
        event.registerShader(lbUnlitShaderInstance, LaserBladeRenderType::setUnlitShaderInstance);
    }

    @SubscribeEvent
    public static void onParticleFactoryRegisterEvent(ParticleFactoryRegisterEvent event) {
        var particleEngine = Minecraft.getInstance().particleEngine;
        particleEngine.register(ModParticleTypes.LASER_TRAP_X, new LaserTrapParticle.Provider(Direction.Axis.X));
        particleEngine.register(ModParticleTypes.LASER_TRAP_Y, new LaserTrapParticle.Provider(Direction.Axis.Y));
        particleEngine.register(ModParticleTypes.LASER_TRAP_Z, new LaserTrapParticle.Provider(Direction.Axis.Z));
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
            Component modNameHighlighted = new TextComponent(ToLaserBlade.MOD_NAME).withStyle(ChatFormatting.YELLOW);

            Component newVersionHighlighted = new TextComponent(result.target().toString()).withStyle(ChatFormatting.YELLOW);

            Component message = new TranslatableComponent("tolaserblade.update.newVersion", modNameHighlighted).append(": ")
                    .append(newVersionHighlighted)
                    .withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result.url())));

            Minecraft.getInstance().gui.getChat().addMessage(message);
        }
    }
}
