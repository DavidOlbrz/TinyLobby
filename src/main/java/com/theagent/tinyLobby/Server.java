package com.theagent.tinyLobby;

import org.bukkit.Material;

public class Server {

    private String name;
    private Material item;
    private String host;
    private int port;

    public Server(String name, Material item, String host, int port) {
        this.name = name;
        this.item = item;
        this.host = host;
        this.port = port;
    }

    public String getName() {
        return name;
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
