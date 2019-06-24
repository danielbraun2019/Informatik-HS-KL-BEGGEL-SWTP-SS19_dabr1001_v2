package de.hskl.swtp.ss19.sqlcoachservice.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement()
public class Table_shemas {
    private String tableName;
    private List<String> column_names;
    private String primary_key;
    private List<String> foreign_key;

    public Table_shemas() {
    }


    public Table_shemas(String tableName, List<String> column_names, String primary_key, List<String> foreign_key) {
        this.tableName = tableName;
        this.column_names = column_names;
        this.primary_key = primary_key;
        this.foreign_key = foreign_key;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumn_names() {
        return column_names;
    }

    public void setColumn_names(List<String> column_names) {
        this.column_names = column_names;
    }

    public String getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(String primary_key) {
        this.primary_key = primary_key;
    }

    public List<String> getForeign_key() {
        return foreign_key;
    }

    public void setForeign_key(List<String> foreign_key) {
        this.foreign_key = foreign_key;
    }
}



