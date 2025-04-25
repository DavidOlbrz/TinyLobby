package com.theagent.tinyLobby;

import com.destroystokyo.paper.utils.PaperPluginLogger;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class TinyLobby extends JavaPlugin {

    public static final TextComponent MESSAGE_PREFIX = Component.text("[TinyLobby] ");

    private ConfigurationManager configManager;
    private ServerSelectorGUI gui;

    public Logger logger;

    /**
     * Runs on plugin startup
     */
    @Override
    public void onEnable() {
        logger = PaperPluginLogger.getLogger(getPluginMeta());

        configManager = new ConfigurationManager(this);
        gui = new ServerSelectorGUI(configManager);

        registerCommands();
        registerEvents();
        registerScheduler();
        registerPluginChannels();

        logger.info("TinyLobby plugin enabled");

        // if the plugin was recently updated, send a warning to the console
        if (configManager.getWasUpdated() > 0) {
            logger.warning(String.format(
                    "TinyLobby was updated to %s, you should reconfigure the plugin config!",
                    this.getPluginMeta().getVersion()
            ));
        }
    }

    /**
     * Runs on plugin shutdown
     */
    @Override
    public void onDisable() {
        logger.info("TinyLobby plugin disabled");
    }

    /**
     * Make commands usable on the server
     */
    private void registerCommands() {
        LifecycleEventManager<Plugin> manager = getLifecycleManager();

        new TinyLobbyCommand(manager, configManager);
        new ServerSelectorCommand().initialize(manager, gui);
    }

    /**
     * Registers event to check if a player clicked a GUI item
     */
    private void registerEvents() {
        ServerSelectorItem selectorItem = new ServerSelectorItem(configManager, gui);
        getServer().getPluginManager().registerEvents(new PlayerListener(configManager, gui, selectorItem), this);
    }

    /**
     * Registers a scheduler.
     * Constantly opens the Server Selector GUI for every player.
     */
    private void registerScheduler() {
        // only activate if configured
        if (!configManager.getAllowClosing()) {
            String configuredTitle = configManager.getTitle();
            getServer().getScheduler().scheduleSyncRepeatingTask(
                    this,
                    () -> getServer().getOnlinePlayers().forEach(player -> {
                        if (!Names.TextComponentToString(player.getOpenInventory().title()).equals(configuredTitle)) {
                            gui.open(player);
                        }
                    }),
                    0,
                    1
            );
        }
    }

    /**
     * Registers a plugin channel to communicate with proxy server
     */
    private void registerPluginChannels() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        PlayerSender.setPlugin(this);
    }

}
