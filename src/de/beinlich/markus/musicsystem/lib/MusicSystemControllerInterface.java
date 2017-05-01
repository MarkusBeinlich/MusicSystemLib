package de.beinlich.markus.musicsystem.lib;


/**
 *
 * @author Markus
 */
public interface MusicSystemControllerInterface {

    public void play();

    public void pause();

    public void stop();

    public void previous();

    public void next();

    public void setVolume(double volume);

    void seek(int currentTimtTrack);

    public void setCurrentTrack(PlayListComponentInterface track);

    public void setActivePlayer(String selectedPlayer);

    public void setRecord(RecordInterface record);
}
