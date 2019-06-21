package de.hskl.swtp.ss19.sqlcoachservice.model;

import javax.xml.bind.annotation.XmlRootElement;


/**
   * Group
   * Diese Klasse enthält die Eigenschaften eine einzelnen Gruppe.
   */

@XmlRootElement()
public class Group {
     private  int groupId;
     private  String groupName;
     private  int scenarioId;

    /**
     * Default Konstruktor
     */
    public Group() {
    }

    /**
     * Konstruktorüberladung, um eine Übung von der Datenquelle oder der REST Schnittstelle zu erzeugen.
     *
     * @param groupId    GruppenID
     * @param groupName  Gruppen Name
     * @param scenarioId Zugehöriges Szenario
     */
    public Group(int groupId, String groupName, int scenarioId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.scenarioId=scenarioId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }
}
