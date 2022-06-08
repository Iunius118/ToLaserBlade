package com.github.iunius118.tolaserblade.client.renderer.item.model;

import com.github.iunius118.tolaserblade.api.client.model.LaserBladeModel;
import com.github.iunius118.tolaserblade.client.model.LaserBladeInternalModelManager;
import com.github.iunius118.tolaserblade.client.model.LaserBladeModelHolder;
import com.github.iunius118.tolaserblade.client.model.laserblade.LaserBladeOBJModel;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation") // for getQuads, getParticleTexture
public class LBSwordItemModel implements BakedModel {
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

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource randomSource, @Nonnull IModelData modelData) {
        return Collections.emptyList();
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource randomSource) {
        return getQuads(state, side, randomSource, EmptyModelData.INSTANCE);
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
        return getParticleIcon(EmptyModelData.INSTANCE);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@Nonnull IModelData data) {
        return Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(Items.IRON_INGOT).getParticleIcon(EmptyModelData.INSTANCE);
    }

    private static final ItemOverrides ITEM_OVERRIDES = new LBSwordItemOverrides();

    @Override
    public ItemOverrides getOverrides() {
        return ITEM_OVERRIDES;
    }

    @Override
    public ItemTransforms getTransforms() {
        if (!isBlocking) return LBSwordItemTransforms.ITEM_TRANSFORMS.get();

        if (mainArm == HumanoidArm.RIGHT) {
            return LBSwordItemTransforms.BLOCKING_RIGHT_ITEM_TRANSFORMS.get();
        } else {
            return LBSwordItemTransforms.BLOCKING_LEFT_ITEM_TRANSFORMS.get();
        }
    }

    public void loadModel(ModelBakeEvent event) {
        LaserBladeModel model;
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
