package soupit.hilfsklassen;

import org.json.JSONArray;
import org.json.JSONObject;
import soupit.model.Rezept;
import soupit.model.ZutatMenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class NurEinKleinerTest {

    public static void main(String... args){
        System.out.println(TextService.zutatenVonRezeptVorlesen(RezeptService.getZutaten(1)));
    }

    public static void testnochwas(){
        //hier Json einlesen
        ArrayList<String> schritte = new ArrayList<>(Arrays.asList(new String[]{"test"}));
        ArrayList<Integer> zutaten = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2}));
        ArrayList<Double> mengen = new ArrayList<>(Arrays.asList(new Double[]{1., 14., 10.}));
        Rezept r = new Rezept(0, "kartoffelsuppe", schritte, zutaten, mengen);

        JSONObject j = new JSONObject(r);

        int newRezeptID = (int)j.get("rezeptID");
        String newName =  (String)j.get("name");
        ArrayList<String> newSchritte = new ArrayList<>();
        ArrayList<Integer> newZutaten = new ArrayList<>();
        ArrayList<Double> newMengen = new ArrayList<>();

        JSONArray jsonSchritte = j.getJSONArray("schritte");
        for(Object schritt: jsonSchritte){
            newSchritte.add((String) schritt);
        }

        JSONArray jsonZutaten = j.getJSONArray("zutaten");
        for(Object zutat: jsonZutaten){
            newZutaten.add((Integer) zutat);
        }

        JSONArray jsonMengen = j.getJSONArray("mengen");
        for(Object menge: jsonMengen){
            newMengen.add((Double) menge);
        }

        Rezept r2 = new Rezept(newRezeptID, newName, newSchritte, newZutaten, newMengen);

        System.out.println(r2.getMengen());
    }

    public static void testnochmalwas() throws IOException {
        //hier Json einlesen
        FileReader fileReader = new FileReader("data/rezepte.json");

        BufferedReader br = new BufferedReader(fileReader);

        String input = "";

        String zeile = br.readLine();

        while( zeile != null )
        {
            input += zeile;
            zeile = br.readLine();
        }

        JSONObject json = new JSONObject(input);
        JSONObject j = (JSONObject) json.get("1");

        //System.out.println(j);


        int newRezeptID = (int)j.get("rezeptID");
        String newName =  (String)j.get("name");
        ArrayList<String> newSchritte = new ArrayList<>();
        ArrayList<Integer> newZutaten = new ArrayList<>();
        ArrayList<Double> newMengen = new ArrayList<>();

        JSONArray jsonSchritte = j.getJSONArray("schritte");
        for(Object schritt: jsonSchritte){
            newSchritte.add((String) schritt);
        }

        JSONArray jsonZutaten = j.getJSONArray("zutaten");
        for(Object zutat: jsonZutaten){
            newZutaten.add((Integer) zutat);
        }

        JSONArray jsonMengen = j.getJSONArray("mengen");
        for(Object menge: jsonMengen){
            newMengen.add((Double) menge);
        }

        Rezept r2 = new Rezept(newRezeptID, newName, newSchritte, newZutaten, newMengen);

        System.out.println(r2.getMengen());

    }
}
