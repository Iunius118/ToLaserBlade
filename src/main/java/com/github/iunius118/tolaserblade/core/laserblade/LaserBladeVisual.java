package com.github.iunius118.tolaserblade.core.laserblade;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class LaserBladeVisual {
    public static final int MODEL_TYPE_DEFAULT = LaserBlade.TYPE_DEFAULT;
    public static final String KEY_MODEL_TYPE = LaserBlade.KEY_TYPE;
    public static final String KEY_INNER_COLOR = "colorC";
    public static final String KEY_IS_INNER_SUB_COLOR = "isSubC";
    public static final String KEY_OUTER_COLOR = "colorH";
    public static final String KEY_IS_OUTER_SUB_COLOR = "isSubH";
    public static final String KEY_GRIP_COLOR = "colorG";

    private final CompoundTag tag;

    public LaserBladeVisual(ItemStack itemStack) {
        // Read only, don't create any tag
        tag = itemStack.getTag();
    }

    public LaserBladeVisual(CompoundTag compoundTag) {
        tag = compoundTag;
    }

    public static LaserBladeVisual of(ItemStack itemStack) {
        return new LaserBladeVisual(itemStack);
    }

    public int getModelType() {
        if (tag != null) {
            return Math.max(tag.getInt(KEY_MODEL_TYPE), 0);
        }

        return MODEL_TYPE_DEFAULT;
    }

    public boolean innerColorExists() {
        return tag != null && tag.contains(KEY_INNER_COLOR, Tag.TAG_INT);
    }

    public int getInnerColor() {
        if (innerColorExists()) {
            return tag.getInt(KEY_INNER_COLOR);
        }

        return LaserBladeColor.WHITE.getBladeColor();
    }

    public boolean isInnerSubColor() {
        if (tag != null) {
            return tag.getBoolean(KEY_IS_INNER_SUB_COLOR);
        }

        return false;
    }

    public boolean outerColorExists() {
        return tag != null && tag.contains(KEY_OUTER_COLOR, Tag.TAG_INT);
    }

    public int getOuterColor() {
        if (outerColorExists()) {
            return tag.getInt(KEY_OUTER_COLOR);
        }

        return LaserBladeColor.RED.getBladeColor();
    }

    public boolean isOuterSubColor() {
        if (tag != null) {
            return tag.getBoolean(KEY_IS_OUTER_SUB_COLOR);
        }

        return false;
    }

    public boolean gripColorExists() {
        return tag != null && tag.contains(KEY_GRIP_COLOR, Tag.TAG_INT);
    }

    public int getGripColor() {
        if (gripColorExists()) {
            return tag.getInt(KEY_GRIP_COLOR);
        }

        return LaserBladeColor.WHITE.getGripColor();
    }

    public static class Writer {
        private final CompoundTag tag;

        private Writer(CompoundTag tag) {
            this.tag = tag;
        }

        public static LaserBladeVisual.Writer of(ItemStack itemStack) {
            return new LaserBladeVisual.Writer(itemStack.getOrCreateTag());
        }

        public Writer writeModelType(int modelType) {
            tag.putInt(KEY_MODEL_TYPE, Math.max(modelType, 0));
            return this;
        }

        public Writer writeInnerColor(int color) {
            tag.putInt(KEY_INNER_COLOR, color);
            return this;
        }

        public Writer writeIsInnerSubColor(boolean isSubColor) {
            tag.putBoolean(KEY_IS_INNER_SUB_COLOR, isSubColor);
            return this;
        }

        public Writer switchIsInnerSubColor() {
            tag.putBoolean(KEY_IS_INNER_SUB_COLOR, !tag.getBoolean(KEY_IS_INNER_SUB_COLOR));
            return this;
        }

        public Writer writeOuterColor(int color) {
            tag.putInt(KEY_OUTER_COLOR, color);
            return this;
        }

        public Writer writeIsOuterSubColor(boolean isSubColor) {
            tag.putBoolean(KEY_IS_OUTER_SUB_COLOR, isSubColor);
            return this;
        }

        public Writer switchIsOuterSubColor() {
            tag.putBoolean(KEY_IS_OUTER_SUB_COLOR, !tag.getBoolean(KEY_IS_OUTER_SUB_COLOR));
            return this;
        }

        public Writer writeGripColor(int color) {
            tag.putInt(KEY_GRIP_COLOR, color);
            return this;
        }

        // Color by Biome type or Biome temperature
        public LaserBladeVisual.Writer setColorsByBiome(Level level, Holder<Biome> biomeHolder) {
            ResourceKey<Level> dimension = level.dimension();

            if (Level.NETHER.equals(dimension)) {
                // The Nether
                setColorsByNetherBiome(level, biomeHolder);
            } else if (Level.END.equals(dimension)) {
                // The End
                setColorsByEndBiome(level, biomeHolder);
            } else {
                // Over-world etc.
                setColorsByOverWorldBiome(level, biomeHolder);
            }

            return this;
        }

        private void setColorsByNetherBiome(Level level, Holder<Biome> biomeHolder) {
            this.writeOuterColor(LaserBladeColor.WHITE.getBladeColor());

            if (compareBiomes(biomeHolder, Biomes.SOUL_SAND_VALLEY) || compareBiomes(biomeHolder, Biomes.WARPED_FOREST)) {
                this.writeIsOuterSubColor(true);
            } else {
                this.writeIsInnerSubColor(true);
            }
        }

        private void setColorsByEndBiome(Level level, Holder<Biome> biomeHolder) {
            this.writeOuterColor(LaserBladeColor.WHITE.getBladeColor())
                    .writeIsOuterSubColor(true)
                    .writeIsInnerSubColor(true);
        }

        private void setColorsByOverWorldBiome(Level level, Holder<Biome> biomeHolder) {
            if (compareBiomes(biomeHolder, Biomes.DEEP_DARK)) {
                // Deep dark biome
                setDeepDarkColors();
            } else if (compareBiomes(biomeHolder, Biomes.CHERRY_GROVE)) {
                // Cherry grove biome
                setCherryGroveColors();
            } else {
                float temp = biomeHolder.value().getBaseTemperature();
                int color = LaserBladeColor.getColorByTemperature(temp).getBladeColor();
                this.writeOuterColor(color);
            }
        }

        private void setDeepDarkColors() {
            this.writeOuterColor(LaserBladeColor.CYAN.getBladeColor())
                    .writeInnerColor(0xFFFADCD7)    // Sculk's deep dark blue (negative)
                    .writeIsInnerSubColor(true)
                    .writeGripColor(0xFF052328)     // Sculk's deep dark blue
                    .writeModelType(2);
        }

        private void setCherryGroveColors() {
            this.writeOuterColor(LaserBladeColor.PINK.getBladeColor())
                    .writeInnerColor(0xFFFADCF0)    // Cherry blossom's light pink
                    .writeGripColor(0xFF4B2D3C);    // Cherry log's dark brown
        }

        private boolean compareBiomes(Holder<Biome> biomeHolder, ResourceKey<Biome> biomeKey) {
            if (biomeHolder == null)
                return false;

            return biomeHolder.is(biomeKey);
        }
    }
}
