package de.hskl.swtp.ss19.sqlcoachservice.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Exercise
 * Diese Klasse enthält die Eigenschaften eine einzelnen Übung.
 *
 */

@XmlRootElement()
public class Exercise {
     private int exerciseId;
     private String exerciseText;
     private String exerciseSolution;
     private int scenarioId;
     private int groupId;

    /**
     * Default-Konstruktor ohne Elemente
     */
    public Exercise() {
    }

    /**
     * Konstruktorüberladung, um eine Übung von der Datenquelle oder der REST Schnittstelle zu erzeugen.
     * @param exerciseId    ID der Übung
     * @param exerciseText  Aufgabenbeschreibung
     * @param exerciseSolution Musterlösung
     * @param scenarioId Zugehöriges Szenario
     * @param groupId   Zugehörige Aufgabengruppe
     */
    public Exercise(int exerciseId, String exerciseText, String exerciseSolution, int scenarioId, int groupId) {
        this.exerciseId = exerciseId;
        this.exerciseText = exerciseText;
        this.exerciseSolution = exerciseSolution;
        this.scenarioId = scenarioId;
        this.groupId=groupId;
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

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setExerciseText(String exerciseText) {
        this.exerciseText = exerciseText;
    }

    public void setExerciseSolution(String exerciseSolution) {
        this.exerciseSolution = exerciseSolution;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public String getExerciseText() {
        return exerciseText;
    }

    public String getExerciseSolution() {
        return exerciseSolution;
    }
}
