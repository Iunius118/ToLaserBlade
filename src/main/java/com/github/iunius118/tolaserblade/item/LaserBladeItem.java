package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class LaserBladeItem extends SwordItem implements ToLaserBladeItemGroup {
    private final IItemTier tier;
    private final float attackDamage;
    private final float attackSpeed;

    public static Item.Properties properties = (new Item.Properties()).setNoRepair().group(ItemGroup.TOOLS);
    public static final IItemPropertyGetter BLOCKING_GETTER = (stack, world, entity) -> {
        return entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;
    };

    public LaserBladeItem() {
        super(new ItemTier(), 3, -1.2F, properties);

        tier = getTier();
        attackDamage = 3.0F + tier.getAttackDamage();
        attackSpeed = -1.2F;
        addPropertyOverride(new ResourceLocation("blocking"), BLOCKING_GETTER);
    }

    /* Shield functions */

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return ToLaserBladeConfig.COMMON.isEnabledBlockingWithLaserBladeInServer.get();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        if (ToLaserBladeConfig.COMMON.isEnabledBlockingWithLaserBladeInServer.get()) {
            return UseAction.BLOCK;
        } else {
            return UseAction.NONE;
        }
    }


    @Override
    public int getUseDuration(ItemStack stack) {
        if (ToLaserBladeConfig.COMMON.isEnabledBlockingWithLaserBladeInServer.get()) {
            return 72000;
        } else {
            return 0;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (ToLaserBladeConfig.COMMON.isEnabledBlockingWithLaserBladeInServer.get()) {
            UseAction offhandItemAction = playerIn.getHeldItemOffhand().getUseAction();

            if (offhandItemAction != UseAction.BOW && offhandItemAction != UseAction.SPEAR) {
                playerIn.setActiveHand(handIn);
            }

        }

        return new ActionResult<>(ActionResultType.PASS, itemstack);
    }

    /* Characterizing */

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            stack.damageItem(1, entityLiving, (playerEntity) -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }

        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (playerEntity) -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return tier.getEfficiency();
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return true;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, PlayerEntity player, BlockState blockState) {
        return tier.getHarvestLevel();
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ColorHandler implements IItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            if (ToLaserBladeConfig.CLIENT.isEnabledLaserBlade3DModel.get()) {
                return 0xFFFFFFFF;
            }

            switch (tintIndex) {
                case 1:
                    return getColorFromNBT(stack, LaserBlade.KEY_COLOR_HALO, LaserBlade.KEY_IS_SUB_COLOR_HALO, LaserBlade.DEFAULT_COLOR_HALO);

                case 2:
                    return getColorFromNBT(stack, LaserBlade.KEY_COLOR_CORE, LaserBlade.KEY_IS_SUB_COLOR_CORE, LaserBlade.DEFAULT_COLOR_CORE);

                default:
                    return getColorFromNBT(stack, LaserBlade.KEY_COLOR_GRIP, null, LaserBlade.DEFAULT_COLOR_GRIP);
            }
        }

        private int getColorFromNBT(ItemStack stack, String keyColor, String keyIsSubColor, int defaultColor) {
            CompoundNBT nbt = stack.getTag();

            if (nbt != null && nbt.contains(keyColor, Constants.NBT.TAG_INT)) {
                int color = nbt.getInt(keyColor);

                if (keyIsSubColor != null && nbt.getBoolean(keyIsSubColor)) {
                    color = ~color | 0xFF000000;
                }

                return color;
            }

            return defaultColor;
        }
    }

    public static class ItemTier implements IItemTier {
        @Override
        public int getHarvestLevel() {
            return 3;
        }

        @Override
        public int getMaxUses() {
            return LaserBlade.MAX_USES;
        }

        @Override
        public float getEfficiency() {
            return ToLaserBladeConfig.COMMON.laserBladeEfficiencyInServer.get();
        }

        @Override
        public float getAttackDamage() {
            return 3.0F;
        }

        @Override
        public int getEnchantability() {
            return 15;
        }

        @Override
        public Ingredient getRepairMaterial() {
            Tag<Item> tag = ItemTags.getCollection().get(new ResourceLocation("forge", "ingots/iron"));

            if (tag != null) {
                return Ingredient.fromTag(tag);
            } else {
                return Ingredient.fromItems(Items.IRON_INGOT);
            }
        }
    }
}
