package com.github.iunius118.tolaserblade.world.item;

import com.github.iunius118.tolaserblade.client.renderer.item.LBSwordItemRenderer;
import com.github.iunius118.tolaserblade.core.dispenser.DispenseLaserBladeBehavior;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBlade;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeBlocking;
import com.github.iunius118.tolaserblade.core.laserblade.LaserBladePerformance;
import com.github.iunius118.tolaserblade.core.laserblade.upgrade.Upgrade;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
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

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class LBSwordItem extends SwordItem implements LaserBladeItemBase {
    private final Tier tier;
    private final float attackDamage;
    private final float attackSpeed;

    public static Item.Properties properties = (new Item.Properties()).setNoRepair();

    public LBSwordItem(boolean isFireproof) {
        super(ModItemTiers.getLBSwordTier(isFireproof), 3, -1.2F, LaserBladeItemBase.setFireproof(properties, isFireproof));

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
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return LaserBladeBlocking.isShield() && ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
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
                LaserBladeItemUtil.playSwingSound(level, entity, isFireResistant());
            }
        }

        return super.onEntitySwing(stack, entity);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.getCommandSenderWorld();

        if (!level.isClientSide) {
            LaserBladeItemUtil.playSwingSound(level, attacker, isFireResistant());
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    /* Handling Events */

    public void onCriticalHit(CriticalHitEvent event) {
        Entity target = event.getTarget();
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        LaserBladePerformance performance = LaserBlade.performanceOf(stack);
        LaserBladePerformance.AttackPerformance attack = performance.getAttackPerformance();

        if (target instanceof WitherBoss || attack.damage >= LaserBladePerformance.AttackPerformance.MOD_ATK_CRITICAL_BONUS) {
            event.setDamageModifier(event.getDamageModifier() + LaserBladePerformance.AttackPerformance.MOD_CRITICAL_BONUS_VS_WITHER);
        }
    }

    /* Item Characterizing */

    @Override
    public float getDamage() {
        return attackDamage;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(1, entityLiving, (playerEntity) -> playerEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
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
        return enchantment.category == EnchantmentCategory.WEAPON
                || enchantment == Enchantments.BLOCK_EFFICIENCY
                || enchantment == Enchantments.MENDING
                || enchantment == Enchantments.SILK_TOUCH
                || enchantment == Enchantments.VANISHING_CURSE;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();

        if (slot == EquipmentSlot.MAINHAND) {
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
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, tooltip, flag);
        LaserBladeItemUtil.addLaserBladeInformation(itemStack, level, tooltip, flag, Upgrade.Type.OTHER);
    }
}
