package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.client.renderer.LBBrandNewItemRenderer;
import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeVisual;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import java.util.function.Function;

public class LBBrandNewItem extends Item {
    public static Properties properties = (new Item.Properties()).setNoRepair().group(ModMainItemGroup.ITEM_GROUP).setISTER(() -> LBBrandNewItemRenderer::new);

    private final Type type;

    public LBBrandNewItem(Type typeIn) {
        super(properties);
        type = typeIn;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote()) {
            getLaserBlade(worldIn, playerIn, handIn, itemStack);
            return ActionResult.resultSuccess(itemStack);
        }

        return ActionResult.resultSuccess(itemStack);
    }

    private void getLaserBlade(World worldIn, PlayerEntity playerIn, Hand handIn, ItemStack itemStack) {
        ItemStack laserBladeStack = type.getLaserBlade(itemStack);

        if (type != Type.NONE) {
            // If Brand-new Laser Blade is type of Light Element I or II, its blade will be colored by biome player in
            LaserBlade laserBlade = LaserBlade.of(laserBladeStack);
            LaserBladeVisual visual = laserBlade.getVisual();
            BlockPos pos = playerIn.getPosition();
            Biome biome = worldIn.getBiome(pos);
            visual.setColorsByBiome(biome);
            laserBlade.write(laserBladeStack);
        }

        EquipmentSlotType slotType = (handIn == Hand.MAIN_HAND ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND);

        // Set Laser Blade to player inventory
        if (playerIn.abilities.isCreativeMode || itemStack.getCount() > 1) {
            // Remain Brand-new ItemStack and return
            playerIn.addItemStackToInventory(laserBladeStack);
            itemStack.shrink(1);
            return;
        }

        // ...or Change Brand-new Laser Blade to Laser Blade
        playerIn.setItemStackToSlot(slotType, laserBladeStack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.tolaserblade.brandNew1").mergeStyle(TextFormatting.YELLOW));
        tooltip.add(new TranslationTextComponent("tooltip.tolaserblade.brandNew2").mergeStyle(TextFormatting.YELLOW));
        tooltip.add(new TranslationTextComponent("tooltip.tolaserblade.brandNew3").mergeStyle(TextFormatting.YELLOW));
    }

    public enum Type {
        NONE(brandNew -> {
            ItemStack laserBladeStack = new ItemStack(ModItems.LASER_BLADE);
            laserBladeStack.setTag(brandNew.getOrCreateTag());
            return laserBladeStack;}),

        LIGHT_ELEMENT_1(brandNew -> {
            ItemStack laserBlade = new ItemStack(ModItems.LASER_BLADE);
            laserBlade.addEnchantment(ModEnchantments.LIGHT_ELEMENT, 1);
            laserBlade.addEnchantment(Enchantments.EFFICIENCY, 1);
            return laserBlade;}),

        LIGHT_ELEMENT_2(brandNew -> {
            ItemStack laserBlade = new ItemStack(ModItems.LASER_BLADE);
            laserBlade.addEnchantment(ModEnchantments.LIGHT_ELEMENT, 2);
            laserBlade.addEnchantment(Enchantments.EFFICIENCY, 1);
            return laserBlade;});

        private final Function<ItemStack, ItemStack> function;

        Type(Function<ItemStack, ItemStack> laserBladeFunction) {
            function = laserBladeFunction;
        }

        public ItemStack getLaserBlade(ItemStack brandNewStack) {
            return function.apply(brandNewStack);
        }
    }
}
