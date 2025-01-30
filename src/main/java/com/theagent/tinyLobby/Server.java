package com.theagent.tinyLobby;

import org.bukkit.Material;

public class Server {

    private final String name;
    private final String proxyName;
    private final Material item;

    public Server(String name, String proxyName, Material item) {
        this.name = name;
        this.proxyName = proxyName;
        this.item = item;
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

}
