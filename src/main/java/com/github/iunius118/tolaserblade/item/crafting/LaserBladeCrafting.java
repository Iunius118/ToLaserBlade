package com.github.iunius118.tolaserblade.item.crafting;

import com.github.iunius118.tolaserblade.enchantment.ModEnchantments;
import com.github.iunius118.tolaserblade.item.LaserBladeItemBase;
import com.github.iunius118.tolaserblade.item.ModItems;
import com.github.iunius118.tolaserblade.item.upgrade.LaserBladeUpgrade;
import com.google.common.collect.Maps;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.NetherBiome;
import net.minecraft.world.biome.TheEndBiome;
import net.minecraft.world.biome.TheVoidBiome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.Map;

public class LaserBladeCrafting {
    private final ItemStack result;
    private final LaserBladeItemBase resultItem;
    private int bladeGripColor = LaserBladeItemBase.DEFAULT_COLOR_GRIP;
    private int bladeInnerColor = LaserBladeItemBase.DEFAULT_COLOR_INNER;
    private int bladeOuterColor = LaserBladeItemBase.DEFAULT_COLOR_OUTER;
    private boolean isBladeInnerSubColor = false;
    private boolean isBladeOuterSubColor = false;
    private float attackDamage = 0.0F;
    private float attackSpeed = 0.0F;
    private final Map<Enchantment, Integer> enchantments = Maps.newLinkedHashMap();
    private ITextComponent name;

    public LaserBladeCrafting(PlayerEvent.ItemCraftedEvent event, LaserBladeItemBase resultItemIn) {
        result = event.getCrafting();
        resultItem = resultItemIn;
        IInventory inventory = event.getInventory();

        if (resultItem.canUpgrade(LaserBladeUpgrade.Type.MEDIUM)) {
            // Add Light Element I to Laser Medium
            enchantments.put(ModEnchantments.LIGHT_ELEMENT, LaserBladeItemBase.LVL_LIGHT_ELEMENT_1);
            // Biome colors
            modifyColorsByBiome(event.getPlayer());

        } else if (resultItem.canUpgrade(LaserBladeUpgrade.Type.EMITTER)) {
            // Biome colors
            modifyColorsByBiome(event.getPlayer());
        }


        if (inventory != null) {
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack ingredient = inventory.getStackInSlot(i);

                if (!ingredient.isEmpty()) {
                    Item item = ingredient.getItem();

                    if (item instanceof LaserBladeItemBase) {
                        addEnchantments(ingredient);
                        setColors(ingredient);
                        setAttackDamage(ingredient);
                        setAttackSpeed(ingredient);
                        setName(ingredient);

                    } else if (resultItem.canUpgrade(LaserBladeUpgrade.Type.BATTERY) && Tags.Items.DUSTS_REDSTONE.contains(item)) {
                        // Add Efficiency I to LB Energy Cell by Redstone
                        addEnchantment(Enchantments.EFFICIENCY, 1);

                    } else if (resultItem.canUpgrade(LaserBladeUpgrade.Type.MEDIUM) && Tags.Items.DUSTS_GLOWSTONE.contains(item)) {
                        // Add Light Element II to Laser Medium by Glowstone Dust
                        addEnchantment(ModEnchantments.LIGHT_ELEMENT, LaserBladeItemBase.LVL_LIGHT_ELEMENT_2);
                    }
                }
            }
        }
    }

    public ItemStack getResult() {
        CompoundNBT nbt = result.getOrCreateTag();

        if (resultItem.canUpgrade(LaserBladeUpgrade.Type.BATTERY)) {
            nbt.putFloat(LaserBladeItemBase.KEY_SPD, attackSpeed);
        }

        if (resultItem.canUpgrade(LaserBladeUpgrade.Type.MEDIUM)) {
            nbt.putFloat(LaserBladeItemBase.KEY_ATK, attackDamage);
            nbt.putInt(LaserBladeItemBase.KEY_OUTER_COLOR, bladeOuterColor);
            nbt.putBoolean(LaserBladeItemBase.KEY_IS_OUTER_SUB_COLOR, isBladeOuterSubColor);
        }

        if (resultItem.canUpgrade(LaserBladeUpgrade.Type.EMITTER)) {
            nbt.putInt(LaserBladeItemBase.KEY_INNER_COLOR, bladeInnerColor);
            nbt.putBoolean(LaserBladeItemBase.KEY_IS_INNER_SUB_COLOR, isBladeInnerSubColor);
        }

        if (resultItem.canUpgrade(LaserBladeUpgrade.Type.CASING)) {
            nbt.putInt(LaserBladeItemBase.KEY_GRIP_COLOR, bladeGripColor);

            if (name != null) {
                result.setDisplayName(name);
            }
        }

        EnchantmentHelper.setEnchantments(enchantments, result);

        return result;
    }

    private void addEnchantment(Enchantment enchantment, int level) {
        if (enchantments.containsKey(enchantment)) {
            if (enchantments.get(enchantment) < level) {
                enchantments.put(enchantment, level);
            }

        } else {
            enchantments.put(enchantment, level);
        }
    }

    private void addEnchantments(ItemStack ingredient) {
        Map<Enchantment, Integer> ingredientEnchantments = EnchantmentHelper.getEnchantments(ingredient);

        ingredientEnchantments.forEach((e, lvl) -> {
            if (enchantments.containsKey(e)) {
                if (enchantments.get(e) < lvl) {
                    enchantments.put(e, lvl);
                }

            } else {
                enchantments.put(e, lvl);
            }
        });
    }

    private void setAttackDamage(ItemStack ingredient) {
        if (ingredient.hasTag() && ingredient.getTag().contains(LaserBladeItemBase.KEY_ATK, Constants.NBT.TAG_FLOAT)) {
            attackDamage = Math.max(attackDamage, ModItems.LB_MEDIUM.getLaserBladeATK(ingredient));
        }
    }

    private void setAttackSpeed(ItemStack ingredient) {
        if (ingredient.hasTag() && ingredient.getTag().contains(LaserBladeItemBase.KEY_SPD, Constants.NBT.TAG_FLOAT)) {
            attackSpeed = Math.max(attackSpeed, ModItems.LB_BATTERY.getLaserBladeSPD(ingredient));
        }
    }

    private void setColors(ItemStack ingredient) {
        CompoundNBT nbt = ingredient.getTag();

        if (nbt == null) {
            return;
        }

        if (nbt.contains(LaserBladeItemBase.KEY_GRIP_COLOR, Constants.NBT.TAG_INT)) {
            bladeGripColor = nbt.getInt(LaserBladeItemBase.KEY_GRIP_COLOR);
        }

        if (nbt.contains(LaserBladeItemBase.KEY_INNER_COLOR, Constants.NBT.TAG_INT)) {
            bladeInnerColor = nbt.getInt(LaserBladeItemBase.KEY_INNER_COLOR);
        }

        if (nbt.contains(LaserBladeItemBase.KEY_OUTER_COLOR, Constants.NBT.TAG_INT)) {
            bladeOuterColor = nbt.getInt(LaserBladeItemBase.KEY_OUTER_COLOR);
        }

        if (nbt.contains(LaserBladeItemBase.KEY_IS_INNER_SUB_COLOR, Constants.NBT.TAG_BYTE)) {
            isBladeInnerSubColor = nbt.getBoolean(LaserBladeItemBase.KEY_IS_INNER_SUB_COLOR);
        }

        if (nbt.contains(LaserBladeItemBase.KEY_IS_OUTER_SUB_COLOR, Constants.NBT.TAG_BYTE)) {
            isBladeOuterSubColor = nbt.getBoolean(LaserBladeItemBase.KEY_IS_OUTER_SUB_COLOR);
        }
    }

    private void setName(ItemStack ingredient) {
        Item item = ingredient.getItem();

        if (item instanceof LaserBladeItemBase && ((LaserBladeItemBase)item).canUpgrade(LaserBladeUpgrade.Type.CASING)) {
            if (ingredient.hasDisplayName()) {
                name = ingredient.getDisplayName();
            }
        }
    }


    private void modifyColorsByBiome(PlayerEntity player) {
        World world = player.world;
        Biome biome = world.getBiome(player.getPosition());

        // Dyeing by Biome type or Biome temperature
        if (world.getDimension().getType() == DimensionType.THE_NETHER || biome instanceof NetherBiome) {
            // Nether
            isBladeInnerSubColor = true;
        } else if (world.getDimension().getType() == DimensionType.THE_END || biome instanceof TheEndBiome) {
            // The End
            isBladeOuterSubColor = true;
        } else if (biome instanceof TheVoidBiome) {
            // The Void

        } else {
            // Biomes on Overworld or the other dimensions
            float temp = biome.getDefaultTemperature();

            if (temp > 1.5F) {
                // t > 1.5
                bladeOuterColor = LaserBladeItemBase.LBColor.TEMP_DESERT.getBladeColor();
            } else if (temp > 1.0F) {
                // 1.5 >= t > 1.0
                bladeOuterColor = LaserBladeItemBase.LBColor.TEMP_SAVANNA.getBladeColor();
            } else if (temp > 0.8F) {
                // 1.0 >= t > 0.8
                bladeOuterColor = LaserBladeItemBase.LBColor.TEMP_JUNGLE.getBladeColor();
            } else if (temp >= 0.5F) {
                // 0.8 >= t >= 0.5
                bladeOuterColor = LaserBladeItemBase.LBColor.RED.getBladeColor();
            } else if (temp >= 0.2F) {
                // 0.5 > t >= 0.2
                bladeOuterColor = LaserBladeItemBase.LBColor.TEMP_TAIGA.getBladeColor();
            } else if (temp >= -0.25F) {
                // 0.2 > t >= -0.25
                bladeOuterColor = LaserBladeItemBase.LBColor.TEMP_ICE_PLAIN.getBladeColor();
            } else {
                // -0.25 > t
                bladeOuterColor = LaserBladeItemBase.LBColor.TEMP_SNOWY_TAIGA.getBladeColor();
            }
        }
    }
}
