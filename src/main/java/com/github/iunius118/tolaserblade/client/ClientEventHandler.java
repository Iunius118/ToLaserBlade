package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.client.model.LaserBladeInternalModelManager;
import com.github.iunius118.tolaserblade.client.model.LaserBladeItemBakedModel;
import com.github.iunius118.tolaserblade.item.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.VersionChecker.CheckResult;
import net.minecraftforge.fml.VersionChecker.Status;

public class ClientEventHandler {
    @SubscribeEvent
    public void onItemColorHandlerEvent(ColorHandlerEvent.Item event) {
        event.getItemColors().register(new LaserBladeItem.ColorHandler(), ModItems.LASER_BLADE);
        event.getItemColors().register(new LaserBladeItem.ColorHandler(), ModItems.LASER_BLADE_FP);
        event.getItemColors().register(new LaserBladeItem.ColorHandler(), ModItems.LB_BRAND_NEW);
        event.getItemColors().register(new LaserBladeItem.ColorHandler(), ModItems.LB_BRAND_NEW_1);
        event.getItemColors().register(new LaserBladeItem.ColorHandler(), ModItems.LB_BRAND_NEW_2);
        event.getItemColors().register(new LaserBladeItem.ColorHandler(), ModItems.LB_BRAND_NEW_FP);
        event.getItemColors().register(new LaserBladeItem.ColorHandler(), ModItems.LB_BROKEN);
        event.getItemColors().register(new LaserBladeItem.ColorHandler(), ModItems.LB_BROKEN_FP);
        event.getItemColors().register(new LBEmitterItem.ColorHandler(), ModItems.LB_EMITTER);
        event.getItemColors().register(new LBMediumItem.ColorHandler(), ModItems.LB_MEDIUM);
        event.getItemColors().register(new LBCasingItem.ColorHandler(), ModItems.LB_CASING);
        event.getItemColors().register(new LBCasingItem.ColorHandler(), ModItems.LB_CASING_FP);
    }

    @SubscribeEvent
    public void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        if (event.getMap().getTextureLocation().equals(PlayerContainer.LOCATION_BLOCKS_TEXTURE)) {
            if (!ToLaserBladeConfig.CLIENT.useInternalModel.get() && ToLaserBladeConfig.CLIENT.externalModelType.get() == 1) {
                // When using external OBJ model, add OBJ model texture to block atlas
                event.addSprite(new ResourceLocation(ToLaserBlade.MOD_ID, "item/laser_blade_obj"));
            }
        }
    }

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event) {
        LaserBladeInternalModelManager.renewInstance(); // Reset model manager

        if (!ToLaserBladeConfig.CLIENT.useInternalModel.get() && ToLaserBladeConfig.CLIENT.externalModelType.get() != 1) {
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
        LaserBladeItemBakedModel bakedModel = new LaserBladeItemBakedModel();

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

    public static void checkUpdate() {
        // Check update and Notify client
        CheckResult result = VersionChecker.getResult(ModList.get().getModFileById(ToLaserBlade.MOD_ID).getMods().get(0));
        Status status = result.status;

        if (status == Status.PENDING || result.target == null) {
            // Failed to get update information
            return;
        }

        if (status == Status.OUTDATED || status == Status.BETA_OUTDATED) {
            ITextComponent modNameHighlighted = new StringTextComponent(ToLaserBlade.MOD_NAME).mergeStyle(TextFormatting.YELLOW);

            ITextComponent newVersionHighlighted = new StringTextComponent(result.target.toString()).mergeStyle(TextFormatting.YELLOW);

            ITextComponent message = new TranslationTextComponent("tolaserblade.update.newVersion", modNameHighlighted).appendString(": ")
                    .append(newVersionHighlighted)
                    .modifyStyle(style -> style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result.url)));

            Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(message);
        }
    }
}
