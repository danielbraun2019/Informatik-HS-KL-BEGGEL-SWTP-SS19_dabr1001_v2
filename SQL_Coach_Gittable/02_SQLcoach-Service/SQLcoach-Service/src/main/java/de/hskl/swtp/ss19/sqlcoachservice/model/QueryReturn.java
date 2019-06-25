package de.hskl.swtp.ss19.sqlcoachservice.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 QueryReturn
 Dies ist eine Hilfsklasse und der hängt stark mit der Methode executeQuery() in SqlCoachDBFacet zusammen, welche eine Liste dieser Objekte zurückgibt
 clumns ist als eine Liste von Strings modelliert, da verschiedene Tabllen unterschiedlich viele Spalten haben
 */

@XmlRootElement()
public class QueryReturn {
    private List<String> columnameandvalue;

    public QueryReturn() {
    }
    public QueryReturn(List<String> columnameandvalue) {
        this.columnameandvalue=columnameandvalue;
    }

    public List<String> getColumnameandvalue() {
        return columnameandvalue;
    }

    public void setColumnameandvalue(List<String> columnameandvalue) {
        this.columnameandvalue = columnameandvalue;
    }
}


