package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.client.renderer.LBBrandNewItemRenderer;
import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeStack;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeVisual;
import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class LBBrandNewItem extends Item implements LaserBladeItemBase {
    public static Properties properties = (new Item.Properties()).setNoRepair().tab(ModMainItemGroup.ITEM_GROUP).setISTER(() -> LBBrandNewItemRenderer::new);
    public static final String KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_1 = "tooltip.tolaserblade.brandNew1";
    public static final String KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_2 = "tooltip.tolaserblade.brandNew2";
    public static final String KEY_TOOLTIP_BLAND_NEW_HOW_TO_USE_LINE_3 = "tooltip.tolaserblade.brandNew3";


    private final Type type;

    public LBBrandNewItem(Type typeIn, boolean isFireproof) {
        super(LaserBladeItemBase.setFireproof(properties, isFireproof));
        type = typeIn;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getItemInHand(handIn);

        if (!worldIn.isClientSide()) {
            setLaserBladeToPlayer(worldIn, playerIn, handIn, itemStack);
            return ActionResult.success(itemStack);
        }

        return ActionResult.success(itemStack);
    }

    private void setLaserBladeToPlayer(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack itemStack) {
        ItemStack laserBladeStack;

        if (type == Type.NONE || type == Type.FP) {
            // Copy NBT tag to Laser Blade. This is for customized recipe
            CompoundNBT tag = itemStack.getOrCreateTag();
            CompoundNBT newNbt = tag.copy();    // Copy nbt to make it independent of the Brand-new Laser Blade
            laserBladeStack = type.getCopy();
            laserBladeStack.setTag(newNbt);
            laserBladeStack.setDamageValue(0);   // Repair Laser Blade
        } else {
            laserBladeStack = getPresetLaserBlade(worldIn, playerIn, itemStack);
        }

        EquipmentSlotType slotType = (handIn == Hand.MAIN_HAND ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND);

        // Set Laser Blade to player inventory
        if (playerIn.abilities.instabuild || itemStack.getCount() > 1) {
            // Remain Brand-new ItemStack and return
            // If the inventory is full, it will fail
            if (!playerIn.addItem(laserBladeStack)) return;
            // Successfully added new Laser Blade to the inventory, and consume 1 Brand-new Laser Blade
            itemStack.shrink(1);
            return;
        }

        // ...or Change Brand-new Laser Blade to Laser Blade
        playerIn.setItemSlot(slotType, laserBladeStack);
    }

    private ItemStack getPresetLaserBlade(World worldIn, PlayerEntity playerIn, ItemStack brandNewStack) {
        String name = brandNewStack.hasCustomHoverName() ? brandNewStack.getHoverName().getString() : "";

        // GIFT code
        if ("GIFT".equals(name) || "\u304A\u305F\u304B\u3089".equals(name)) {   // name == {"GIFT" || "おたから"}
            return LaserBladeStack.GIFT.getCopy();  // Get GIFT Laser Blade
        }

        int modelType;

        try {
            modelType = Integer.parseInt(name);
        } catch (NumberFormatException e) {
            modelType = LaserBladeVisual.MODEL_TYPE_NO_MODEL;
        }

        // If Brand-new Laser Blade is type of Light Element I or II, ...
        ItemStack laserBladeStack = type.getCopy();
        LaserBlade laserBlade = LaserBlade.of(laserBladeStack);
        LaserBladeVisual visual = laserBlade.getVisual();
        BlockPos pos = playerIn.blockPosition();
        Biome biome = worldIn.getBiome(pos);
        // ... its blade will be colored by biome player in, ...
        visual.setColorsByBiome(worldIn, biome);
        // ... and its model will be set to specific model type
        visual.setModelType(modelType);
        laserBlade.write(laserBladeStack);
        return laserBladeStack;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TranslationTextComponent("tooltip.tolaserblade.brandNew1").withStyle(TextFormatting.YELLOW));
        tooltip.add(new TranslationTextComponent("tooltip.tolaserblade.brandNew2").withStyle(TextFormatting.YELLOW));
        tooltip.add(new TranslationTextComponent("tooltip.tolaserblade.brandNew3").withStyle(TextFormatting.YELLOW));

        addLaserBladeInformation(stack, worldIn, tooltip, flagIn, Upgrade.Type.REPAIR);
    }

    public enum Type {
        NONE(LaserBladeStack.ORIGINAL),
        LIGHT_ELEMENT_1(LaserBladeStack.LIGHT_ELEMENT_1),
        LIGHT_ELEMENT_2(LaserBladeStack.LIGHT_ELEMENT_2),
        FP(LaserBladeStack.FP);

        private final LaserBladeStack original;

        Type(LaserBladeStack stack) {
            original = stack;
        }

        public ItemStack getCopy() {
            return original.getCopy();
        }
    }
}
