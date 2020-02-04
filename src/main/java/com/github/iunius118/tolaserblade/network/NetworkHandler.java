package com.github.iunius118.tolaserblade.network;

import com.github.iunius118.tolaserblade.ToLaserBlade;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String NETWORK_VERSION = "TLBv2";

    private static final ResourceLocation CONFIG_CHANNEL_RESOURCE = new ResourceLocation(ToLaserBlade.MOD_ID, "config");
    private static final SimpleChannel CONFIG_CHANNEL;

    static {
        CONFIG_CHANNEL = NetworkRegistry.ChannelBuilder
                .named(CONFIG_CHANNEL_RESOURCE)
                .clientAcceptedVersions(v -> true)
                .serverAcceptedVersions(v -> true)
                .networkProtocolVersion(() -> NETWORK_VERSION)
                .simpleChannel();

        CONFIG_CHANNEL.messageBuilder(ServerConfigMessage.class, 0)
                .encoder(ServerConfigMessage::encode)
                .decoder(ServerConfigMessage::decode)
                .consumer(ServerConfigMessage::handle)
                .add();
    }

    public SimpleChannel getConfigChannel() {
        return CONFIG_CHANNEL;
    }
}
