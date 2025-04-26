package com.theagent.tinyLobby;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ConfigurationManager {

    private final TinyLobby plugin;
    private FileConfiguration config;
    private int wasUpdated;

    public ConfigurationManager(TinyLobby plugin) {
        this.plugin = plugin;

        createConfig();

        wasUpdated = checkOutdatedConfig() ? 3 : 0;
    }

    /**
     * Check if the currently saved config was made for an older version of the plugin
     * and if so, backup the old one and create the new updated config
     */
    private boolean checkOutdatedConfig() {
        String[][] versions = new String[2][3];
        versions[0] = plugin.getPluginMeta().getVersion().split("\\.");
        versions[1] = config.getString("plugin-version", "").split("\\.");

        if (versions[1].length != 3) {
            plugin.logger.info("Outdated config found! Creating backup...");
            backupOldConfig();

            return true;
        }

        for (int i = 0; i < 3; i++) {
            if (isOlder(versions, i)) {
                plugin.logger.info("Outdated config found! Creating backup...");
                backupOldConfig();

                return true;
            }
        }

        return false;
    }

    /**
     * Parses the version string to int and compares if one part of the version number
     * inside the config is smaller than the current plugin version
     *
     * @param versions Array containing both plugin and config version numbers
     * @param index    the current part of the version number to be checked
     * @return if the config version is older than the plugin
     */
    private boolean isOlder(String[][] versions, int index) {
        int pluginVersion = Integer.parseInt(versions[0][index]);
        int configVersion = Integer.parseInt(versions[1][index]);
        return pluginVersion > configVersion;
    }

    /**
     * Appends the old version number to the outdated config file
     * and creates a new one
     */
    private void backupOldConfig() {
        Path dataPath = plugin.getDataPath();
        // rename file
        File configFile = dataPath.resolve("config.yml").toFile();
        boolean success = configFile.renameTo(new File(dataPath.resolve(
                "config-backup-"
                        + config.getString("plugin-version", "noVersionDefined")
                        + ".yml"
        ).toString()));

        // abort after unsuccessful renaming and send a warning
        if (!success) {
            plugin.logger.warning("Failed to backup old config!");
            return;
        }

        createConfig();
        plugin.logger.info("Finished creating backup!");
    }

    /**
     * Creates a new configuration file if not already present
     */
    private void createConfig() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    /**
     * Reloads the config file
     */
    @ApiStatus.Experimental
    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
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
     * Get the size of the GUI
     *
     * @return GUI size
     */
    public int getSize() {
        return config.getInt("gui-size");
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
        return Objects.requireNonNull(config.getConfigurationSection("servers")).getKeys(false);
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
                Material.getMaterial(Objects.requireNonNull(config.getString(path + "item"))),
                loreComponents
        );
    }

    /**
     * Get if players are allowed to close the GUI
     *
     * @return Players can close GUI
     */
    public boolean getAllowClosing() {
        return config.getBoolean("allow-closing");
    }

    /**
     * Get the item which opens the GUI
     *
     * @return item
     */
    public String getSelectorItem() {
        return config.getString("selector-item");
    }

    /**
     * Get the name of the item which opens the GUI
     *
     * @return name
     */
    public String getSelectorName() {
        return config.getString("selector-name");
    }

    /**
     * Get the slot the selector item will be in
     *
     * @return slot number
     */
    public int getSelectorSlot() {
        return config.getInt("selector-slot");
    }

    /**
     * Parses the lore into a usable (Text-)Component
     *
     * @param lore single lore line
     * @return lore as Component
     */
    private Component parseLore(String lore) {
        // TODO correctly parse formatting codes
        return Component.text(lore);
    }

    /**
     * Get if the plugin was updated recently
     * <p>
     * Also used to send a warning to a server operator 3 times on join
     *
     * @return int > 0 --> recently updated
     */
    public int getWasUpdated() {
        return wasUpdated;
    }

    /**
     * Decreases the counter
     * <p>
     * Used for showing the warning message 3 times
     */
    public void decreaseWasUpdated() {
        wasUpdated--;
    }

    /**
     * Instantly reset the counter to 0
     */
    public void resetWasUpdated() {
        wasUpdated = 0;
        // TODO reset the counter to 0 when admin reloads config (via reload command?)
    }

}
