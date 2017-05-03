package de.beinlich.markus.musicsystem.model;

import java.io.*;
import java.util.*;

/**
 *
 * @author Markus Beinlich
 */
public class MusicPlayerDto implements MusicPlayerInterface, Serializable {

    private static final long serialVersionUID = 4622961481684284456L;

    public String title;
    public MusicSystemState musicSystemState;
    public double volume;
    public PlayListComponentDto currentTrack;
    public int currentTimeTrack;
    public RecordDto record;
    public boolean hasPlay;
    public boolean hasStop;
    public boolean hasNext;
    public boolean hasPause;
    public boolean hasPrevious;
    public boolean hasTracks;
    public boolean hasCurrentTime;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public MusicPlayerDto getDto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return title; 
    }
    
}
