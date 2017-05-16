package de.beinlich.markus.musicsystem.model;

import de.beinlich.markus.musicsystem.model.RecordInterface;
import java.io.*;
import java.util.*;

/**
 *
 * @author Markus Beinlich
 */
public class RecordDto implements RecordInterface, Serializable {

    private static final long serialVersionUID = -5271344418912579109L;

    public int rid;
    public String title;
    public String artist;
    public String medium;
    public byte[] cover;
    public List<PlayListComponentInterface> tracks;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public RecordDto getDto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] getCover() {
        return cover;
    }

    @Override
    public List<PlayListComponentInterface> getTracks() {
//        List<PlayListComponentInterface> tracks = new ArrayList<>();
//        for (PlayListComponentDto track : this.tracks) {
//            tracks.add(track);
//        }
        return tracks;
    }

    @Override
    public String toString() {
        return this.artist + " - " + this.getTitle();
    }

    @Override
    public PlayListComponentInterface getTrackById(int uid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getRid() {
        return rid;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.rid;
        hash = 53 * hash + Objects.hashCode(this.title);
        return hash;
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
        final RecordDto other = (RecordDto) obj;
        if (this.rid != other.rid) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
    }
}
