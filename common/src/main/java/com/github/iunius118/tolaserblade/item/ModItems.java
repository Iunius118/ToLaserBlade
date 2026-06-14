package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.Constants;
import com.github.iunius118.tolaserblade.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<BlockItem> BLOCK_ITEMS = new ArrayList<>();

    // Blueprint
    public static final Item BL_BLUEPRINT =
            addBlock(new BlockItem(ModBlocks.BL_BLUEPRINT,
                    createProperties(Constants.Blocks.BL_BLUEPRINT).useBlockDescriptionPrefix()));

    // Laser Blades
    public static final Item LASER_BLADE =
            add(new LBSwordItem(ModToolMaterials.applyLaserBladeProperties(
                    createProperties(Constants.Items.LASER_BLADE), false, 3F, -2.4F, 0F), false));
    public static final Item LASER_BLADE_FP =
            add(new LBSwordItem(ModToolMaterials.applyLaserBladeProperties(
                    createProperties(Constants.Items.LASER_BLADE_FP), true, 3F, -2.4F, 0F), true));

    // Laser blade parts
    public static final Item LB_BATTERY = add(new Item(createProperties(Constants.Items.LB_BATTERY)));
    public static final Item LB_MEDIUM = add(new Item(createProperties(Constants.Items.LB_MEDIUM)));
    public static final Item LB_EMITTER = add(new Item(createProperties(Constants.Items.LB_EMITTER)));
    public static final Item LB_CASING = add(new Item(createProperties(Constants.Items.LB_CASING)));
    public static final Item LB_CASING_FP =
            add(new Item(createProperties(Constants.Items.LB_CASING_FP).fireResistant()));

    private static Item add(Item item) {
        ITEMS.add(item);
        return item;
    }

    private static Item addBlock(BlockItem item) {
        BLOCK_ITEMS.add(item);
        return add(item);
    }

    private static Item.Properties createProperties(Identifier id) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id));
    }

    static {
        // Register block items
        for (BlockItem blockItem: BLOCK_ITEMS) {
            blockItem.registerBlocks(Item.BY_BLOCK, blockItem);
        }
    }
}
