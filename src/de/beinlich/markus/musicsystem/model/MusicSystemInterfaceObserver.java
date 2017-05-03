/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.model;

import de.beinlich.markus.musicsystem.model.TrackObserver;
import de.beinlich.markus.musicsystem.model.TrackTimeObserver;
import de.beinlich.markus.musicsystem.model.VolumeObserver;
import de.beinlich.markus.musicsystem.model.net.ServerPoolObserver;


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
