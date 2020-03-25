package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemRenderer;
import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

public class LaserBladeItem extends SwordItem implements LaserBladeItemBase, ModMainItemGroup {
    private final IItemTier tier;
    private final float attackDamage;
    private final float attackSpeed;

    public static Item.Properties properties = (new Item.Properties()).setNoRepair().group(ItemGroup.TOOLS).setISTER(() -> LaserBladeItemRenderer::new);
    public static final IItemPropertyGetter BLOCKING_GETTER = (stack, world, entity) -> entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F;

    public LaserBladeItem() {
        super(new ItemTier(), 3, -1.2F, properties);

        tier = getTier();
        attackDamage = 3.0F + tier.getAttackDamage();
        attackSpeed = -1.2F;
        addPropertyOverride(new ResourceLocation("blocking"), BLOCKING_GETTER);
    }

    @Override
    public boolean canUpgrade(LaserBladeUpgrade.Type type) {
        return true;
    }

    /* Shield Functions */

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

    /* Handling Events */

    public void onCriticalHit(CriticalHitEvent event) {
        Entity target = event.getTarget();
        float attack = getLaserBladeATK(event.getPlayer().getHeldItemMainhand());

        if (event.isVanillaCritical()) {
            if (target instanceof WitherEntity || attack > MOD_ATK_CLASS_4) {
                event.setDamageModifier(MOD_CRITICAL_VS_WITHER);
            }
        }
    }

    /* Item Characterizing */

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
        return tier.getEfficiency() * getDestroySpeedRate(stack);
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

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        // Accept Mending and Curse of Vanish
        return enchantment.type == EnchantmentType.WEAPON
                || enchantment == Enchantments.EFFICIENCY
                || enchantment == Enchantments.MENDING
                || enchantment == Enchantments.VANISHING_CURSE;
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
        return armorType == EquipmentSlotType.HEAD;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);

        if (slot == EquipmentSlotType.MAINHAND) {
            multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                    new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier",
                            this.attackDamage + getLaserBladeATK(stack), AttributeModifier.Operation.ADDITION));

            multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                    new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier",
                            this.attackSpeed + getLaserBladeSPD(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    /* Creative Tab */

    private ItemStack laserBladeUpgraded;
    private ItemStack laserBladeFullMod;

    private ItemStack getLaserBladeUpgraded() {
        ItemStack laserBlade = new ItemStack(ModItems.LASER_BLADE);

        setLaserBladeATK(laserBlade, MOD_ATK_CLASS_5);
        setLaserBladeSPD(laserBlade, MOD_SPD_MAX);

        setGripColor(laserBlade, LBColor.GRAY.getGripColor());
        setBladeInnerColor(laserBlade, LBColor.LIGHT_BLUE.getBladeColor());
        setBladeOuterColor(laserBlade, LBColor.BLUE.getBladeColor());

        laserBlade.addEnchantment(ModEnchantments.LIGHT_ELEMENT, ModEnchantments.LIGHT_ELEMENT.getMaxLevel());
        laserBlade.addEnchantment(Enchantments.EFFICIENCY, Enchantments.EFFICIENCY.getMaxLevel());
        laserBlade.addEnchantment(Enchantments.MENDING, Enchantments.MENDING.getMaxLevel());

        return laserBlade;
    }

    private ItemStack getLaserBladeFullMod() {
        ItemStack laserBlade = getLaserBladeUpgraded();

        setBladeInnerColor(laserBlade, LBColor.WHITE.getBladeColor());
        setBladeInnerSubColorFlag(laserBlade, true);
        setBladeOuterColor(laserBlade, LBColor.CYAN.getBladeColor());
        setBladeOuterSubColorFlag(laserBlade, true);

        laserBlade.addEnchantment(Enchantments.FIRE_ASPECT, Enchantments.FIRE_ASPECT.getMaxLevel());
        laserBlade.addEnchantment(Enchantments.SWEEPING, Enchantments.SWEEPING.getMaxLevel());
        laserBlade.addEnchantment(Enchantments.LOOTING, Enchantments.LOOTING.getMaxLevel());

        return laserBlade;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        super.fillItemGroup(group, items);

        if (laserBladeUpgraded == null) {
            laserBladeUpgraded = getLaserBladeUpgraded();
        }

        if (laserBladeFullMod == null) {
            laserBladeFullMod = getLaserBladeFullMod();
        }

        if (group == ModMainItemGroup.ITEM_GROUP) {
            items.add(laserBladeUpgraded);
            items.add(laserBladeFullMod);
        }
    }



    /* Inner Classes */

    @OnlyIn(Dist.CLIENT)
    public static class ColorHandler implements IItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            if (ToLaserBladeConfig.CLIENT.isEnabledLaserBlade3DModel.get()) {
                return 0xFFFFFFFF;
            }

            Pair<Integer, Boolean> bladeColor;

            switch (tintIndex) {
                case 1:
                    bladeColor = ModItems.LASER_BLADE.getBladeOuterColor(stack);
                    return (bladeColor.getRight() ? ~bladeColor.getLeft() : bladeColor.getLeft()) | 0xFF000000;

                case 2:
                    bladeColor = ModItems.LASER_BLADE.getBladeInnerColor(stack);
                    return (bladeColor.getRight() ? ~bladeColor.getLeft() : bladeColor.getLeft()) | 0xFF000000;

                default:
                    return ModItems.LASER_BLADE.getGripColor(stack) | 0xFF000000;
            }
        }
    }

    public static class ItemTier implements IItemTier {
        @Override
        public int getHarvestLevel() {
            return 3;
        }

        @Override
        public int getMaxUses() {
            return LaserBladeItemBase.MAX_USES;
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
