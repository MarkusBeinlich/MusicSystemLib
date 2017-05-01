
package de.beinlich.markus.musicsystem.lib;

import java.util.*;

/**
 *
 * @author Markus Beinlich
 */
public interface MusicSystemInterface {
    
    MusicSystemDto getDto();

    MusicPlayerInterface getActivePlayer();

    RecordInterface getRecord();

    void setRecord(RecordInterface record);

    MusicSystemState getMusicSystemState();

    List<MusicPlayerInterface> getPlayers();

    MusicPlayerInterface getPlayer(String title);

    PlayListComponentInterface getCurrentTrack();

    String getMusicSystemName();

    String getLocation();

    int getCurrentTimeTrack();

    double getVolume();

    void play();

    void pause();

    void next();

    void previous();

    void stop();

    void setVolume(double volume);

    void setCurrentTrack(PlayListComponentInterface track);
    
    void seek(int currentTimeTrack);

    void setActivePlayer(MusicPlayerInterface activePlayer) throws IllegalePlayerException;

    public ServerAddr getServerAddr();

    boolean hasPause();

    boolean hasPlay();

    boolean hasNext();

    boolean hasPrevious();

    boolean hasStop();

    boolean hasTracks();

    boolean hasCurrentTime();
}
