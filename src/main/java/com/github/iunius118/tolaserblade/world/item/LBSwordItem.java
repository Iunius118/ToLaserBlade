package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.client.renderer.LaserBladeItemRenderer;
import com.github.iunius118.tolaserblade.core.dispenser.DispenseLaserBladeBehavior;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeBlocking;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladePerformance;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

import javax.annotation.Nullable;
import java.util.List;

public class LBSwordItem extends SwordItem implements LaserBladeItemBase {
    private final IItemTier tier;
    private final float attackDamage;
    private final float attackSpeed;

    public static Item.Properties properties = (new Item.Properties()).setNoRepair().tab(ModMainItemGroup.ITEM_GROUP).setISTER(() -> LaserBladeItemRenderer::new);

    public LBSwordItem(boolean isFireproof) {
        super(new LBSwordItemTier(isFireproof), 3, -1.2F, LaserBladeItemBase.setFireproof(properties, isFireproof));

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
        return LaserBladeBlocking.isShield();
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return LaserBladeBlocking.getUseAction();
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return LaserBladeBlocking.getUseDuration();
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        LaserBladeBlocking.start(player, hand);
        return new ActionResult<>(ActionResultType.PASS, itemstack);
    }

    /* Sounds */

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        World world = entity.getCommandSenderWorld();

        if (!world.isClientSide && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)entity;

            if (!player.swinging) {
                LaserBladeItemUtil.playSwingSound(world, entity, isFireResistant());
            }
        }

        return super.onEntitySwing(stack, entity);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getCommandSenderWorld();

        if (!world.isClientSide) {
            LaserBladeItemUtil.playSwingSound(world, attacker, isFireResistant());
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    /* Handling Events */

    public void onCriticalHit(CriticalHitEvent event) {
        Entity target = event.getTarget();
        PlayerEntity player = event.getPlayer();
        ItemStack stack = player.getMainHandItem();
        LaserBladePerformance performance = LaserBlade.performanceOf(stack);
        LaserBladePerformance.AttackPerformance attack = performance.getAttackPerformance();

        if (target instanceof WitherEntity || attack.damage >= LaserBladePerformance.AttackPerformance.MOD_ATK_CRITICAL_BONUS) {
            event.setDamageModifier(event.getDamageModifier() + LaserBladePerformance.AttackPerformance.MOD_CRITICAL_BONUS_VS_WITHER);
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
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return LaserBladeItemUtil.getDestroySpeed(stack, tier);
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
        LaserBladeItemUtil.addLaserBladeInformation(stack, worldIn, tooltip, flagIn, Upgrade.Type.OTHER);
    }

    /* Creative Tab */

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        super.fillItemCategory(group, items);
        if (group != ModMainItemGroup.ITEM_GROUP) return;

        LaserBladeItemUtil.addItemStacks(items, isFireResistant());
    }
}
