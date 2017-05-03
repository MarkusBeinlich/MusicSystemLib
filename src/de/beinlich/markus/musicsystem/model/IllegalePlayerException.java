/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.model;

/**
 *
 * @author Markus Beinlich
 */
public class IllegalePlayerException extends Exception {

    /**
     *
     */
    public IllegalePlayerException() {
    }

    /**
     *
     * @param message
     */
    public IllegalePlayerException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public IllegalePlayerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public IllegalePlayerException(Throwable cause) {
        super(cause);
    }

}
