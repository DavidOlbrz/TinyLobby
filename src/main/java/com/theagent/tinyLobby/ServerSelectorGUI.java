package com.theagent.tinyLobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class ServerSelectorGUI implements Listener {

    private final ConfigurationManager configManager;
    private Inventory gui;

    public ServerSelectorGUI(ConfigurationManager configManager) {
        this.configManager = configManager;
    }

    /**
     * Adds all items to the GUI
     */
    public void initialize() {
        gui = Bukkit.createInventory(null, 54, Component.text(configManager.getTitle()));
        // exit server
        gui.setItem(
                gui.getSize() - 1,
                createItem(Material.BARRIER, Names.EXIT_ITEM_NAME, NamedTextColor.RED)
        );
        // servers
        addServers();
    }

    /**
     * Opens the GUI for a specific player
     *
     * @param player Player to open GUI
     */
    public void open(Player player) {
        initialize();
        player.openInventory(gui);
    }

    /**
     * Add all configured servers to the GUI
     */
    private void addServers() {
        Set<String> servers = configManager.getServers();

        servers.forEach(server -> {
            Server info = configManager.getServerInfo(server);
            gui.setItem(
                    Integer.parseInt(server),
                    createItem(info.getItem(), info.getName(), NamedTextColor.WHITE)
            );
        });
    }

    /**
     * Creates an item which can be added to the GUI
     *
     * @param material  Item
     * @param name      Display Name
     * @param nameColor Text Color
     * @return Item
     */
    public ItemStack createItem(
            @NotNull final Material material,
            @Nullable final String name,
            @Nullable final TextColor nameColor
    ) {
        final ItemStack item = new ItemStack(material, 1);

        if (name != null && nameColor != null) {
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text(name, nameColor));
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Check if a player clicked on GUI item
     *
     * @param event InventoryClickEvent
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // check if it is the correct GUI
        if (Names.TextComponentToString(event.getView().title()).equals(Names.SERVER_SELECTOR_GUI_TITLE)) {
            event.setCancelled(true); // cancel movement of items

            int slot = event.getRawSlot(); // get the slot that was clicked

            // check if an item was clicked
            if (event.getView().getItem(slot) == null) {
                return;
            }

            Player player = (Player) event.getWhoClicked(); // get the player who clicked

            // check if "exit server" was clicked
            if (slot == gui.getSize() - 1) {
                player.kick(Component.text(configManager.getDisconnectMessage()), PlayerKickEvent.Cause.PLUGIN);
                return;
            }

            // send player to the selected server
            Server clickedServer = configManager.getServerInfo("" + slot);
            PlayerSender.send(player, clickedServer.getProxyName());
        }
    }

}
