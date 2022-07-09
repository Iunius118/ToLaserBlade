package com.github.iunius118.tolaserblade.client.renderer.item.model;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.LaserBladeInternalModelManager;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelHolder;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeOBJModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class LBSwordItemModel implements IDynamicBakedModel {
    ItemStack itemStack = ItemStack.EMPTY;
    public HumanoidArm mainArm = HumanoidArm.RIGHT;
    public boolean isBlocking = false;

    public LBSwordItemModel handleItemOverride(ItemStack itemStackIn, HumanoidArm mainArm, boolean isBlockingIn) {
        // For rendering
        LBSwordItemModel newModel = new LBSwordItemModel();
        newModel.itemStack = itemStackIn;
        newModel.mainArm = mainArm;
        newModel.isBlocking = isBlockingIn;
        return newModel;
    }

    @Override
    @NotNull
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return getParticleIcon(ModelData.EMPTY);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@NotNull ModelData data) {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(Items.IRON_INGOT).getParticleIcon(data);
    }

    private static final ItemOverrides ITEM_OVERRIDES = new LBSwordItemOverrides();

    @Override
    public ItemOverrides getOverrides() {
        return ITEM_OVERRIDES;
    }

    @Override
    public BakedModel applyTransform(ItemTransforms.TransformType transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        getItemTransforms().getTransform(transformType).apply(applyLeftHandTransform, poseStack);
        return this;
    }

    private ItemTransforms getItemTransforms() {
        if (!isBlocking)
            return LBSwordItemTransforms.ITEM_TRANSFORMS.get();

        if (mainArm == HumanoidArm.RIGHT) {
            return LBSwordItemTransforms.BLOCKING_RIGHT_ITEM_TRANSFORMS.get();
        } else {
            return LBSwordItemTransforms.BLOCKING_LEFT_ITEM_TRANSFORMS.get();
        }
    }

    public void loadModel(ModelEvent.BakingCompleted event) {
        LaserBladeModel model;
        var internalModelManager = LaserBladeInternalModelManager.getInstance();

        if (internalModelManager.canUseInternalModel()) {
            // Use internal model
            model = internalModelManager.getModel();
        } else {
            // Use external model
            // If ToLaserBladeConfig.CLIENT.externalModelType.get() == 1
            LaserBladeOBJModel objModel = new LaserBladeOBJModel();
            objModel.loadLaserBladeOBJModel(event.getModelBakery());
            model = objModel;
        }

        LaserBladeModelHolder.setModel(model);
    }
}
