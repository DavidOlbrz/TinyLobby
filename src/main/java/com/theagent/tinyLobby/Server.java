package com.theagent.tinyLobby;

import org.bukkit.Material;

public class Server {

    private final String name;
    private final String proxyName;
    private final Material item;
    private final String host;
    private final int port;

    public Server(String name, String proxyName, Material item, String host, int port) {
        this.name = name;
        this.proxyName = proxyName;
        this.item = item;
        this.host = host;
        this.port = port;
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

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

}
