
package de.beinlich.markus.musicsystem.model;

import java.util.*;


public interface RecordInterface {


    /**
     *
     * @return
     */
    String getTitle();
    
    RecordDto getDto();
    
    int getRid();
    
    byte[] getCover();
            
    List<PlayListComponentInterface> getTracks();
    
    PlayListComponentInterface getTrackById(int uid);

}
