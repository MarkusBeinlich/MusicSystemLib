/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.model;


/**
 *
 * In diesem Interface wird festgelegt, welche Attribute und Methoden ein
 * MusicPlayer hat.
 *
 * Die has??? - Methoden geben an, ob ein konkretes Gerät eine Methode
 * unterstützt. zum Beispiel kennt ein Radio keine Pause-Methode.
 *
 * @author Markus Beinlich
 */
public interface MusicPlayerInterface {


    /**
     *
     * @return
     */
    String getTitle();
    
    MusicPlayerDto getDto();

}
