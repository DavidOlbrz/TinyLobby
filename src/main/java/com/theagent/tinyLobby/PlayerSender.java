package com.theagent.tinyLobby;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

public class PlayerSender {

    private static TinyLobby plugin;

    /**
     * Sets the plugin so the player can use the plugin
     * channel to communicate with the proxy server
     *
     * @param plugin TinyLobby
     */
    public static void setPlugin(TinyLobby plugin) {
        PlayerSender.plugin = plugin;
    }

    /**
     * Send a player to a specific server
     *
     * @param player     Player to be transferred
     * @param serverName Name of the destination server
     */
    public static void send(Player player, String serverName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

}
