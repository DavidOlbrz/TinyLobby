package com.theagent.tinyLobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ServerSelectorItem {

    private final ConfigurationManager config;
    private final ServerSelectorGUI gui;
    private final ItemStack selectorItem;

    public ServerSelectorItem(ConfigurationManager configManager, ServerSelectorGUI gui) {
        this.config = configManager;
        this.gui = gui;

        this.selectorItem = createItem();
    }

    public ItemStack createItem() {
        ItemStack selectorItem = new ItemStack(Material.valueOf(config.getSelectorItem()));
        ItemMeta selectorMeta = selectorItem.getItemMeta();
        selectorMeta.displayName(Component.text(config.getSelectorName()));
        selectorItem.setItemMeta(selectorMeta);
        return selectorItem;
    }

    public void addItem(Player player) {
        player.getInventory().setItem(config.getSelectorSlot(), selectorItem);
    }

}
