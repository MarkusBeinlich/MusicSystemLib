
package de.beinlich.markus.musicsystem.model;

import de.beinlich.markus.musicsystem.model.PlayListComponentDto;
import java.io.*;


public interface PlayListComponentInterface {


    /**
     *
     * @return
     */
    String getTitle();
    
    int getUid();
    
    int getPlayingTime();
    
    PlayListComponentDto getDto();

}
