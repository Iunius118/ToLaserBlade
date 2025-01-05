package com.github.iunius118.tolaserblade.client.color.item;

import com.github.iunius118.tolaserblade.core.laserblade.LaserBladeColorPart;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record LaserBladeTintSource(LaserBladeColorPart part) implements ItemTintSource {
    public static final MapCodec<LaserBladeTintSource> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.STRING.fieldOf("part").xmap(LaserBladeColorPart::byPartName, LaserBladeColorPart::getPartName).forGetter(LaserBladeTintSource::part)
            ).apply(instance, LaserBladeTintSource::new)
    );

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
        LaserBladeItemColor color = LaserBladeItemColor.of(stack);

        return switch (part) {
            case INNER_BLADE -> color.simpleInnerColor();
            case OUTER_BLADE -> color.simpleOuterColor();
            case GRIP -> color.simpleGripColor();
        };
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
