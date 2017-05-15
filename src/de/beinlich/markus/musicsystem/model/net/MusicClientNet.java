package de.beinlich.markus.musicsystem.model.net;

import de.beinlich.markus.musicsystem.model.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

public class MusicClientNet extends Observable {

    private final String clientName;
    // Verbindungsaufbau mit dem Server
    public Socket socket;
    private Socket newSocket;
    private ServerAddr currentServerAddr;
    private Thread readerThread;
    //
    // IO-Klassen zur Kommunikation
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private ServerPool serverPool;
    private boolean switchToServer = false;

    public MusicClientNet(String clientName) {
        this.clientName = clientName;
    }

    public void netzwerkEinrichten() {
        ServerFinder serverFinder;
        serverPool = ServerPool.getInstance();
        serverFinder = new ServerFinder(serverPool, null);
        serverFinder.findServers();

        socket = null;
        while (socket == null) {
            System.out.println("Alle:" + serverPool.toString());
            for (Map.Entry<String, ServerAddr> poolEntry : serverPool.getServers().entrySet()) {
                ServerAddr serverAddr = poolEntry.getValue();
                System.out.println("ServerAddr1: " + serverAddr);
                try {
                    NetProperties netProperties = new NetProperties();
                    System.out.println(System.currentTimeMillis() + "new Socket with " + serverAddr.getServer_ip() + serverAddr.getPort());
                    if (serverAddr.getServer_ip().equals("127.0.0.1")) {
                        throw new ConnectException();
                    }
                    socket = new Socket(serverAddr.getServer_ip(), serverAddr.getPort());
                    // Erzeugung der Kommunikations-Objekte
                    ois = new ObjectInputStream(socket.getInputStream());
                    System.out.println(System.currentTimeMillis() + "socket.connect 2");
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    // Als erstes write den Namen des eigenen Client übergeben!
                    oos.writeObject(new Protokoll(ProtokollType.CLIENT_NAME, clientName));
                    oos.flush();
                    break;
                } catch (ConnectException e) {
                    System.out.println(System.currentTimeMillis() + "Error while connecting. " + e.getMessage());
                } catch (SocketTimeoutException e) {
                    System.out.println(System.currentTimeMillis() + "Connection: " + e.getMessage() + ".");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(System.currentTimeMillis() + "socket.connect3");
            if (socket == null) {
                serverFinder.findServers();
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MusicClientNet.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        startReaderThread();
    }

    private void startReaderThread() {
        // Thread der sich um die eingehende Kommunikation kümmert
        readerThread = new Thread(new MusicClientNet.EingehendReader(this));
        // Thread als (Hintergrund-) Service
        readerThread.setDaemon(true);
        readerThread.start();
        System.out.println(System.currentTimeMillis() + "CLIENT: Netzwerkverbindung steht jetzt");
    }

    public void writeObject(Protokoll protokoll) {
        try {
            System.out.println(System.currentTimeMillis() + "writeObject:" + protokoll.getProtokollType() + ": " + protokoll.getValue());
            // einen Befehl an der Server übertragen
            oos.writeObject(protokoll);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Protokoll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean switchToServer(String newServer) {
        ServerAddr serverAddr;
        System.out.println("switchToServer:" + newServer);
        serverAddr = serverPool.getServers().get(newServer);
        System.out.println("switchToServer:" + serverAddr + ServerPool.getInstance());

        try {
            //wenn es geklappt hat, kann die Verbindung zum alten Server getrennt werden
            switchToServer = true;
            System.out.println("Old Socket:" + socket.hashCode());
            newSocket = new Socket(serverAddr.getServer_ip(), serverAddr.getPort());
            oos.writeObject(new Protokoll(ProtokollType.CLIENT_DISCONNECT, true));
            oos.flush();
            socket.close();
            System.out.println("LocalSocketAddress: " + newSocket.getLocalSocketAddress());
            System.out.println("RemoteSocketAddress: " + newSocket.getRemoteSocketAddress());

            // Erzeugung der Kommunikations-Objekte
            ois = new ObjectInputStream(newSocket.getInputStream());
            oos = new ObjectOutputStream(newSocket.getOutputStream());
            socket = newSocket;
            this.currentServerAddr = serverAddr;
            // Als erstes write den Namen des eigenen Client übergeben!
            oos.writeObject(new Protokoll(ProtokollType.CLIENT_NAME, clientName));
            oos.flush();
            startReaderThread();
            System.out.println(System.currentTimeMillis() + "netzwerk eingerichtet: ");
        } catch (IOException ex) {
            Logger.getLogger(MusicClientNet.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    private class EingehendReader implements Runnable {

        private final MusicClientNet musicClientNet;
        private Protokoll nachricht;
        private MusicPlayerDto musicPlayer;
        private MusicSystemState state;
        private RecordDto record;
        double volume;
        double trackTime;

        public EingehendReader(MusicClientNet musicClientNet) {
            this.musicClientNet = musicClientNet;
        }

        @Override
        public void run() {

            try {
                while (true) {

                    // reinkommende Nachrichten vom Server
                    Object o = ois.readObject();
                    nachricht = (Protokoll) o; // blockiert!
                    System.out.println(System.currentTimeMillis() + "CLIENT: gelesen: "
                            + nachricht + " - " + o.getClass() + " - " + musicClientNet.countObservers());
                    musicClientNet.setChanged();
                    musicClientNet.notifyObservers(nachricht);
                }

            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(System.currentTimeMillis() + "CLIENT: Verbindung zum Server beendet - " + ex);

                //SERVER_DISCONNECT nicht aufrufen, wenn es durch ein switchToServer ausgelöst wurde
                if (switchToServer) {
                    switchToServer = false;
                } else {
                    try {
                        socket.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(MusicClientNet.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    try {
                        musicClientNet.setChanged();
                        musicClientNet.notifyObservers(new Protokoll(ProtokollType.SERVER_DISCONNECT, true));
                    } catch (InvalidObjectException ex1) {
                        Logger.getLogger(MusicClientNet.class.getName()).log(Level.SEVERE, null, ex1);
                    }

                }
            }
        }
    }
}
