/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.model.net;

import static de.beinlich.markus.musicsystem.model.net.ServerPool.*;
import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author Markus Beinlich
 */
public class ServerFinder {

    private final ServerPool serverPool;
    private final ServerAddr myServerAddr;

    public ServerFinder(ServerPool serverPool, ServerAddr myServerAddr) {
        this.serverPool = serverPool;
        this.myServerAddr = myServerAddr;
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
//                System.out.println("Search for: " + address);
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
//            System.out.println("isReachable: " + address.toString());
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
                    serverPool.addServer(serverAddr.getName(), serverAddr);
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
}
