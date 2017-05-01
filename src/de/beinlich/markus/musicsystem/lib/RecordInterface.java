
package de.beinlich.markus.musicsystem.lib;

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
