package com.theagent.tinyLobby;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Server {

    private final String name;
    private final String proxyName;
    private final Material item;
    private final List<Component> lore;

    public Server(String name, String proxyName, Material item, @Nullable List<Component> lore) {
        this.name = name;
        this.proxyName = proxyName;
        this.item = item;
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public String getProxyName() {
        return proxyName;
    }

    public Material getItem() {
        return item;
    }

    public List<Component> getLore() {
        return lore;
    }

}
