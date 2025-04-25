package com.theagent.tinyLobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final ConfigurationManager config;
    private final ServerSelectorGUI gui;

    public PlayerListener(ConfigurationManager config, ServerSelectorGUI gui) {
        this.config = config;
        this.gui = gui;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // check if the player is an Op and if the plugin was recently updated
        if (player.isOp() && config.getWasUpdated() > 0) {
            player.sendMessage(
                    TinyLobby.MESSAGE_PREFIX.append(Component.text(
                            "TinyLobby was updated, you should reconfigure the plugin config!",
                            NamedTextColor.GOLD
                    ))
            );
            // decrease counter lowering the amount of times the message will be shown
            config.decreaseWasUpdated();
        }

        // players allowed to close the GUI -> open GUI once
        if (config.getAllowClosing()) {
            gui.open(player);
        }
    }

}
