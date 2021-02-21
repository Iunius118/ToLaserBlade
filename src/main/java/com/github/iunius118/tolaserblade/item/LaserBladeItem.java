package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.ToLaserBladeConfig;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemRenderer;
import com.github.iunius118.tolaserblade.dispenser.DispenseLaserBladeBehavior;
import com.github.iunius118.tolaserblade.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.laserblade.LaserBladePerformance;
import com.github.iunius118.tolaserblade.laserblade.LaserBladeStack;
import com.github.iunius118.tolaserblade.laserblade.upgrade.Upgrade;
import com.github.iunius118.tolaserblade.util.ModSoundEvents;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

import javax.annotation.Nullable;
import java.util.List;

public class LaserBladeItem extends SwordItem implements LaserBladeItemBase {
    private final IItemTier tier;
    private final float attackDamage;
    private final float attackSpeed;

    public static Item.Properties properties = (new Item.Properties()).setNoRepair().group(ModMainItemGroup.ITEM_GROUP).setISTER(() -> LaserBladeItemRenderer::new);

    public LaserBladeItem(boolean isFireproof) {
        super(new ItemTier(isFireproof), 3, -1.2F, LaserBladeItemBase.setFireproof(properties, isFireproof));

        tier = getTier();
        attackDamage = 3.0F + tier.getAttackDamage();
        attackSpeed = -1.2F;

        // Register dispense behavior
        DispenserBlock.registerDispenseBehavior(this, new DispenseLaserBladeBehavior());
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return true;
    }

    /* Shield Functions */

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return ToLaserBladeConfig.SERVER.isEnabledBlockingWithLaserBlade.get();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        if (ToLaserBladeConfig.SERVER.isEnabledBlockingWithLaserBlade.get()) {
            return UseAction.BLOCK;
        } else {
            return UseAction.NONE;
        }
    }


    @Override
    public int getUseDuration(ItemStack stack) {
        if (ToLaserBladeConfig.SERVER.isEnabledBlockingWithLaserBlade.get()) {
            return 72000;
        } else {
            return 0;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (ToLaserBladeConfig.SERVER.isEnabledBlockingWithLaserBlade.get()) {
            UseAction offhandItemAction = playerIn.getHeldItemOffhand().getUseAction();

            if (offhandItemAction != UseAction.BOW && offhandItemAction != UseAction.SPEAR) {
                playerIn.setActiveHand(handIn);
            }

        }

        return new ActionResult<>(ActionResultType.PASS, itemstack);
    }

    /* Sounds */

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        World world = entity.getEntityWorld();

        if (!world.isRemote && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;

            if (!player.isSwingInProgress) {
                playSwingSound(world, entity);
            }
        }

        return super.onEntitySwing(stack, entity);
    }

    private void playSwingSound(World world, LivingEntity entity) {
        SoundEvent soundEvent = isImmuneToFire() ? ModSoundEvents.ITEM_LASER_BLADE_FP_SWING : ModSoundEvents.ITEM_LASER_BLADE_SWING;
        Vector3d pos = entity.getPositionVec().add(0, entity.getEyeHeight(), 0).add(entity.getLookVec());
        world.playSound(null, pos.x, pos.y, pos.z, soundEvent, SoundCategory.PLAYERS, 0.5F, 1.0F);
    }

    /* Handling Events */

    public void onCriticalHit(CriticalHitEvent event) {
        Entity target = event.getTarget();
        PlayerEntity player = event.getPlayer();
        ItemStack stack = player.getHeldItemMainhand();
        LaserBladePerformance performance = LaserBlade.performanceOf(stack);
        LaserBladePerformance.AttackPerformance attack = performance.getAttackPerformance();

        if (target instanceof WitherEntity || attack.damage >= LaserBladePerformance.AttackPerformance.MOD_ATK_CRITICAL_BONUS) {
            event.setDamageModifier(event.getDamageModifier() + MOD_CRITICAL_BONUS_VS_WITHER);
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
        World world = attacker.getEntityWorld();

        if (!world.isRemote) {
            playSwingSound(world, attacker);
        }

        stack.damageItem(1, attacker, (playerEntity) -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float rate = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) / 5.0F;
        return tier.getEfficiency() * MathHelper.clamp(rate, 0.0F, 1.0F);
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
    public boolean isDamageable(ItemStack stack) {
        return true;
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
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();

        if (slot == EquipmentSlotType.MAINHAND) {
            LaserBladePerformance performance = LaserBlade.performanceOf(stack);
            LaserBladePerformance.AttackPerformance attack = performance.getAttackPerformance();

            multimap.put(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier",
                            this.attackDamage + attack.damage, AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED,
                    new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier",
                            this.attackSpeed + attack.speed, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        addLaserBladeInformation(stack, worldIn, tooltip, flagIn, Upgrade.Type.OTHER);
    }

    /* Creative Tab */

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        super.fillItemGroup(group, items);
        if (group != ModMainItemGroup.ITEM_GROUP) return;

        if (isImmuneToFire()) {
            items.add(LaserBladeStack.UPGRADED_FP.getCopy());
            items.add(LaserBladeStack.DAMAGED_FP.getCopy());
            items.add(LaserBladeStack.FULL_MOD_FP.getCopy());
        } else {
            items.add(LaserBladeStack.MODEL_TYPE_526.getCopy());
            items.add(LaserBladeStack.LIGHT_ELEMENT_1.getCopy());
            items.add(LaserBladeStack.LIGHT_ELEMENT_2.getCopy());
            items.add(LaserBladeStack.GIFT.getCopy());
            items.add(LaserBladeStack.UPGRADED.getCopy());
            items.add(LaserBladeStack.DAMAGED.getCopy());
            items.add(LaserBladeStack.FULL_MOD.getCopy());
        }
    }

    /* Inner Classes */

    @OnlyIn(Dist.CLIENT)
    public static class ColorHandler implements IItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            LaserBladeItemColor color = LaserBladeItemColor.of(stack);

            switch (tintIndex) {
                case 0:
                    return color.gripColor | 0xFF000000;
                case 1:
                    return color.simpleOuterColor | 0xFF000000;
                case 2:
                    return color.simpleInnerColor | 0xFF000000;
                default:
                    return 0xFFFFFFFF;
            }
        }
    }

    public static class ItemTier implements IItemTier {
        private final static int NETHERITIC_HARVEST_LEVEL = 4;
        private final static int HARVEST_LEVEL = 3;
        private final static float NETHERITIC_DAMAGE = 4.0F;
        private final static float NORMAL_DAMAGE = 3.0F;
        private final static int ENCHANTABILITY = 15;

        private final boolean isNetheritic;

        public ItemTier(boolean isFireproof){
            isNetheritic = isFireproof;
        }

        @Override
        public int getHarvestLevel() {
            return isNetheritic ? NETHERITIC_HARVEST_LEVEL : HARVEST_LEVEL;
        }

        @Override
        public int getMaxUses() {
            return LaserBladeItemBase.MAX_USES;
        }

        @Override
        public float getEfficiency() {
            return ToLaserBladeConfig.SERVER.laserBladeEfficiency.get();
        }

        @Override
        public float getAttackDamage() {
            return isNetheritic ? NETHERITIC_DAMAGE : NORMAL_DAMAGE;
        }

        @Override
        public int getEnchantability() {
            return ENCHANTABILITY;
        }

        @Override
        public Ingredient getRepairMaterial() {
            ITag<Item> tag = ItemTags.getCollection().get(new ResourceLocation("forge", "ingots/iron"));

            if (tag != null) {
                return Ingredient.fromTag(tag);

            } else {
                return Ingredient.fromItems(Items.IRON_INGOT);
            }
        }
    }
}
