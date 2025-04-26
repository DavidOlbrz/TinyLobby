package com.theagent.tinyLobby;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ServerSelectorCommand {

    private final ConfigurationManager config;
    private final ServerSelectorGUI gui;

    public ServerSelectorCommand(ConfigurationManager configManager, ServerSelectorGUI gui) {
        this.config = configManager;
        this.gui = gui;
    }

    public void initialize(LifecycleEventManager<@org.jetbrains.annotations.NotNull Plugin> manager) {
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
            commands.register(tinyLobbyCommand());
        });
    }

    public LiteralCommandNode<CommandSourceStack> tinyLobbyCommand() {
        LiteralArgumentBuilder<CommandSourceStack> reloadCmd = Commands.literal("reload")
                .executes((ctx) -> {
                    config.reload();
                    gui.initialize();
                    return Command.SINGLE_SUCCESS;
                });

        LiteralArgumentBuilder<CommandSourceStack> tinyLobbyCmd = Commands.literal("tinylobby");
        tinyLobbyCmd.then(reloadCmd);

        return tinyLobbyCmd.build();
    }

}
