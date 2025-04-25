package com.theagent.tinyLobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerListener implements Listener {

    private final ConfigurationManager config;
    private final ServerSelectorGUI gui;
    private final ServerSelectorItem selectorItem;

    public PlayerListener(
            ConfigurationManager config,
            ServerSelectorGUI gui,
            ServerSelectorItem selectorItem
    ) {
        this.config = config;
        this.gui = gui;
        this.selectorItem = selectorItem;
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

        if (config.getAllowClosing()) {
            // players allowed to close the GUI -> open GUI once
            gui.open(player);
            // add item to manually open GUI
            selectorItem.addItem(player);
        }
    }

    /**
     * Open the Server Selector GUI when a player right-clicks on the selector item
     *
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        event.setCancelled(true);

        if (event.getAction().isRightClick()) {
            ItemStack usedItem = event.getItem();
            if (usedItem != null && PlainTextComponentSerializer.plainText().serialize(
                    Objects.requireNonNull(usedItem.getItemMeta().displayName())).equals(config.getSelectorName())
            ) {
                gui.open(event.getPlayer());
            }
        }
    }

    /**
     * Check if a player clicked on a GUI item
     *
     * @param event InventoryClickEvent
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // cancel event
        event.setCancelled(true);

        // check if it is the correct GUI
        if (Names.TextComponentToString(event.getView().title()).equals(config.getTitle())) {

            // ignore if item in quick bar was clicked
            if (event.getSlotType().equals(InventoryType.SlotType.QUICKBAR)) {
                return;
            }

            int slot = event.getRawSlot(); // get the slot that was clicked

            // check if an item was clicked
            if (event.getView().getItem(slot) == null) {
                return;
            }

            Player player = (Player) event.getWhoClicked(); // get the player who clicked

            // check if "exit server" was clicked
            if (slot == gui.getSize() - 1) {
                player.kick(Component.text(config.getDisconnectMessage()), PlayerKickEvent.Cause.PLUGIN);
                return;
            }

            // send the player to the selected server
            Server clickedServer = config.getServerInfo("" + slot);
            PlayerSender.send(player, clickedServer.getProxyName());
        }
    }

    /**
     * Prevent players from dropping items
     *
     * @param event PlayerDropItemEvent
     */
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

}
