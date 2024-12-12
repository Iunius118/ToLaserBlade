package com.github.iunius118.tolaserblade.client;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.color.item.LaserBladeTintSource;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelManager;
import com.github.iunius118.tolaserblade.client.particle.LaserTrapParticle;
import com.github.iunius118.tolaserblade.client.renderer.item.LBBrandNewItemRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.LBBrokenItemRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordItemRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.model.LBSwordItemModel;
import com.github.iunius118.tolaserblade.client.renderer.item.properties.UsingOriginalModel;
import com.github.iunius118.tolaserblade.core.particle.ModParticleTypes;
import com.github.iunius118.tolaserblade.world.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
import net.neoforged.fml.VersionChecker.Status;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterConditionalItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public class ClientModEventHandler {
    @SubscribeEvent
    public static void onRegisterItemTintSourceEvent(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(ToLaserBlade.makeId("laser_blade"), LaserBladeTintSource.MAP_CODEC);
    }

    @SubscribeEvent
    public static void onRegisterConditionalItemModelPropertyEvent(RegisterConditionalItemModelPropertyEvent event) {
        event.register(ToLaserBlade.makeId("using_original_model"), UsingOriginalModel.MAP_CODEC);
    }

    @SubscribeEvent
    public static void onModifyBakingResultEvent(ModelEvent.ModifyBakingResult event) {
        // Reset internal model manager
        var modelManager = LaserBladeModelManager.getInstance();
        modelManager.reload();

        if (!modelManager.canUseOriginalModelType()) {
            // Use vanilla-like model
            return;
        }

        ModelResourceLocation laserBladeItemID = ModelResourceLocation.inventory(getItemId(ModItems.LASER_BLADE));
        ModelResourceLocation laserBladeFPItemID = ModelResourceLocation.inventory(getItemId(ModItems.LASER_BLADE_FP));
        ModelResourceLocation lBBrandNewItemID = ModelResourceLocation.inventory(getItemId(ModItems.LB_BRAND_NEW));
        ModelResourceLocation lBBrandNew1ItemID = ModelResourceLocation.inventory(getItemId(ModItems.LB_BRAND_NEW_1));
        ModelResourceLocation lBBrandNew2ItemID = ModelResourceLocation.inventory(getItemId(ModItems.LB_BRAND_NEW_2));
        ModelResourceLocation lBBrandNewFPItemID = ModelResourceLocation.inventory(getItemId(ModItems.LB_BRAND_NEW_FP));
        ModelResourceLocation lBBrokenItemID = ModelResourceLocation.inventory(getItemId(ModItems.LB_BROKEN));
        ModelResourceLocation lBBrokenFPItemID = ModelResourceLocation.inventory(getItemId(ModItems.LB_BROKEN_FP));
        LBSwordItemModel bakedModel = new LBSwordItemModel();

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

    @SubscribeEvent
    public static void onBakingCompletedEvent(ModelEvent.BakingCompleted event) {
        LaserBladeModelManager.getInstance().logLoadedModelCount();
    }

    @SubscribeEvent
    public static void onRegisterClientExtensionsEvent(RegisterClientExtensionsEvent event) {
        event.registerItem(LBSwordItemRenderer.CLIENT_ITEM_EXTENSIONS, ModItems.LASER_BLADE, ModItems.LASER_BLADE_FP);
        event.registerItem(LBBrandNewItemRenderer.CLIENT_ITEM_EXTENSIONS, ModItems.LB_BRAND_NEW, ModItems.LB_BRAND_NEW_1, ModItems.LB_BRAND_NEW_2, ModItems.LB_BRAND_NEW_FP);
        event.registerItem(LBBrokenItemRenderer.CLIENT_ITEM_EXTENSIONS, ModItems.LB_BROKEN, ModItems.LB_BROKEN_FP);
    }

    @SubscribeEvent
    public static void onParticleFactoryRegisterEvent(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ModParticleTypes.LASER_TRAP_X, new LaserTrapParticle.Provider(Direction.Axis.X));
        event.registerSpecial(ModParticleTypes.LASER_TRAP_Y, new LaserTrapParticle.Provider(Direction.Axis.Y));
        event.registerSpecial(ModParticleTypes.LASER_TRAP_Z, new LaserTrapParticle.Provider(Direction.Axis.Z));
    }

    public static void checkUpdate() {
        // Check update and Notify client
        var result = VersionChecker.getResult(ModList.get().getModFileById(ToLaserBlade.MOD_ID).getMods().get(0));
        var status = result.status();

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

    private static ResourceLocation getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
