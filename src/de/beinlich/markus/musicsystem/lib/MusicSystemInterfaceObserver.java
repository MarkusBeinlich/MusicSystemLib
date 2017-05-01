/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.lib;


/**
 *
 * @author Markus Beinlich
 */
public interface MusicSystemInterfaceObserver extends MusicSystemInterface{

    void registerObserver(TrackObserver o);

    void registerObserver(TrackTimeObserver o);

    void registerObserver(VolumeObserver o);

    void registerObserver(StateObserver o);

    void registerObserver(RecordObserver o);

    void registerObserver(MusicPlayerObserver o);

    void registerObserver(ServerPoolObserver o);
}
