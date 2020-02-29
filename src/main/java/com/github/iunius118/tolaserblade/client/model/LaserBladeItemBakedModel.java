package com.github.iunius118.tolaserblade.client.model;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.TransformationMatrix;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation") // for getQuads, getParticleTexture and ItemCameraTransforms
public class LaserBladeItemBakedModel implements IBakedModel {
    public IBakedModel bakedJSONModel;
    public boolean isBlocking = false;

    private LaserBladeItemBakedModel() {
    }

    public LaserBladeItemBakedModel(IBakedModel bakedJSONModelIn) {
        // For ModelBakeEvent
        bakedJSONModel = bakedJSONModelIn;
    }

    public LaserBladeItemBakedModel handleBlocking(boolean isBlockingIn) {
        // For rendering
        LaserBladeItemBakedModel newModel = new LaserBladeItemBakedModel();
        newModel.bakedJSONModel = this.bakedJSONModel;
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
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean func_230044_c_() {   // isGuiFlatDiffuseLighting or isSideLit
        return bakedJSONModel.func_230044_c_();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return getParticleTexture(EmptyModelData.INSTANCE);
    }

    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData modelData) {
        return Minecraft.getInstance().getItemRenderer().getItemModelMesher().getParticleIcon(Items.IRON_INGOT);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return new ItemOverrideList() {
            @Nullable
            @Override
            public IBakedModel getModelWithOverrides(IBakedModel model, ItemStack stack, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (model instanceof LaserBladeItemBakedModel) {
                    LaserBladeItemBakedModel laserBladeModel = (LaserBladeItemBakedModel) model;

                    if (ToLaserBladeConfig.CLIENT.isEnabledLaserBlade3DModel.get()) {
                        boolean isBlocking = entityIn != null
                                    && ToLaserBladeConfig.COMMON.isEnabledBlockingWithLaserBladeInServer.get()
                                    && entityIn.isHandActive();
                        return laserBladeModel.handleBlocking(isBlocking);
                    } else {
                        // When 3D models are DISABLED
                        return laserBladeModel.bakedJSONModel;
                    }
                }

                return model;
            }
        };
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        Matrix4f matrix = new Matrix4f(new float[]{-3.090862E-8F, 3.090862E-8F, -1.0F, 0.0F, 0.8838835F, 0.8838835F, 0.0F, 0.0F, 0.70710677F, -0.70710677F, -4.371139E-8F, 0.0F, -0.030330122F, -0.030330122F, 0.5F, 1.0F});
        matrix.transpose();
        TransformationMatrix trMatrix = new TransformationMatrix(matrix);
        trMatrix.getTranslation();

        Matrix4f rtMatrix2 = new Matrix4f(new Quaternion(90F, 45F, -90F, true));
        Matrix4f rtMatrix3 = new Matrix4f(new Quaternion(0.27059805F, 0.65328145F, -0.27059808F, 0.65328145F));
        Quaternion q = new Quaternion(1.57079632679F, 1.57079632679F, 1.57079632679F, false);
        Quaternion q2 = new Quaternion(90F, 45F, -90F, true);

        MatrixStack ms = new MatrixStack();
        ms.translate(0.5, 0.5, 0.5);
        ms.rotate(new Quaternion(Vector3f.ZN, 45F, true));
        //ms.scale(1F, 1.25F, 1F);
        ms.translate(0, -0.6, 0);
        ms.rotate(new Quaternion(Vector3f.YP, 90F, true));
        MatrixStack.Entry entry = ms.getLast();

        return bakedJSONModel.getItemCameraTransforms();
    }
}
