package com.theagent.tinyLobby;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ServerSelectorCommand {

    public void initialize(LifecycleEventManager<Plugin> manager, ServerSelectorGUI gui) {
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("servers")
                            .executes(context -> {
                                CommandSender sender = context.getSource().getSender();

                                if (sender instanceof Player) {
                                    gui.open((Player) sender);
                                }

                                return Command.SINGLE_SUCCESS;
                            })
                            .build(),
                    "Opens a GUI with a list of servers to join"
            );
        });
    }

}
