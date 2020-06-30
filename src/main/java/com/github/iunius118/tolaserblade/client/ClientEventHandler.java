package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
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
        event.getItemColors().register(new LaserBladeItem.ColorHandler(), ModItems.LB_BROKEN);
        event.getItemColors().register(new LBEmitterItem.ColorHandler(), ModItems.LB_EMITTER);
        event.getItemColors().register(new LBMediumItem.ColorHandler(), ModItems.LB_MEDIUM);
        event.getItemColors().register(new LBCasingItem.ColorHandler(), ModItems.LB_CASING);
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
        if (!ToLaserBladeConfig.CLIENT.useInternalModel.get() && ToLaserBladeConfig.CLIENT.externalModelType.get() != 1) {
            return; // Use generated model
        }

        ModelResourceLocation laserBladeItemID = new ModelResourceLocation(ModItems.LASER_BLADE.getRegistryName(), "inventory");
        ModelResourceLocation lBBrokenItemID = new ModelResourceLocation(ModItems.LB_BROKEN.getRegistryName(), "inventory");
        LaserBladeItemBakedModel bakedModel = new LaserBladeItemBakedModel();

        bakedModel.loadModel(event);
        event.getModelRegistry().put(laserBladeItemID, bakedModel);
        event.getModelRegistry().put(lBBrokenItemID, bakedModel);
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
            ITextComponent modNameHighlighted = new StringTextComponent(ToLaserBlade.MOD_NAME);
            modNameHighlighted.getStyle().func_240721_b_(TextFormatting.YELLOW);    // TODO: func_240718_a_ = setFormat?

            ITextComponent newVersionHighlighted = new StringTextComponent(result.target.toString());
            newVersionHighlighted.getStyle().func_240721_b_(TextFormatting.YELLOW);

            ITextComponent message = new TranslationTextComponent("tolaserblade.update.newVersion", modNameHighlighted).func_240702_b_(": ")    // TODO: func_240702_b_ = appendText
                    .func_230529_a_(newVersionHighlighted); // TODO: func_230529_a_ = appendSibling
            message.getStyle().func_240715_a_(new ClickEvent(ClickEvent.Action.OPEN_URL, result.url));  // TODO: func_240715_a_ = setClickEvent

            Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(message);
        }
    }
}
