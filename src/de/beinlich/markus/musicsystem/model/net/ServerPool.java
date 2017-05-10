package de.beinlich.markus.musicsystem.model.net;

import java.io.*;
import java.util.*;

/**
 *
 * @author Markus Beinlich
 */
public class ServerPool implements Serializable {

    private static final long serialVersionUID = 5923335649688352457L;

    private static ServerPool uniqueInstance;
    private final Map<String, ServerAddr> servers;

    private ServerPool() {
        servers = new TreeMap<>();
    }

    public static synchronized ServerPool getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ServerPool();
        }
        return uniqueInstance;
    }

    public ServerPool addServers(Map<String, ServerAddr> servers) {
        this.servers.putAll(servers);
        return this;
    }

    public ServerAddr getFirstServer() {
        return (ServerAddr) ((TreeMap) servers).firstEntry().getValue();
    }

    public void addServer(String name, ServerAddr serverAddr) {
        servers.put(name, serverAddr);

    }

    public List<String> getActiveServers() {

        return new ArrayList<>(servers.keySet());
    }

    /**
     * @return the servers
     */
    public Map<String, ServerAddr> getServers() {
        return new TreeMap<>(servers);
//        return servers;
    }

    @Override
    public String toString() {
        return  " Servers: " + servers.toString();
    }

}
