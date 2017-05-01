package de.beinlich.markus.musicsystem.lib;

import java.io.*;
import java.util.*;

/**
 *
 * @author Markus Beinlich
 */
public class MusicCollectionDto implements MusicCollectionInterface, Serializable {

    private static final long serialVersionUID = 6399011378220532131L;

    public List<RecordInterface> records;

    @Override
    public MusicCollectionDto getMusicCollectionDto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RecordInterface getRecord() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RecordInterface> getRecords() {
        return (List<RecordInterface>)records;
    }

    @Override
    public RecordInterface getRecord(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerObserver(MusicCollectionObserver o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RecordInterface getRecordById(int rid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFormat(String format) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
