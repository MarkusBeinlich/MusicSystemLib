/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.model.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Markus
 */
public class ServerShutdown {

    private final ServerPool serverPool;

    public ServerShutdown(ServerPool serverPool) {
        this.serverPool = serverPool;
    }

    public void shutdownServers() {
        for (ServerAddr serverAddr : serverPool.getServers().values()) {
            Socket socket = null;
            ObjectInputStream ois;
            ObjectOutputStream oos;
            Protokoll nachricht;

            try {
                socket = new Socket(serverAddr.getServer_ip(), serverAddr.getPort());
                // Erzeugung der Kommunikations-Objekte
                ois = new ObjectInputStream(socket.getInputStream());
                System.out.println("tryToConnetServer " + serverAddr);
                System.out.println(System.currentTimeMillis() + "socket.connect 2");
                oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(new Protokoll(ProtokollType.SERVER_POOL_REDUCED, ServerPool.getInstance().getServers()));
                oos.flush();

            } catch (ConnectException e) {
                System.out.println(System.currentTimeMillis() + "Error while connecting. " + e.getMessage());
            } catch (SocketTimeoutException e) {
                System.out.println(System.currentTimeMillis() + "Connection: " + e.getMessage() + ".");
            } catch (IOException e) {
                Logger.getLogger(ServerShutdown.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                if (socket != null && !socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ServerFinder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
