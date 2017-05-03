package de.beinlich.markus.musicsystem.model.net;

import de.beinlich.markus.musicsystem.model.net.ProtokollType;
import java.io.*;

/**
 *
 * @author Markus Beinlich
 */
public class Protokoll implements Serializable {

    private static final long serialVersionUID = 8198519843089657793L;

    private final ProtokollType protokollType;
    private Object value;

    public Protokoll(ProtokollType protokollType, Object value) throws InvalidObjectException {
        this.protokollType = protokollType;
//        if (value.getClass() != protokollType.getClasss()) {
//        if (Arrays.asList(value.getClass().getClasses()).contains(protokollType.getClasss())) {
        if (! protokollType.getClasss().isInstance(value)) {
            throw new InvalidObjectException(protokollType.getClasss() + " expected. " + value.getClass() + " not valid.");
        }
        this.value = value;
    }

    /**
     * @return the protokollType
     */
    public ProtokollType getProtokollType() {
        return protokollType;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.protokollType.name() + " - " + this.value.toString();
    }
}
