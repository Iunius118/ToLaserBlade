package com.github.iunius118.tolaserblade.core.laserblade;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public record LaserBladeVisual(ModelType modelType, Coloring coloring) {
    public static final int MODEL_TYPE_NO_MODEL = -1;

    public static LaserBladeVisual of(CompoundTag compoundTag) {
        var modelType = new ModelType(compoundTag);
        var coloring = Coloring.of(compoundTag);
        return new LaserBladeVisual(modelType, coloring);
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

    public void setColorsByBiome(Level level, Holder<Biome> biomeHolder) {
        // Color by Biome type or Biome temperature
        ResourceKey<Level> dimension = level.dimension();

        if (Level.NETHER.equals(dimension)) {
            // The Nether
            setColorsByNetherBiome(level, biomeHolder);
        } else if (Level.END.equals(dimension)) {
            // The End
            getOuterColor().color = LaserBladeColor.WHITE.getBladeColor();
            getOuterColor().isSubtractColor = true;
            getInnerColor().isSubtractColor = true;
        } else {
            // Over-world etc.
            float temp = biomeHolder.value().getBaseTemperature();
            var laserBladeColor = LaserBladeColor.getColorByTemperature(temp);
            getOuterColor().color = laserBladeColor.getBladeColor();
        }
    }

    public void setColorsByNetherBiome(Level level, Holder<Biome> biomeHolder) {
        getOuterColor().color = LaserBladeColor.WHITE.getBladeColor();


        if (compareBiome(biomeHolder, Biomes.SOUL_SAND_VALLEY) ||
                compareBiome(biomeHolder, Biomes.WARPED_FOREST)) {
            getOuterColor().isSubtractColor = true;

        } else {
            getInnerColor().isSubtractColor = true;
        }
    }

    private boolean compareBiome(Holder<Biome> biomeHolder, ResourceKey<Biome> biomeKey) {
        if (biomeHolder == null)
            return false;

        return biomeHolder.is(biomeKey);
    }

    public void write(CompoundTag compound) {
        modelType.write(compound);
        coloring.write(compound);
    }

    /* Inner classes */
    // This is also used in laser blade renderer so be public
    public static class ModelType {
        public int type = MODEL_TYPE_NO_MODEL;

        private static final String KEY_TYPE = "type";

        public ModelType(CompoundTag compound) {
            if (compound.contains(KEY_TYPE, Tag.TAG_INT)) {
                type = compound.getInt(KEY_TYPE);
            }
        }

        public void write(CompoundTag compound) {
            if (type >= 0) {
                compound.putInt(KEY_TYPE, type);
            } else if (compound.contains(KEY_TYPE, Tag.TAG_INT)) {
                compound.remove(KEY_TYPE);
            }
        }
    }


    private record Coloring(BladeColor bladeColor, GripColor gripColor) {
        public static Coloring of(CompoundTag compoundTag) {
            var bladeColor = BladeColor.of(compoundTag);
            var gripColor = GripColor.of(compoundTag);
            return new Coloring(bladeColor, gripColor);
        }

        public void write(CompoundTag compound) {
            bladeColor.write(compound);
            gripColor.write(compound);
        }
    }

    private record BladeColor(PartColor innerColor, PartColor outerColor) {
        private static final String KEY_INNER_COLOR = "colorC";
        private static final String KEY_OUTER_COLOR = "colorH";
        private static final String KEY_IS_INNER_SUB_COLOR = "isSubC";
        private static final String KEY_IS_OUTER_SUB_COLOR = "isSubH";

        public static BladeColor of(CompoundTag compoundTag) {
            var innerColor = new PartColor(compoundTag, KEY_INNER_COLOR, KEY_IS_INNER_SUB_COLOR, LaserBladeColor.WHITE.getBladeColor());
            var outerColor = new PartColor(compoundTag, KEY_OUTER_COLOR, KEY_IS_OUTER_SUB_COLOR, LaserBladeColor.RED.getBladeColor());
            return new BladeColor(innerColor, outerColor);
        }

        public void write(CompoundTag compound) {
            compound.putInt(KEY_INNER_COLOR, innerColor.color);
            compound.putBoolean(KEY_IS_INNER_SUB_COLOR, innerColor.isSubtractColor);
            compound.putInt(KEY_OUTER_COLOR, outerColor.color);
            compound.putBoolean(KEY_IS_OUTER_SUB_COLOR, outerColor.isSubtractColor);
        }
    }

    private record GripColor(PartColor gripColor) {
        private static final String KEY_GRIP_COLOR = "colorG";

        public static GripColor of(CompoundTag compoundTag) {
            var gripColor = new PartColor(compoundTag, KEY_GRIP_COLOR, null, LaserBladeColor.WHITE.getBladeColor());
            return new GripColor(gripColor);
        }
        public int getGripColor() {
            return gripColor.color;
        }

        public void write(CompoundTag compound) {
            compound.putInt(KEY_GRIP_COLOR, gripColor.color);
        }
    }

    public static class PartColor {
        public int color = -1;
        public boolean isSubtractColor = false;

        public PartColor(CompoundTag compound, String colorKey, String subKey, int defaultColor) {
            color = defaultColor;

            if (colorKey != null && compound.contains(colorKey, Tag.TAG_INT)) {
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
