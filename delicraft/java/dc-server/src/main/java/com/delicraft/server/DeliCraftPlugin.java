package com.delicraft.server;

import com.delicraft.common.network.DeliCraftChannel;
import com.delicraft.server.di.ServerInjector;
import com.delicraft.server.network.PacketListener;
import org.bukkit.plugin.java.JavaPlugin;

public class DeliCraftPlugin extends JavaPlugin {
    private DLiPlugin plugin;
    private PacketListener packetListener;
    private ServerInjector serverInjector;

    @Override
    public void onLoad() {
        serverInjector = new ServerInjector(getDataFolder().toPath());
        plugin = new DLiPlugin(serverInjector);
        plugin.onLoad();
    }

    @Override
    public void onEnable() {
        plugin.onEnable();

        packetListener = new PacketListener(serverInjector);
        getServer().getMessenger().registerIncomingPluginChannel(
            this, DeliCraftChannel.CHANNEL, packetListener);
        getServer().getMessenger().registerOutgoingPluginChannel(
            this, DeliCraftChannel.CHANNEL);
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterIncomingPluginChannel(this);
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        plugin.onDisable();
        plugin = null;
    }
}
