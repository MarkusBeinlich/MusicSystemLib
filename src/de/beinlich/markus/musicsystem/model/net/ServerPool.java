package de.beinlich.markus.musicsystem.model.net;

import de.beinlich.markus.musicsystem.model.net.ServerAddr;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author Markus Beinlich
 */
public class ServerPool extends Observable implements Serializable {

    private static final long serialVersionUID = 5923335649688352457L;

    protected static ServerPool uniqueInstance;
    private final Map<String, ServerAddr> servers;
    transient private ServerAddr myServerAddr;

    private ServerPool(ServerAddr myServerAddr) {
        this.myServerAddr = myServerAddr;
        servers = new TreeMap<>();
        findServers();
//        fileNameServerPool = clientName + ".ServerPool";
//        File serverFile = new File(fileNameServerPool);
//        if (!serverFile.exists()) {
//            servers = new TreeMap<>();
//            try {
//                //Der Standard-Server muss immer eingetragen sein, damit sich die Server
//                //gegenseitig finden können
//                addServer("HiFi-Anlage", new ServerAddr(50001, InetAddress.getLocalHost().getHostAddress(), "HiFi-Anlage", true));
////            addServer(musicNetComponent.getMusicSystem().getName(), musicNetComponent.getMusicSystem().getServerAddr());
//            } catch (UnknownHostException ex) {
//                Logger.getLogger(ServerPool.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } else {
//            try {
//                FileInputStream fis = new FileInputStream(serverFile);
//                ObjectInputStream ois = new ObjectInputStream(fis);
//                servers = (Map) ois.readObject();
//                ois.close();
//                // aktuellen Server aktiv setzen - wenn er nicht existiert wird er hinzugefügt
//                // Das geht zumindest Client-seitig nicht, da das MusicSystem ja noch nicht existiert, wenn der Serverpool erzeugt wird
////                if (servers.containsKey(musicNetComponent.getMusicSystem().getName())) {
////                    servers.get(musicNetComponent.getMusicSystem().getName()).setActiv(true);
////                } else {
////                    addServer(musicNetComponent.getMusicSystem().getName(), musicNetComponent.getMusicSystem().getServerAddr());
////                }
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(ServerPool.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(ServerPool.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(ServerPool.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
    }

    public static synchronized ServerPool getInstance(ServerAddr myServerAddr) {
        if (uniqueInstance == null) {
            uniqueInstance = new ServerPool(myServerAddr);
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
//        saveServerPool();
        hasChanged();
        notifyObservers(serverAddr);
    }

    public List<String> getActiveServers() {
//        List<String> activeServers = new ArrayList<>();
//        servers.forEach((key, value) -> {
//            if (value.isActiv()) {
//                activeServers.add(key);
//            }
//        });
//        return activeServers;
        return new ArrayList<>(servers.keySet());
    }

    /**
     * @return the servers
     */
    public Map<String, ServerAddr> getServers() {
        return new TreeMap<>(servers);
//        return servers;
    }

    public void findServers() {
        //Nach weiteren aktiven IP-Adresse im LAN suchen 
        tryAllAddressesOnLan();
    }

    private void tryAllAddressesOnLan() {
        InetAddress localhost;
        try {
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerPool.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        byte[] ip = localhost.getAddress();

        for (int i = 1; i <= 254; i++) {
            try {
                ip[3] = (byte) i;
                InetAddress address = InetAddress.getByAddress(ip);
                System.out.println("Search for: " + address);
                Thread musicServerFinderThread = new Thread(new MusicServerFinder(address));
                musicServerFinderThread.setDaemon(true);
                musicServerFinderThread.start();

            } catch (UnknownHostException e) {
                Logger.getLogger(ServerPool.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private class MusicServerFinder implements Runnable {

        private final InetAddress address;

        public MusicServerFinder(InetAddress address) {
            this.address = address;
        }

        @Override
        public void run() {
            System.out.println("isReachable: " + address.toString());
            try {
                if (address.isReachable(20)) {
                    System.out.println(address.toString().substring(1) + " is on the network");
                    tryAllPorts(address.getHostAddress());
                }
            } catch (IOException ex) {
                Logger.getLogger(MusicServerFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void tryAllPorts(String hostAddress) {
            for (int j = 1; j <= 3; j++) {
                tryToConnectServer(hostAddress, 50000 + j);
            }
        }

        private void tryToConnectServer(String hostAddress, int port) {
            Socket socket;
            ObjectInputStream ois;
            ObjectOutputStream oos;
            Protokoll nachricht;
            System.out.println("tryToConnetServer " + hostAddress + " " + port);
            try {
                socket = new Socket(hostAddress, port);
                // Erzeugung der Kommunikations-Objekte
                ois = new ObjectInputStream(socket.getInputStream());
                System.out.println(System.currentTimeMillis() + "socket.connect 2");
                oos = new ObjectOutputStream(socket.getOutputStream());
                // Als erstes write die eigene ServerAddresse übergeben!
                if (myServerAddr != null) {
                    oos.writeObject(new Protokoll(ProtokollType.SERVER_ADDR, myServerAddr));
                    oos.flush();
                }
                oos.writeObject(new Protokoll(ProtokollType.SERVER_ADDR_REQUEST, true));
                oos.flush();
                try {
                    nachricht = (Protokoll) ois.readObject(); // blockiert!
                    ServerAddr serverAddr = (ServerAddr) nachricht.getValue();
                    uniqueInstance.addServer(serverAddr.getName(), serverAddr);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ServerPool.class.getName()).log(Level.SEVERE, null, ex);
                }
                oos.writeObject(new Protokoll(ProtokollType.CLIENT_DISCONNECT, true));
                oos.flush();
                socket.close();
//                new Thread(new ClientHandler(socket, MusicServer.this, true)).start();
            } catch (ConnectException e) {
                System.out.println(System.currentTimeMillis() + "Error while connecting. " + e.getMessage());
            } catch (SocketTimeoutException e) {
                System.out.println(System.currentTimeMillis() + "Connection: " + e.getMessage() + ".");
            } catch (IOException e) {
                Logger.getLogger(MusicServerFinder.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

    @Override
    public String toString() {
        return this.myServerAddr + " Servers: " + servers.toString();
    }

}
