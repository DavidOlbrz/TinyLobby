package com.theagent.tinyLobby;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.Plugin;

public class TinyLobbyCommand {

    public TinyLobbyCommand(LifecycleEventManager<Plugin> manager, ConfigurationManager configManager) {
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("tinylobbyreload")
                            .executes(context -> {
                                //configManager.reload();
                                //context.getSource().getSender()
                                //        .sendMessage(TinyLobby.MESSAGE_PREFIX
                                //                .append(Component.text("Reloaded configuration successfully.")));
                                context.getSource().getSender().sendMessage(
                                        TinyLobby.MESSAGE_PREFIX.append(
                                                Component.text(
                                                        "This command is currently disabled!",
                                                        NamedTextColor.RED))
                                );
                                return Command.SINGLE_SUCCESS;
                            }).build(),
                    "Reload the plugin config file"
            );
        });
    }

}
