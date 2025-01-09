package com.theagent.tinyLobby;

import com.destroystokyo.paper.utils.PaperPluginLogger;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class TinyLobby extends JavaPlugin {

    private Logger logger;

    /**
     * Runs on plugin startup
     */
    @Override
    public void onEnable() {
        logger = PaperPluginLogger.getLogger(getPluginMeta());

        registerCommands();
        registerEvents();
        registerScheduler();

        logger.info("TinyLobby plugin enabled");
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

        new ServerSelectorCommand().initialize(manager);
    }

    /**
     * Registers event to check if a player clicked a GUI item
     */
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new ServerSelectorGUI(), this);
    }

    /**
     * Registers a scheduler.
     * Constantly opens the Server Selector GUI for every player.
     */
    private void registerScheduler() {
        getServer().getScheduler().scheduleSyncRepeatingTask(
                this,
                () -> getServer().getOnlinePlayers().forEach(player -> {
                    if (!Names.TextComponentToString(player.getOpenInventory().title()).equals(Names.SERVER_SELECTOR_GUI_TITLE)) {
                        new ServerSelectorGUI().open(player);
                    }
                }),
                0,
                1
        );
    }
}
