package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.client.renderer.item.LBBrandNewItemRenderer;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.function.Consumer;

public class LBBrandNewItem extends Item implements LaserBladeItemBase {
    public static Properties properties = new Item.Properties();

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
            // Copy components to Laser Blade. This is for customized recipe
            laserBladeStack = itemStack.transmuteCopy(type.getCopy().getItem(), itemStack.getCount());
            // Repair Laser Blade
            laserBladeStack.setDamageValue(0);
        } else {
            // When Brand-new Laser Blade I or II was used
            laserBladeStack = getPresetLaserBlade(level, player, itemStack);
        }

        // Set Laser Blade to player inventory
        if (player.getAbilities().instabuild) {
            // Creative mode player
            player.addItem(laserBladeStack);
            // Remain Brand-new ItemStack and return
            return;
        } else if (itemStack.getCount() > 1) {
            // If the inventory is full, it will fail
            if (player.addItem(laserBladeStack)) {
                // Successfully added new Laser Blade to the inventory, and consume 1 Brand-new Laser Blade
                itemStack.shrink(1);
            }

            return;
        }

        // ...or change Brand-new Laser Blade to Laser Blade
        EquipmentSlot slotType = (hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        player.setItemSlot(slotType, laserBladeStack);
    }

    private ItemStack getPresetLaserBlade(Level level, Player player, ItemStack brandNewStack) {
        var customName = brandNewStack.get(DataComponents.CUSTOM_NAME);
        String name = (customName != null) ? customName.getString() : "";

        // GIFT code
        // name == {"GIFT" || "おたから"}
        if ("GIFT".equals(name) || "\u304A\u305F\u304B\u3089".equals(name)) {
            // Get GIFT Laser Blade
            return LaserBladeItemStack.GIFT.getCopy();
        }

        // If Brand-new Laser Blade is type of Light Element I or II, ...
        ItemStack laserBladeStack = type.getCopy();
        // ... its blade will be colored by biome player in, and its model will be set to specific model type
        BlockPos pos = player.blockPosition();
        Holder<Biome> biome = level.getBiome(pos);
        LaserBladeAppearance appearance = LaserBladeAppearance.of().setColorsByBiome(level, biome);

        try {
            // Try getting number of model type from display name of item stack
            int modelType = Integer.parseInt(name);
            appearance.setType(modelType);
        } catch (NumberFormatException e) {
        }

        appearance.writeTo(laserBladeStack);
        return laserBladeStack;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        var itemRenderProperties = new IClientItemExtensions() {
            @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return LBBrandNewItemRenderer.INSTANCE;
            }
        };

        consumer.accept(itemRenderProperties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, tooltipContext, tooltip, flag);
        LaserBladeItemUtil.addBrandNewText(tooltip);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, tooltipContext, tooltip, flag, Upgrade.Type.REPAIR);
    }

}
