package de.hskl.swtp.ss19.sqlcoachservice.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Scenario
 * Diese Klasse enthält die Eigenschaften eines Szenarios
 */
@XmlRootElement()
public class Scenario {

    private int scenarioId;
    private String scenarioName;
    private String scenarioOwner;
    private int datasetId;
    private int rank;


    /**
     * Defaultkonstruktor
     */
    public Scenario() {
    }


    /**
     * Konstruktorüberladung, um ein Szenario von der Datenquelle oder der REST Schnittstelle zu erzeugen.
     *
     * @param scenarioId    Szenario ID
     * @param scenarioName  Name des Szenario
     * @param scenarioOwner Ersteller/Besitzer des Szenraios.
     */
    public Scenario(int scenarioId, String scenarioName, String scenarioOwner, int datasetId, int rank) {
        this.scenarioId = scenarioId;
        this.scenarioName = scenarioName;
        this.scenarioOwner = scenarioOwner;
        this.datasetId = datasetId;
        this.rank = rank;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public void setScenarioOwner(String scenarioOwner) {
        this.scenarioOwner = scenarioOwner;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public String getScenarioOwner() {
        return scenarioOwner;
    }

    public int getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(int datasetId) {
        this.datasetId = datasetId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
