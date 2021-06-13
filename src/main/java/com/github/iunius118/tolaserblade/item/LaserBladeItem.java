package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemColor;
import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemRenderer;
import com.github.iunius118.tolaserblade.config.ToLaserBladeConfig;
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

    public static Item.Properties properties = (new Item.Properties()).setNoRepair().tab(ModMainItemGroup.ITEM_GROUP).setISTER(() -> LaserBladeItemRenderer::new);

    public LaserBladeItem(boolean isFireproof) {
        super(new ItemTier(isFireproof), 3, -1.2F, LaserBladeItemBase.setFireproof(properties, isFireproof));

        tier = getTier();
        attackDamage = 3.0F + tier.getAttackDamageBonus();
        attackSpeed = -1.2F;

        // Register dispense behavior
        DispenserBlock.registerBehavior(this, new DispenseLaserBladeBehavior());
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
    public UseAction getUseAnimation(ItemStack stack) {
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
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);

        if (ToLaserBladeConfig.SERVER.isEnabledBlockingWithLaserBlade.get()) {
            UseAction offhandItemAction = playerIn.getOffhandItem().getUseAnimation();

            if (offhandItemAction != UseAction.BOW && offhandItemAction != UseAction.SPEAR) {
                playerIn.startUsingItem(handIn);
            }

        }

        return new ActionResult<>(ActionResultType.PASS, itemstack);
    }

    /* Sounds */

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        World world = entity.getCommandSenderWorld();

        if (!world.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;

            if (!player.swinging) {
                playSwingSound(world, entity);
            }
        }

        return super.onEntitySwing(stack, entity);
    }

    private void playSwingSound(World world, LivingEntity entity) {
        SoundEvent soundEvent = isFireResistant() ? ModSoundEvents.ITEM_LASER_BLADE_FP_SWING : ModSoundEvents.ITEM_LASER_BLADE_SWING;
        Vector3d pos = entity.position().add(0, entity.getEyeHeight(), 0).add(entity.getLookAngle());
        world.playSound(null, pos.x, pos.y, pos.z, soundEvent, SoundCategory.PLAYERS, 0.5F, 1.0F);
    }

    /* Handling Events */

    public void onCriticalHit(CriticalHitEvent event) {
        Entity target = event.getTarget();
        PlayerEntity player = event.getPlayer();
        ItemStack stack = player.getMainHandItem();
        LaserBladePerformance performance = LaserBlade.performanceOf(stack);
        LaserBladePerformance.AttackPerformance attack = performance.getAttackPerformance();

        if (target instanceof WitherEntity || attack.damage >= LaserBladePerformance.AttackPerformance.MOD_ATK_CRITICAL_BONUS) {
            event.setDamageModifier(event.getDamageModifier() + MOD_CRITICAL_BONUS_VS_WITHER);
        }
    }

    /* Item Characterizing */

    @Override
    public float getDamage() {
        return attackDamage;
    }

    @Override
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) != 0.0F) {
            stack.hurtAndBreak(1, entityLiving, (playerEntity) -> playerEntity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        }

        return true;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getCommandSenderWorld();

        if (!world.isClientSide) {
            playSwingSound(world, attacker);
        }

        stack.hurtAndBreak(1, attacker, (playerEntity) -> playerEntity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float rate = (float)EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, stack) / 5.0F;
        return tier.getSpeed() * MathHelper.clamp(rate, 0.0F, 1.0F);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState blockIn) {
        return true;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, PlayerEntity player, BlockState blockState) {
        return tier.getLevel();
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        // Accept Mending and Curse of Vanish
        return enchantment.category == EnchantmentType.WEAPON
                || enchantment == Enchantments.BLOCK_EFFICIENCY
                || enchantment == Enchantments.MENDING
                || enchantment == Enchantments.SILK_TOUCH
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
                    new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                            this.attackDamage + attack.damage, AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED,
                    new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
                            this.attackSpeed + attack.speed, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        addLaserBladeInformation(stack, worldIn, tooltip, flagIn, Upgrade.Type.OTHER);
    }

    /* Creative Tab */

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        super.fillItemCategory(group, items);
        if (group != ModMainItemGroup.ITEM_GROUP) return;

        if (isFireResistant()) {
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
        public int getLevel() {
            return isNetheritic ? NETHERITIC_HARVEST_LEVEL : HARVEST_LEVEL;
        }

        @Override
        public int getUses() {
            return LaserBladeItemBase.MAX_USES;
        }

        @Override
        public float getSpeed() {
            return ToLaserBladeConfig.SERVER.laserBladeEfficiency.get();
        }

        @Override
        public float getAttackDamageBonus() {
            return isNetheritic ? NETHERITIC_DAMAGE : NORMAL_DAMAGE;
        }

        @Override
        public int getEnchantmentValue() {
            return ENCHANTABILITY;
        }

        @Override
        public Ingredient getRepairIngredient() {
            ITag<Item> tag = ItemTags.getAllTags().getTag(new ResourceLocation("forge", "ingots/iron"));

            if (tag != null) {
                return Ingredient.of(tag);

            } else {
                return Ingredient.of(Items.IRON_INGOT);
            }
        }
    }
}
