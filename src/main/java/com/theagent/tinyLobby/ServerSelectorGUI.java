package com.theagent.tinyLobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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
                createItem(Material.BARRIER, Names.EXIT_ITEM_NAME, NamedTextColor.RED, null)
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
                    createItem(info.getItem(), info.getName(), NamedTextColor.WHITE, info.getLore())
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
            @Nullable final TextColor nameColor,
            @Nullable final List<Component> lore
    ) {
        final ItemStack item = new ItemStack(material, 1);

        if (name != null && nameColor != null) {
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text(name, nameColor));

            if (lore != null) {
                meta.lore(lore);
            }

            item.setItemMeta(meta);
        }

        return item;
    }

    public int getSize() {
        return gui.getSize();
    }

}
