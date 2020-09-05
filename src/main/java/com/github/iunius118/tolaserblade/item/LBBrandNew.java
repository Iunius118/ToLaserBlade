package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeVisual;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.function.Function;

public class LBBrandNew extends Item {
    public static Properties properties = (new Properties()).setNoRepair().group(ModMainItemGroup.ITEM_GROUP);

    private final Type type;

    public LBBrandNew(Type typeIn) {
        super(properties);
        type = typeIn;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote()) {
            obtainLaserBlade(worldIn, playerIn, itemStack);
            itemStack.shrink(1);
            return ActionResult.resultSuccess(itemStack);
        }

        return ActionResult.resultSuccess(itemStack);
    }

    private void obtainLaserBlade(World worldIn, PlayerEntity playerIn, ItemStack itemStack) {
        ItemStack laserBladeStack = type.getLaserBlade(itemStack);

        if (type != Type.NONE) {
            LaserBlade laserBlade = LaserBlade.of(laserBladeStack);
            LaserBladeVisual visual = laserBlade.getVisual();
            BlockPos pos = playerIn.getPosition();
            Biome biome = worldIn.getBiome(pos);
            visual.setColorsByBiome(biome);
            laserBlade.write(laserBladeStack);
        }

        dropItem(laserBladeStack, playerIn);
    }

    private void dropItem(ItemStack itemStack, PlayerEntity playerIn) {
        ItemEntity itemEntity = new ItemEntity(playerIn.world, playerIn.getPosX(), playerIn.getPosY() + 0.5, playerIn.getPosZ(), itemStack);
        playerIn.world.addEntity(itemEntity);
    }


    enum Type {
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
