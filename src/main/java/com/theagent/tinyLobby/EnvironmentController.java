package com.theagent.tinyLobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EnvironmentController implements Listener {

    /**
     * Players should not be able to move
     *
     * @param event PlayerMoveEvent
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        event.setCancelled(true);
    }

}
