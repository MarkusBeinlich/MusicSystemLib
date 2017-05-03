/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.model.net;

import java.io.Serializable;

/**
 *
 * @author Markus Beinlich
 */
public class ServerAddr implements Serializable {

    private static final long serialVersionUID = -7396694931458806220L;

    private int port;
    private String server_ip;
    private boolean activ = false;
    private String name;

    public ServerAddr(int port, String server_ip, String name, boolean activ) {
        this.port = port;
        this.server_ip = server_ip;
        this.name = name;
        this.activ = activ;
    }
    

    /**
     * @return the activ
     */
    public boolean isActiv() {
        return activ;
    }

    /**
     * @param activ the activ to set
     */
    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return the server_ip
     */
    public String getServer_ip() {
        return server_ip;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name + " activ: " + isActiv();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServerAddr other = (ServerAddr) obj;
        return (this.port == other.port) && (this.server_ip.equals(other.server_ip)); 
    }
}
