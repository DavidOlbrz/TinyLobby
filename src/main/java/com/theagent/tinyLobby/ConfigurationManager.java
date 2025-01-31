package com.theagent.tinyLobby;

import io.papermc.paper.annotation.DoNotUse;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigurationManager {

    private final TinyLobby plugin;
    private final FileConfiguration config;

    public ConfigurationManager(TinyLobby plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig(); // save default config if nonexistent
        config = plugin.getConfig();
    }

    /**
     * Reloads the config file
     */
    @DoNotUse
    public void reload() {
        plugin.getConfig();
        plugin.logger.info("Reloaded configuration successfully.");
    }

    /**
     * Get the title of the GUI
     *
     * @return GUI title
     */
    public String getTitle() {
        return config.getString("title");
    }

    /**
     * Get the message shown when disconnecting from the server
     *
     * @return Disconnect message
     */
    public String getDisconnectMessage() {
        return config.getString("disconnect-message");
    }

    /**
     * Get all configured servers
     *
     * @return Servers
     */
    public Set<String> getServers() {
        return config.getConfigurationSection("servers").getKeys(false);
    }

    /**
     * Get information for a specific server
     *
     * @param server Server
     * @return Server info
     */
    public Server getServerInfo(String server) {
        String path = "servers." + server + ".";

        List<String> lore = config.getStringList(path + "lore");
        ArrayList<Component> loreComponents = null;
        if (!lore.isEmpty()) {
            loreComponents = new ArrayList<>();
            for (String line : lore) {
                loreComponents.add(parseLore(line));
            }
        }

        return new Server(
                config.getString(path + "name"),
                config.getString(path + "proxy-name"),
                Material.getMaterial(config.getString(path + "item")),
                loreComponents
        );
    }

    private Component parseLore(String lore) {
        // TODO correctly parse formatting codes
        return Component.text(lore);
    }

}
