package com.github.iunius118.tolaserblade.client.renderer.item.model;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import org.joml.Vector3f;

public enum LBSwordItemTransforms {
    ITEM_TRANSFORMS(new ItemTransforms(
            new ItemTransform(new Vector3f(-8F, 0F, 0F), new Vector3f(-0.55F, 0.315F, 0.6F), new Vector3f(1.1F, 1.1F, 1.1F)),
            new ItemTransform(new Vector3f(-8F, 0F, 0F), new Vector3f(0.55F, 0.315F, 0.6F), new Vector3f(1.1F, 1.1F, 1.1F)),
            new ItemTransform(new Vector3f(20F, 0F, 0F), new Vector3f(-0.26F, 0.0025F, 0.355F), new Vector3f(0.68F, 0.85F, 0.68F)),
            new ItemTransform(new Vector3f(20F, 0F, 0F), new Vector3f(0.415F, 0.0025F, 0.355F), new Vector3f(0.68F, 0.85F, 0.68F)),
            new ItemTransform(new Vector3f(0F, 0F, 0F), new Vector3f(0.5F, 0.6F, 0.5F), new Vector3f(1F, 1F, 1F)),
            new ItemTransform(new Vector3f(90F, 45F, -90F), new Vector3f(0.16F, -0.475F, 0F), new Vector3f(0.9F, 0.9F, 0.9F)),
            new ItemTransform(new Vector3f(90F, -45F, 90F), new Vector3f(-0.25F, -0.1F, 0.25F), new Vector3f(0.5F, 0.5F, 0.5F)),
            new ItemTransform(new Vector3f(90F, -45F, 90F), new Vector3f(-0.175F, -0.485F, 0.44F), new Vector3f(0.95F, 0.95F, 0.95F)),
            ImmutableMap.of()
    )),
    BLOCKING_RIGHT_ITEM_TRANSFORMS(new ItemTransforms(
            ITEM_TRANSFORMS.get().thirdPersonLeftHand,
            ITEM_TRANSFORMS.get().thirdPersonRightHand,
            new ItemTransform(new Vector3f(0F, 0F, 60F), new Vector3f(-0.4F, -0.15F, 0.6F), new Vector3f(0.68F, 0.85F, 0.68F)),
            new ItemTransform(new Vector3f(0F, 0F, 60F), new Vector3f(-0.05F, 0.4F, 0.5F), new Vector3f(0.68F, 0.85F, 0.68F)),
            ITEM_TRANSFORMS.get().head,
            ITEM_TRANSFORMS.get().gui,
            ITEM_TRANSFORMS.get().ground,
            ITEM_TRANSFORMS.get().fixed,
            ImmutableMap.of()
    )),
    BLOCKING_LEFT_ITEM_TRANSFORMS(new ItemTransforms(
            ITEM_TRANSFORMS.get().thirdPersonLeftHand,
            ITEM_TRANSFORMS.get().thirdPersonRightHand,
            new ItemTransform(new Vector3f(0F, 0F, 60F), new Vector3f(-0.4F, -0.185F, 0.5F), new Vector3f(0.68F, 0.85F, 0.68F)),
            new ItemTransform(new Vector3f(0F, 0F, 60F), new Vector3f(-0.05F, 0.43F, 0.6F), new Vector3f(0.68F, 0.85F, 0.68F)),
            ITEM_TRANSFORMS.get().head,
            ITEM_TRANSFORMS.get().gui,
            ITEM_TRANSFORMS.get().ground,
            ITEM_TRANSFORMS.get().fixed,
            ImmutableMap.of()
    )),
    ;

    private final ItemTransforms itemTransforms;

    LBSwordItemTransforms(ItemTransforms t) {
        itemTransforms = t;
    }

    public ItemTransforms get() {
        return itemTransforms;
    }
}
