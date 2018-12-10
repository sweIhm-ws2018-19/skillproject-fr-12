package soupit.hilfsklassen;

import org.json.JSONArray;
import org.json.JSONObject;
import soupit.model.Rezept;
import soupit.model.Zutat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("data/rezepte.json");

            BufferedReader br = new BufferedReader(fileReader);

            String input = "";

            String zeile = br.readLine();

            while (zeile != null) {
                input += zeile;
                zeile = br.readLine();
            }

            JSONArray json = new JSONObject(input).getJSONArray("rezepte");

            for(Object j : json){
                rezepte.add(rezeptParsen((JSONObject) j));
            }

        } catch (FileNotFoundException e) {
            System.err.println("File nicht vorhanden..");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Irgendein Fehler in JsonService.rezepteEinlesen()..");
            e.printStackTrace();
        }

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

    private static Rezept rezeptParsen(JSONObject j) {
        int newRezeptID = (int) j.get("rezeptID");
        String newName = (String) j.get("name");
        ArrayList<String> newSchritte = new ArrayList<>();
        ArrayList<Integer> newZutaten = new ArrayList<>();
        ArrayList<Integer> newMengen = new ArrayList<>();

        JSONArray jsonSchritte = j.getJSONArray("schritte");
        for (Object schritt : jsonSchritte) {
            newSchritte.add((String) schritt);
        }

        JSONArray jsonZutaten = j.getJSONArray("zutaten");
        for (Object zutat : jsonZutaten) {
            newZutaten.add((Integer) zutat);
        }

        JSONArray jsonMengen = j.getJSONArray("mengen");
        for (Object menge : jsonMengen) {
            newMengen.add((Integer) menge);
        }

        Rezept r2 = new Rezept(newRezeptID, newName, newSchritte, newZutaten, newMengen);

        return r2;
    }
}
