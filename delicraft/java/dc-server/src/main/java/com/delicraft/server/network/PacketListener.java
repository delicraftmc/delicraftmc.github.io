package com.delicraft.server.network;

import com.delicraft.common.network.DeliCraftChannel;
import com.delicraft.common.packet.DLPacket;
import com.delicraft.common.packet.PacketRegistry;
import com.delicraft.server.di.ServerInjector;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketListener implements PluginMessageListener {
    private static final Logger log = LoggerFactory.getLogger(PacketListener.class);
    private final PacketRegistry registry;
    private final HandshakeHandler handshakeHandler;

    public PacketListener(ServerInjector di) {
        this.registry = new PacketRegistry();
        this.handshakeHandler = new HandshakeHandler(registry);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!DeliCraftChannel.CHANNEL.equals(channel)) return;

        try {
            DLPacket decoded = registry.decode(message);
            DLPacket response = switch (decoded) {
                case DLPacket.HandshakeRequest req -> handshakeHandler.handle(req);
                case DLPacket.KeepAlivePacket alive -> new DLPacket.KeepAlivePacket(alive.timestamp());
                default -> {
                    log.warn("Unknown packet from {}: {}", player.getName(), decoded.getClass().getSimpleName());
                    yield null;
                }
            };

            if (response != null) {
                byte[] responseData = registry.encode(response);
                player.sendPluginMessage(
                    null, DeliCraftChannel.CHANNEL, responseData);
            }
        } catch (Exception e) {
            log.error("Packet error from {}: {}", player.getName(), e.getMessage());
        }
    }
}
