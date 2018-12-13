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
        String s = "[{\"rezeptID\":5,\"schritte\":[\"Zwiebel, Knoblauch und Ingwer schälen, klein schneiden und in Öl andünsten.\",\"Tomaten anritzen, mit kochendem Wasser übergießen und die Haut entfernen.\",\"Tomaten vierteln und in den Topf dazugeben.\",\"Gemüsebrühe,  Salz, Pfeffer,  Paprikapulver und Zucker dazugeben, kurz aufkochen und zehn Minten lang kochen lassen.\",\"Die Suppe mit einem Stabmixer fein pürieren.\",\"Die Kokosmilch dazugeben und vier Minuten bei niedriger Hitze köcheln lassen.\",\"Suppe mit Salz und Pfeffer abschmecken und heiß servieren. \"],\"name\":\"tomaten kokos suppe\",\"mengen\":[130,0.25,0.166666667,32.5,0.333333333,67.5,-1,-1,-1,-1,0.25],\"zutaten\":[45,33,17,21,22,46,47,5,48,49,50]},{\"rezeptID\":6,\"schritte\":[\"Schäle die Zwiebel und den Knoblauch und schneide beides in kleine Stücke.\",\"Wasche die Tomaten und schneide sie in kleine Würfel.\",\"Öl im Topf erhitzen, Zwiebel und Knoblauch hinzugeben und etwa fünf Minuten lang bei niedriger Hitze schmoren lassen.\",\"Die Tomatenstücke und Tomtatenmark hinzugeben und etwa zehn Minuten lang köcheln lassen.\",\"Mit Gemüsebrühe ablöschen. Salz, Pfeffer und Zucker dazugeben und die Suppe für weitere fünf Minuten köcheln lassen.\",\"Die Suppe einige Minuten abkühlen lassen und mit einem Pürierstab pürieren.\"],\"name\":\"tomatensuppe\",\"mengen\":[175,0.25,0.25,175,0.5,0.5,-1,-1,-1],\"zutaten\":[45,1,17,21,22,51,5,6,52]}]";


        ArrayList<Rezept> a = JsonService.rezepteParsen(new JSONArray(s));

        System.out.println(a.get(0).getSchritte());
    }

    /*
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
    */
}
