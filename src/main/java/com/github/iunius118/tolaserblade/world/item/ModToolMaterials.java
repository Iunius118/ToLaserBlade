package com.github.iunius118.tolaserblade.world.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class ModToolMaterials {
    private static final int LB_MAX_USES = 32000;
    private static final float LB_DEFAULT_SPEED = 12.0F;
    private static final float LB_DAMAGE_BONUS = 3.0F;
    private static final float LB_FP_DAMAGE_BONUS = 4.0F;
    private static final int LB_ENCHANTMENT_VALUE = 15;

    // Set temporary tags for issue with unbound mod tag
    private static final TagKey<Item> LB_REPAIR_ITEMS = ItemTags.IRON_TOOL_MATERIALS; // ModItemTags.CASING_REPAIR;
    private static final TagKey<Item> DX_LB_REPAIR_ITEMS = ItemTags.WOODEN_TOOL_MATERIALS; // Tags.Items.DUSTS_REDSTONE;

    public static final ToolMaterial DX_LASER_BLADE = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 255, 12.0F, 1.0F, 15, DX_LB_REPAIR_ITEMS);
    public static final ToolMaterial LASER_BLADE = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, LB_MAX_USES, LB_DEFAULT_SPEED, LB_DAMAGE_BONUS, LB_ENCHANTMENT_VALUE, LB_REPAIR_ITEMS);
    public static final ToolMaterial LASER_BLADE_FP = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, LB_MAX_USES, LB_DEFAULT_SPEED, LB_FP_DAMAGE_BONUS, LB_ENCHANTMENT_VALUE, LB_REPAIR_ITEMS);

    public static ToolMaterial getLBSwordMaterial(boolean isFireResistant) {
        return isFireResistant ? LASER_BLADE_FP : LASER_BLADE;
    }
}
