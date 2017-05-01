/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.beinlich.markus.musicsystem.lib;

import java.util.*;

/**
 *
 * @author Markus Beinlich
 */
public interface MusicCollectionInterface {
    
    MusicCollectionDto getMusicCollectionDto();

    RecordInterface getRecord();

    List<RecordInterface> getRecords();
    
    void setFormat(String format);

    RecordInterface getRecord(int i);
    
    RecordInterface getRecordById(int rid);

    void registerObserver(MusicCollectionObserver o);

}
