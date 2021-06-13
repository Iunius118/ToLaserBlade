package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.api.client.model.ILaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeOBJModel;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation") // for getQuads, getParticleTexture and ItemCameraTransforms
public class LaserBladeItemBakedModel implements IBakedModel {
    ItemStack itemStack = ItemStack.EMPTY;
    HandSide primaryHand = HandSide.RIGHT;
    public boolean isBlocking = false;

    private LaserBladeItemBakedModel handleItemOverride(ItemStack itemStackIn, HandSide primaryHandIn, boolean isBlockingIn) {
        // For rendering
        LaserBladeItemBakedModel newModel = new LaserBladeItemBakedModel();
        newModel.itemStack = itemStackIn;
        newModel.primaryHand = primaryHandIn;
        newModel.isBlocking = isBlockingIn;
        return newModel;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData modelData) {
        return Collections.emptyList();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand) {
        return getQuads(state, side, rand, EmptyModelData.INSTANCE);
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
        return false;   // isGuiFlatDiffuseLighting or isShadedInGui
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return getParticleTexture(EmptyModelData.INSTANCE);
    }

    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData modelData) {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getParticleIcon(Items.IRON_INGOT);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return new ItemOverrideList() {
            @Override
            public IBakedModel resolve(IBakedModel model, ItemStack stack, @Nullable ClientWorld worldIn, @Nullable LivingEntity entityIn) {
                if (model instanceof LaserBladeItemBakedModel) {
                    LaserBladeItemBakedModel laserBladeModel = (LaserBladeItemBakedModel) model;

                    boolean isBlocking = false;
                    HandSide handSide = HandSide.RIGHT;

                    if (entityIn != null) {
                        isBlocking = ToLaserBladeConfig.SERVER.isEnabledBlockingWithLaserBlade.get() && entityIn.isUsingItem();
                        handSide = entityIn.getMainArm();
                    }

                    return laserBladeModel.handleItemOverride(stack, handSide, isBlocking);
                }

                return model;
            }
        };
    }

    @Override
    public IBakedModel handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat) {
        return ForgeHooksClient.handlePerspective(this, cameraTransformType, mat);
    }

    private static final ItemCameraTransforms ITEM_TRANSFORMS = new ItemCameraTransforms(
            new ItemTransformVec3f(new Vector3f(-8F, 0F, 0F), new Vector3f(-0.55F, 0.315F, 0.6F), new Vector3f(1.1F, 1.1F, 1.1F)),
            new ItemTransformVec3f(new Vector3f(-8F, 0F, 0F), new Vector3f(0.55F, 0.315F, 0.6F), new Vector3f(1.1F, 1.1F, 1.1F)),
            new ItemTransformVec3f(new Vector3f(20F, 0F, 0F), new Vector3f(-0.26F, 0.0025F, 0.355F), new Vector3f(0.68F, 0.85F, 0.68F)),
            new ItemTransformVec3f(new Vector3f(20F, 0F, 0F), new Vector3f(0.415F, 0.0025F, 0.355F), new Vector3f(0.68F, 0.85F, 0.68F)),
            new ItemTransformVec3f(new Vector3f(0F, 0F, 0F), new Vector3f(0.5F, 0.6F, 0.5F), new Vector3f(1F, 1F, 1F)),
            new ItemTransformVec3f(new Vector3f(90F, 45F, -90F), new Vector3f(0.16F, -0.475F, 0F), new Vector3f(0.9F, 0.9F, 0.9F)),
            new ItemTransformVec3f(new Vector3f(90F, -45F, 90F), new Vector3f(-0.25F, -0.1F, 0.25F), new Vector3f(0.5F, 0.5F, 0.5F)),
            new ItemTransformVec3f(new Vector3f(90F, -45F, 90F), new Vector3f(-0.175F, -0.485F, 0.44F), new Vector3f(0.95F, 0.95F, 0.95F)));
    private static final ItemCameraTransforms BLOCKING_RIGHTY_ITEM_TRANSFORMS = new ItemCameraTransforms(
            ITEM_TRANSFORMS.thirdPersonLeftHand,
            ITEM_TRANSFORMS.thirdPersonRightHand,
            new ItemTransformVec3f(new Vector3f(0F, 0F, 60F), new Vector3f(-0.4F, -0.15F, 0.6F), new Vector3f(0.68F, 0.85F, 0.68F)),
            new ItemTransformVec3f(new Vector3f(0F, 0F, 60F), new Vector3f(-0.05F, 0.4F, 0.5F), new Vector3f(0.68F, 0.85F, 0.68F)),
            ITEM_TRANSFORMS.head,
            ITEM_TRANSFORMS.gui,
            ITEM_TRANSFORMS.ground,
            ITEM_TRANSFORMS.fixed);
    private static final ItemCameraTransforms BLOCKING_LEFTY_ITEM_TRANSFORMS = new ItemCameraTransforms(
            ITEM_TRANSFORMS.thirdPersonLeftHand,
            ITEM_TRANSFORMS.thirdPersonRightHand,
            new ItemTransformVec3f(new Vector3f(0F, 0F, 60F), new Vector3f(-0.4F, -0.185F, 0.5F), new Vector3f(0.68F, 0.85F, 0.68F)),
            new ItemTransformVec3f(new Vector3f(0F, 0F, 60F), new Vector3f(-0.05F, 0.43F, 0.6F), new Vector3f(0.68F, 0.85F, 0.68F)),
            ITEM_TRANSFORMS.head,
            ITEM_TRANSFORMS.gui,
            ITEM_TRANSFORMS.ground,
            ITEM_TRANSFORMS.fixed);

    @Override
    public ItemCameraTransforms getTransforms() {
        if (isBlocking) {
            if (Minecraft.getInstance().options.mainHand == HandSide.RIGHT) {
                return BLOCKING_RIGHTY_ITEM_TRANSFORMS;

            } else {
                return BLOCKING_LEFTY_ITEM_TRANSFORMS;
            }

        } else {
            return ITEM_TRANSFORMS;
        }
    }

    public void loadModel(ModelBakeEvent event) {
        ILaserBladeModel model;
        LaserBladeInternalModelManager internalModelManager = LaserBladeInternalModelManager.getInstance();

        if (internalModelManager.canUseInternalModel()) {
            // Use internal model
            model = internalModelManager.getModel();
        } else {
            // Use external model
            // If ToLaserBladeConfig.CLIENT.externalModelType.get() == 1
            LaserBladeOBJModel objModel = new LaserBladeOBJModel();
            objModel.loadLaserBladeOBJModel(event.getModelLoader());
            model = objModel;
        }

        LaserBladeModelHolder.setModel(model);
    }
}
