package soupit.hilfsklassen;

import soupit.model.Rezept;
import soupit.model.Zutat;

import java.util.ArrayList;
import java.util.Arrays;

public class JsonService {
    private JsonService instance;

    private JsonService() {
        //empty
    }

    public JsonService getInstance() {
        if (instance == null)
            instance = new JsonService();

        return instance;
    }

    public static ArrayList<Rezept> rezepteEinlesen() {
        ArrayList<Rezept> rezepte = new ArrayList<>();

        //hier Json einlesen
        ArrayList<Integer> zutaten = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2}));
        ArrayList<Integer> mengen = new ArrayList<>(Arrays.asList(new Integer[]{1, 14, 10}));
        rezepte.add(new Rezept(0, "kartoffelsuppe", null, zutaten, mengen));
        rezepte.add(new Rezept(1, "karottensuppe", null, zutaten, mengen));

        return rezepte;
    }

    public static ArrayList<Zutat> zutatenEinlesen() {
        ArrayList<Zutat> zutaten = new ArrayList<>();

        //hier Json einlesen
        zutaten.add(new Zutat(0, "kartoffel", "kartoffeln", "kilogramm",
                "kilogramm", "n"));
        zutaten.add(new Zutat(1, "tomate", "tomaten", "none",
                "none", "w"));
        zutaten.add(new Zutat(2, "salz", "salz", "prise",
                "prisen", "w"));

        return zutaten;
    }
}
