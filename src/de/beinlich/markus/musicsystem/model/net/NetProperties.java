 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.model.net;

import java.io.*;
import java.util.*;

/**
 *
 * @author Markus Beinlich
 */
public class NetProperties extends Properties {

    public NetProperties() {
        super();
        File propertyFile = new File("net.properties");
//            Properties netProperties = new Properties();
        if (!propertyFile.exists()) {
            // erzeugen
            this.setProperty("net.port", "50001");
            this.setProperty("net.server_ip", "127.0.0.1");
            try {
                this.store(new FileWriter(propertyFile), "EIN KOMMENTAR");
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }

        try {
            // einlesen
            FileReader fr = new FileReader(propertyFile);
            this.load(fr);
            fr.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
