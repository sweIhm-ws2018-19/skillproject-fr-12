package soupit.hilfsklassen;

import org.json.JSONArray;
import org.json.JSONObject;
import soupit.model.Rezept;
import soupit.model.ZutatMenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NurEinKleinerTest {

    public static void main(String... args){
        //System.out.println(TextService.zutatenVonRezeptVorlesen(RezeptService.getZutaten(0)));

        testnochwas();
    }

    public static void testirgendwas(){
        ZutatMenge z = new ZutatMenge(15, "gramm", "tomaten");

        JSONObject j = new JSONObject(z);

        ZutatMenge z2 = new ZutatMenge(j);

        System.out.println(z2.getMenge() + z2.getEinheit() + z2.getName());
    }

    public static void testnochwas(){
        //hier Json einlesen
        ArrayList<String> schritte = new ArrayList<>(Arrays.asList(new String[]{"test"}));
        ArrayList<Integer> zutaten = new ArrayList<>(Arrays.asList(new Integer[]{0, 1, 2}));
        ArrayList<Integer> mengen = new ArrayList<>(Arrays.asList(new Integer[]{1, 14, 10}));
        Rezept r = new Rezept(0, "kartoffelsuppe", schritte, zutaten, mengen);

        JSONObject j = new JSONObject(r);

        int newRezeptID = (int)j.get("rezeptID");
        String newName =  (String)j.get("name");
        ArrayList<String> newSchritte = new ArrayList<>();
        ArrayList<Integer> newZutaten = new ArrayList<>();
        ArrayList<Integer> newMengen = new ArrayList<>();

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
            newMengen.add((Integer) menge);
        }

        Rezept r2 = new Rezept(newRezeptID, newName, newSchritte, newZutaten, newMengen);

        System.out.println(r2.getMengen());
    }
}
