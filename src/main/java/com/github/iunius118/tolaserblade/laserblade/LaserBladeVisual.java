package com.github.iunius118.tolaserblade.laserblade;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.util.Constants;

public class LaserBladeVisual {
    private final ModelType modelType;
    private final Coloring coloring;

    public static final int MODEL_TYPE_NO_MODEL = -1;

    public LaserBladeVisual(CompoundNBT compound) {
        modelType = new ModelType(compound);
        coloring = new Coloring(compound);
    }

    public int getModelType() {
        return modelType.type;
    }

    public PartColor getInnerColor() {
        return coloring.bladeColor.innerColor;
    }

    public PartColor getOuterColor() {
        return coloring.bladeColor.outerColor;
    }

    public PartColor getGripColor() {
        return coloring.gripColor.gripColor;
    }

    public void setModelType(int type) {
        modelType.type = type;
    }

    public void setColorsByBiome(World world, Biome biome) {
        // Color by Biome type or Biome temperature
        if (biome.getBiomeCategory() == Biome.Category.NETHER) {
            // The Nether
            setColorsByNetherBiome(world, biome);

        } else if (biome.getBiomeCategory() == Biome.Category.THEEND) {
            // The End
            getOuterColor().color = Color.WHITE.getBladeColor();
            getOuterColor().isSubtractColor = true;
            getInnerColor().isSubtractColor = true;

        } else {
            // Biomes on Over-world etc.
            float temp = biome.getBaseTemperature();
            setColorsByTemperature(temp);
        }
    }

    public void setColorsByNetherBiome(World world, Biome biome) {
        getOuterColor().color = Color.WHITE.getBladeColor();

        if (compareBiome(world, biome, Biomes.SOUL_SAND_VALLEY) ||
                compareBiome(world, biome, Biomes.WARPED_FOREST)) {
            getOuterColor().isSubtractColor = true;

        } else {
            getInnerColor().isSubtractColor = true;
        }
    }

    private boolean compareBiome(World world, Biome biome, RegistryKey<Biome> biomeKey) {
        if (world == null || biome == null || biomeKey == null) return false;

        DynamicRegistries registries = world.registryAccess();   // TODO: registryAccess = getDynamicRegistries ?
        MutableRegistry<Biome> biomes = registries.registryOrThrow(Registry.BIOME_REGISTRY);
        ResourceLocation biome1 = biomes.getKey(biome);
        ResourceLocation biome2 = biomeKey.location();

        return biome2.equals(biome1);
    }

    public void setColorsByTemperature(float temp) {
        if (temp > 1.5F) {
            // t > 1.5
            getOuterColor().color = Color.TEMP_DESERT.getBladeColor();
        } else if (temp > 1.0F) {
            // 1.5 >= t > 1.0
            getOuterColor().color = Color.TEMP_SAVANNA.getBladeColor();
        } else if (temp > 0.8F) {
            // 1.0 >= t > 0.8
            getOuterColor().color = Color.TEMP_JUNGLE.getBladeColor();
        } else if (temp >= 0.5F) {
            // 0.8 >= t >= 0.5
            getOuterColor().color = Color.RED.getBladeColor();
        } else if (temp >= 0.2F) {
            // 0.5 > t >= 0.2
            getOuterColor().color = Color.TEMP_TAIGA.getBladeColor();
        } else if (temp >= -0.25F) {
            // 0.2 > t >= -0.25
            getOuterColor().color = Color.TEMP_ICE_PLAIN.getBladeColor();
        } else {
            // -0.25 > t
            getOuterColor().color = Color.TEMP_SNOWY_TAIGA.getBladeColor();
        }
    }

    public void write(CompoundNBT compound) {
        modelType.write(compound);
        coloring.write(compound);
    }

    /* Inner classes */
    public static class ModelType { // This is also used in laser blade renderer so be public
        public int type = MODEL_TYPE_NO_MODEL;

        private static final String KEY_TYPE = "type";

        public ModelType(CompoundNBT compound) {
            if (compound.contains(KEY_TYPE, Constants.NBT.TAG_INT)) {
                type = compound.getInt(KEY_TYPE);
            }
        }

        public void write(CompoundNBT compound) {
            if (type >= 0) {
                compound.putInt(KEY_TYPE, type);
            } else if (compound.contains(KEY_TYPE, Constants.NBT.TAG_INT)) {
                compound.remove(KEY_TYPE);
            }
        }
    }

    private static class Coloring {
        private final BladeColor bladeColor;
        private final GripColor gripColor;

        public Coloring(CompoundNBT compound) {
            bladeColor = new BladeColor(compound);
            gripColor = new GripColor(compound);
        }

        public void write(CompoundNBT compound) {
            bladeColor.write(compound);
            gripColor.write(compound);
        }
    }

    private static class BladeColor {
        public PartColor innerColor;
        public PartColor outerColor;

        private static final String KEY_INNER_COLOR = "colorC";
        private static final String KEY_OUTER_COLOR = "colorH";
        private static final String KEY_IS_INNER_SUB_COLOR = "isSubC";
        private static final String KEY_IS_OUTER_SUB_COLOR = "isSubH";

        public BladeColor(CompoundNBT compound) {
            innerColor = new PartColor(compound, KEY_INNER_COLOR, KEY_IS_INNER_SUB_COLOR, Color.WHITE.getBladeColor());
            outerColor = new PartColor(compound, KEY_OUTER_COLOR, KEY_IS_OUTER_SUB_COLOR, Color.RED.getBladeColor());
        }

        public void write(CompoundNBT compound) {
            compound.putInt(KEY_INNER_COLOR, innerColor.color);
            compound.putBoolean(KEY_IS_INNER_SUB_COLOR, innerColor.isSubtractColor);
            compound.putInt(KEY_OUTER_COLOR, outerColor.color);
            compound.putBoolean(KEY_IS_OUTER_SUB_COLOR, outerColor.isSubtractColor);
        }
    }

    private static class GripColor {
        private PartColor gripColor;

        private static final String KEY_GRIP_COLOR = "colorG";

        public GripColor(CompoundNBT compound) {
            gripColor = new PartColor(compound, KEY_GRIP_COLOR, null, Color.WHITE.getBladeColor());
        }

        public int getGripColor() {
            return gripColor.color;
        }

        public void write(CompoundNBT compound) {
            compound.putInt(KEY_GRIP_COLOR, gripColor.color);
        }
    }

    public static class PartColor {
        public int color = -1;
        public boolean isSubtractColor = false;

        public PartColor(CompoundNBT compound, String colorKey, String subKey, int defaultColor) {
            color = defaultColor;

            if (colorKey != null && compound.contains(colorKey, Constants.NBT.TAG_INT)) {
                color = compound.getInt(colorKey);
            }

            if (subKey != null) {
                isSubtractColor = compound.getBoolean(subKey);
            }
        }

        public void switchBlendMode() {
            isSubtractColor = !isSubtractColor;
        }
    }
}
