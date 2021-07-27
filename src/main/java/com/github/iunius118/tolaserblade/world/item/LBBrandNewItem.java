package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import com.github.iunius118.tolaserblade.client.renderer.item.LBBrandNewItemRenderer;
import com.github.iunius118.tolaserblade.client.renderer.item.LBBrokenItemRenderer;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeVisual;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nullable;
import java.util.List;

public class LBBrandNewItem extends Item implements LaserBladeItemBase {
    public static Properties properties = (new Item.Properties()).setNoRepair().tab(ModMainItemGroup.ITEM_GROUP);

    private final LBBrandNewType type;

    public LBBrandNewItem(LBBrandNewType typeIn, boolean isFireproof) {
        super(LaserBladeItemBase.setFireproof(properties, isFireproof));
        type = typeIn;
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            setLaserBladeToPlayer(level, player, hand, itemStack);
            return InteractionResultHolder.success(itemStack);
        }

        return InteractionResultHolder.success(itemStack);
    }

    private void setLaserBladeToPlayer(Level level, Player player, InteractionHand hand, ItemStack itemStack) {
        ItemStack laserBladeStack;

        if (type == LBBrandNewType.NONE || type == LBBrandNewType.FP) {
            // Copy NBT tag to Laser Blade. This is for customized recipe
            CompoundTag tag = itemStack.getOrCreateTag();
            CompoundTag tagNew = tag.copy();    // Copy nbt to make it independent of the Brand-new Laser Blade
            laserBladeStack = type.getCopy();
            laserBladeStack.setTag(tagNew);
            laserBladeStack.setDamageValue(0);   // Repair Laser Blade
        } else {
            laserBladeStack = getPresetLaserBlade(level, player, itemStack);
        }

        EquipmentSlot slotType = (hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

        // Set Laser Blade to player inventory
        if (player.getAbilities().instabuild || itemStack.getCount() > 1) {
            // Remain Brand-new ItemStack and return
            // If the inventory is full, it will fail
            if (!player.addItem(laserBladeStack)) return;
            // Successfully added new Laser Blade to the inventory, and consume 1 Brand-new Laser Blade
            itemStack.shrink(1);
            return;
        }

        // ...or change Brand-new Laser Blade to Laser Blade
        player.setItemSlot(slotType, laserBladeStack);
    }

    private ItemStack getPresetLaserBlade(Level level, Player player, ItemStack brandNewStack) {
        String name = brandNewStack.hasCustomHoverName() ? brandNewStack.getHoverName().getString() : "";

        // GIFT code
        if ("GIFT".equals(name) || "\u304A\u305F\u304B\u3089".equals(name)) {   // name == {"GIFT" || "おたから"}
            return LaserBladeItemStack.GIFT.getCopy();  // Get GIFT Laser Blade
        }

        int modelType;

        // Try getting number of model type from display name of item stack
        try {
            modelType = Integer.parseInt(name);
        } catch (NumberFormatException e) {
            modelType = ToLaserBlade.getTodayDateNumber();
        }

        // If Brand-new Laser Blade is type of Light Element I or II, ...
        ItemStack laserBladeStack = type.getCopy();
        LaserBlade laserBlade = LaserBlade.of(laserBladeStack);
        LaserBladeVisual visual = laserBlade.getVisual();
        BlockPos pos = player.blockPosition();
        Biome biome = level.getBiome(pos);
        // ... its blade will be colored by biome player in, ...
        visual.setColorsByBiome(level, biome);
        // ... and its model will be set to today's model type
        visual.setModelType(modelType);
        laserBlade.write(laserBladeStack);
        return laserBladeStack;
    }

    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.IItemRenderProperties> consumer) {
        var itemRenderProperties = new IItemRenderProperties() {
            final BlockEntityWithoutLevelRenderer renderer = new LBBrandNewItemRenderer();
            @Override public BlockEntityWithoutLevelRenderer getItemStackRenderer() { return renderer; }
        };

        consumer.accept(itemRenderProperties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, tooltip, flag);
        LaserBladeItemUtil.addBrandNewText(tooltip);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, level, tooltip, flag, Upgrade.Type.REPAIR);
    }

}
