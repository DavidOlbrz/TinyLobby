package com.theagent.tinyLobby;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ServerSelectorItem {

    private final ConfigurationManager config;
    private final ItemStack selectorItem;

    public ServerSelectorItem(ConfigurationManager configManager, ServerSelectorGUI gui) {
        this.config = configManager;

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
