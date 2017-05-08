
package de.beinlich.markus.musicsystem.model.net;

import de.beinlich.markus.musicsystem.model.ClientInit;
import de.beinlich.markus.musicsystem.model.MusicCollectionDto;
import de.beinlich.markus.musicsystem.model.MusicPlayerDto;
import de.beinlich.markus.musicsystem.model.MusicSystemDto;
import de.beinlich.markus.musicsystem.model.MusicSystemState;
import de.beinlich.markus.musicsystem.model.PlayListComponentDto;
import de.beinlich.markus.musicsystem.model.RecordDto;
import java.io.*;

/**
 *
 * @author Markus Beinlich
 */
public enum ProtokollType implements Serializable{
    MUSIC_COLLECTION_DTO(MusicCollectionDto.class),
    MUSIC_SYSTEM_DTO(MusicSystemDto.class), 
    MUSIC_PLAYER_DTO(MusicPlayerDto.class), 
    MUSIC_PLAYER_SELECTED(String.class),
    RECORD_DTO(RecordDto.class), 
    RECORD_SELECTED(RecordDto.class),
    STATE(MusicSystemState.class), 
    PLAY_LIST_COMPONENT_DTO(PlayListComponentDto.class), 
    VOLUME(Double.class),
    TRACK_TIME(Integer.class),
    TRACK_SELECTED(PlayListComponentDto.class),
    CLIENT_COMMAND_PLAY(MusicSystemState.class),
    CLIENT_COMMAND_NEXT(PlayListComponentDto.class),
    CLIENT_COMMAND_PREVIOUS(PlayListComponentDto.class),
    CLIENT_COMMAND_PAUSE(MusicSystemState.class),
    CLIENT_COMMAND_STOP(MusicSystemState.class),
    CLIENT_INIT(ClientInit.class),
    SERVER_POOL(ServerPool.class),
    SERVER_ADDR(ServerAddr.class),
    SERVER_ADDR_REQUEST(Boolean.class),
    CLIENT_DISCONNECT(Boolean.class),
    SERVER_DISCONNECT(Boolean.class),
//    HAS_CURRENT_TIME(Boolean.class),
//    HAS_TRACKS(Boolean.class),
//    HAS_PAUSE(Boolean.class),
//    HAS_PREVIOUS(Boolean.class),
//    HAS_NEXT(Boolean.class),
    CLIENT_NAME(String.class);
    private final Class <?> classs;
    
    ProtokollType(Class classs){
        this.classs = classs;
    }

    /**
     * @return the classs
     */
    public Class <?> getClasss() {
        return classs;
    }
}
