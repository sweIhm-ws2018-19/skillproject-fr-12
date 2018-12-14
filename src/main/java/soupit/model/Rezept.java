package soupit.model;

import java.util.ArrayList;

public class Rezept {
    private int rezeptID;
    private String name;
    private ArrayList<String> schritte;
    private ArrayList<Integer> zutaten;
    private ArrayList<Double> mengen;

    public Rezept(int rezeptID, String name, ArrayList<String> schritte, ArrayList<Integer> zutaten, ArrayList<Double> mengen) {
        this.rezeptID = rezeptID;
        this.name = name;
        this.schritte = schritte;
        this.zutaten = zutaten;
        this.mengen = mengen;
    }

    public int getRezeptID() {
        return rezeptID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSchritte() {
        return schritte;
    }

    public ArrayList<Integer> getZutaten() {
        return zutaten;
    }

    public ArrayList<Double> getMengen() {
        return mengen;
    }
}
