package soupit.hilfsklassen;

import org.json.JSONArray;
import org.json.JSONObject;
import soupit.model.Rezept;
import soupit.model.RezeptCount;
import soupit.model.Zutat;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
        try (BufferedReader br = new BufferedReader(new FileReader("data/rezepte.json"))) {
            String input = "";

            String zeile = br.readLine();
            while (zeile != null) {
                input += zeile;
                zeile = br.readLine();
            }

            JSONArray json = new JSONObject(input).getJSONArray("rezepte");
            for (Object j : json) {
                rezepte.add(rezeptParsen((JSONObject) j));
            }

        } catch (FileNotFoundException e) {
            System.err.println("File nicht vorhanden..");
        } catch (IOException e) {
            System.err.println("Irgendein Fehler in JsonService.rezepteEinlesen()..");
        }

        return rezepte;
    }

    public static ArrayList<Rezept> rezepteParsen(JSONArray json) {
        ArrayList<Rezept> rezepte = new ArrayList<>();

        for (Object j : json) {
            rezepte.add(rezeptParsen((JSONObject) j));
        }

        return rezepte;
    }

    public static ArrayList<Zutat> zutatenEinlesen() {
        ArrayList<Zutat> zutaten = new ArrayList<>();

        //hier Json einlesen
        try (BufferedReader br = new BufferedReader(new FileReader("data/zutaten.json"))) {
            String input = "";

            String zeile = br.readLine();
            while (zeile != null) {
                input += zeile;
                zeile = br.readLine();
            }

            JSONArray json = new JSONObject(input).getJSONArray("zutaten");
            for (Object j : json) {
                zutaten.add(zutatParsen((JSONObject) j));
            }

        } catch (FileNotFoundException e) {
            System.err.println("File nicht vorhanden..");
        } catch (IOException e) {
            System.err.println("Irgendein Fehler in JsonService.zutatenEinlesen()..");
        }

        return zutaten;
    }

    /**
     * parsed ein JSONObject vom Typ Rezept zu einem Rezept Objekt
     *
     * @param j: JSONObject vom Typ Rezept
     * @return Rezept
     */
    private static Rezept rezeptParsen(JSONObject j) {
        int newRezeptID = (int) j.get("rezeptID");
        String newName = (String) j.get("name");

        ArrayList<String> newSchritte = new ArrayList<>();
        ArrayList<Integer> newZutaten = new ArrayList<>();
        ArrayList<Double> newMengen = new ArrayList<>();

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
            newMengen.add(Double.parseDouble(menge.toString()));
        }

        Rezept r2 = new Rezept(newRezeptID, newName, newSchritte, newZutaten, newMengen);
        return r2;
    }

    /**
     * parsed ein JSONObject vom Typ Zutat zu einem Zutat Objekt
     *
     * @param j: JSONObject vom Typ Zutat
     * @return Zutat
     */
    private static Zutat zutatParsen(JSONObject j) {
        int newZutatID = (int) j.get("zutatID");
        String newSingular = (String) j.get("singular");
        String newPlural = (String) j.get("plural");
        String newEinheitSingular = (String) j.get("einheitSingular");
        String newEinheitPlural = (String) j.get("einheitPlural");
        String newEinheitGeschlecht = (String) j.get("einheitGeschlecht");

        Zutat zutat = new Zutat(newZutatID, newSingular, newPlural, newEinheitSingular, newEinheitPlural, newEinheitGeschlecht);
        return zutat;
    }

    public static RezeptCount rezeptCountParsen(JSONObject j) {
        JSONObject rezeptString = (JSONObject) j.get("rezept");
        int count = j.getInt("count");

        return  new RezeptCount(JsonService.rezeptParsen(rezeptString), count);
    }

}
