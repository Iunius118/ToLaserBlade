package com.github.iunius118.tolaserblade.item;

import com.github.iunius118.tolaserblade.item.enchantment.ModEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class LBSwordItem  extends Item {
    public final boolean fireResistant;

    public LBSwordItem(Properties properties, boolean fireResistant) {
        super(properties);
        this.fireResistant = fireResistant;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return true;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);
        var consumable = itemStack.get(DataComponents.CONSUMABLE);

        // Follow vanilla behavior while allowing laser blades to block attacks when possible
        if (consumable != null) {
            return consumable.startConsuming(player, itemStack, hand);
        }

        Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);

        if (equippable != null && equippable.swappable()) {
            return equippable.swapWithEquipmentSlot(itemStack, player);
        } else if (canBlockAttacks(level, itemStack)) {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        } else {
            var kineticWeapon = itemStack.get(DataComponents.KINETIC_WEAPON);

            if (kineticWeapon != null) {
                player.startUsingItem(hand);
                kineticWeapon.makeSound(player);
                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        var consumable = itemStack.get(DataComponents.CONSUMABLE);

        // Follow vanilla behavior while allowing laser blades to block attacks when possible
        if (consumable != null) {
            return consumable.animation();
        } else  if (canBlockAttacks(null, itemStack)) {
            return ItemUseAnimation.BLOCK;
        } else if (itemStack.has(DataComponents.KINETIC_WEAPON)) {
            return ItemUseAnimation.SPEAR;
        } else {
            return ItemUseAnimation.NONE;
        }
    }

    /**
     * Determines whether the given item stack can block attacks.
     */
    private boolean canBlockAttacks(@Nullable Level level, ItemStack itemStack) {
        var itemEnchantments = itemStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        Optional<? extends Holder<Enchantment>> repulsiveForce;

        // Get Repulsive Force enchantment holder
        if (level != null) {
            var enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            repulsiveForce = enchantments.get(ModEnchantments.REPULSIVE_FORCE);
        } else {
            repulsiveForce = itemEnchantments.keySet().stream()
                    .filter(e -> e.is(ModEnchantments.REPULSIVE_FORCE))
                    .findAny();
        }

        // The item stack can block attacks if it is enchanted with Repulsive Force
        if (repulsiveForce.isPresent() && itemEnchantments.getLevel(repulsiveForce.get()) > 0) {
            return true;
        }

        // The item stack can block attacks if the Blocks Attacks component has been applied to it by player
        var blocksAttacks = itemStack.getComponentsPatch().getPatch(DataComponents.BLOCKS_ATTACKS);
        return blocksAttacks != null && blocksAttacks.isPresent();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display,
                                Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);

        if (fireResistant) {
            builder.accept(Component.translatable("tooltip.tolaserblade.fire_resistant")
                    .withStyle(ChatFormatting.GOLD));
        }
    }
}
