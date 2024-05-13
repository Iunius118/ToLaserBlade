package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordItemRenderer;
import com.github.iunius118.tolaserblade.core.dispenser.DispenseLaserBladeBehavior;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeAppearance;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeBlocking;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

import java.util.List;
import java.util.function.Consumer;

public class LBSwordItem extends SwordItem implements LaserBladeItemBase {
    private final Tier tier;
    private final float attackDamage;
    private final float attackSpeed;

    public LBSwordItem(boolean isFireproof) {
        super(ModItemTiers.getLBSwordTier(isFireproof),
                LaserBladeItemBase.setFireproof(new Item.Properties(), isFireproof)
                        .attributes(SwordItem.createAttributes(ModItemTiers.getLBSwordTier(isFireproof), 3, -1.2F)));

        tier = getTier();
        attackDamage = 3.0F + tier.getAttackDamageBonus();
        attackSpeed = -1.2F;

        // Register dispense behavior
        DispenserBlock.registerBehavior(this, new DispenseLaserBladeBehavior());
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack stack) {
        float attackUpgrade = LaserBlade.getAttack(stack);
        float speedUpgrade = LaserBlade.getSpeed(stack);
        var itemAttributeModifiers = createAttributes(attackUpgrade, speedUpgrade);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, itemAttributeModifiers);
        LaserBladeAppearance.of(stack);
    }

    private ItemAttributeModifiers createAttributes(float attackUpgrade, float speedUpgrade) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackUpgrade + attackDamage, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", speedUpgrade + attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    @Override
    public boolean canUpgrade(Upgrade.Type type) {
        return true;
    }

    /* Shield Functions */

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return super.canPerformAction(stack, toolAction) || (LaserBladeBlocking.isShield() && ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return LaserBladeBlocking.getUseAction();
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return LaserBladeBlocking.getUseDuration();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        LaserBladeBlocking.start(player, hand);

        if (LaserBladeBlocking.isShield()) {
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.pass(itemstack);
        }
    }

    /* Sounds */

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        Level level = entity.getCommandSenderWorld();

        if (!level.isClientSide && entity instanceof Player player) {
            if (!player.swinging) {
                LaserBladeItemUtil.playSwingSound(level, entity, stack.has(DataComponents.FIRE_RESISTANT));
            }
        }

        return super.onEntitySwing(stack, entity);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.getCommandSenderWorld();

        if (!level.isClientSide) {
            LaserBladeItemUtil.playSwingSound(level, attacker, stack.has(DataComponents.FIRE_RESISTANT));
            hurtAndBreak(stack, 1, attacker);
        }

        return true;
    }

    /* Handling Events */

    public void onCriticalHit(CriticalHitEvent event) {
        Entity target = event.getTarget();
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();

        if (target instanceof WitherBoss || LaserBlade.getAttack(stack) >= LaserBlade.MOD_ATK_CRITICAL_BONUS) {
            event.setDamageModifier(event.getDamageModifier() + LaserBlade.MOD_CRITICAL_BONUS_VS_WITHER);
        }
    }

    /* Item Characterizing */

    public float getDamage() {
        return attackDamage;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            hurtAndBreak(stack, 1, entityLiving);
        }

        return true;
    }

    private void hurtAndBreak(ItemStack stack, int damage, LivingEntity entityLiving) {
        stack.hurtAndBreak(damage, entityLiving, EquipmentSlot.MAINHAND);
        int count = stack.getCount();

        if (count > 0 /* || level.isClientSide */) {
            return;
        }

        // This item has been broken
        stack.setCount(1);
        ItemStack brokenLaserBlade;

        if (stack.has(DataComponents.FIRE_RESISTANT)) {
            brokenLaserBlade = stack.transmuteCopy(ModItems.LB_BROKEN_FP, 1);
        } else {
            brokenLaserBlade = stack.transmuteCopy(ModItems.LB_BROKEN, 1);
        }

        stack.setCount(count);

        // Drop Broken Laser Blade
        Level level = entityLiving.level();
        ItemEntity itemEntity = new ItemEntity(level, entityLiving.getX(), entityLiving.getY() + 0.5, entityLiving.getZ(), brokenLaserBlade);
        level.addFreshEntity(itemEntity);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return LaserBladeItemUtil.getDestroySpeed(stack, tier);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
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
        return enchantment.getSupportedItems() == ItemTags.WEAPON_ENCHANTABLE
                || enchantment == Enchantments.EFFICIENCY
                || enchantment == Enchantments.MENDING
                || enchantment == Enchantments.SILK_TOUCH
                || enchantment == Enchantments.VANISHING_CURSE;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        var itemRenderProperties = new IClientItemExtensions() {
            @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return LBSwordItemRenderer.INSTANCE;
            }
        };

        consumer.accept(itemRenderProperties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, tooltipContext, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, tooltipContext, tooltip, flag, Upgrade.Type.OTHER);
    }
}
