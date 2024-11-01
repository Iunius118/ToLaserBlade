package com.github.iunius118.tolaserblade.client.renderer.item.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedOverrides;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class LBSwordItemModel implements IDynamicBakedModel {
    private ItemStack itemStack = ItemStack.EMPTY;
    private HumanoidArm mainArm = HumanoidArm.RIGHT;
    private boolean isBlocking = false;
    private final BakedOverrides bakedOverrides = new LBSwordBakedOverrides(this);

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
        return List.of();
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
        return Minecraft.getInstance().getItemRenderer().getModel(new ItemStack(Items.IRON_INGOT), null, null, 0).getParticleIcon(data);
    }

    @Override
    public BakedOverrides overrides() {
        return bakedOverrides;
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
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
}
