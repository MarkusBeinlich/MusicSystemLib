
package de.beinlich.markus.musicsystem.lib;

import de.beinlich.markus.musicsystem.lib.PlayListComponentDto;
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
