package soupit.hilfsklassen;

import soupit.model.Rezept;
import soupit.model.RezeptCount;
import soupit.model.Zutat;

import java.util.ArrayList;

public final class DbRequest {

    private static DbRequest instance;

    private DbRequest(){
        //empty
    }

    public static DbRequest getInstance(){
        if (instance == null)
            instance = new DbRequest();

        return instance;
    }

    public static ArrayList<Rezept> getRecipies(ArrayList<String> ingrdsStringList) {

        ArrayList<Integer> ingrdID = DbRequest.getIngrId(ingrdsStringList);

        ArrayList<RezeptCount> foundRezepte = DbRequest.searchMatching(ingrdID);


        //here should be some best match algoritmic which returns max 3 elements
        ArrayList<Rezept> returnRecipies = new ArrayList<>();
        for (RezeptCount rc : foundRezepte){
            returnRecipies.add(rc.getRezept());
        }



        return returnRecipies;
    }

    private static ArrayList<Integer> getIngrId(ArrayList<String> ingrdsStringList) {
        ArrayList<Integer> ingrdID = new ArrayList<>();
        ArrayList<Zutat> allZutatenListe = JsonService.zutatenEinlesen();

        for (String ingr : ingrdsStringList) {
            for (Zutat ztat : allZutatenListe) {
                if (ztat.getSingular().equals(ingr) || ztat.getPlural().equals(ingr)) {
                    ingrdID.add(ztat.getZutatID());
                }
            }
        }
        return ingrdID;
    }


    private static ArrayList<RezeptCount> searchMatching(ArrayList<Integer> ingrdID){
        ArrayList<Rezept> allRezepte = JsonService.rezepteEinlesen();
        ArrayList<RezeptCount> foundRezepte = new ArrayList<>();

        for (Rezept rezept : allRezepte) {
            boolean match = false;
            int matchCount = 0;
            for (Integer currIngId : ingrdID) {
                if (rezept.getZutaten().contains(currIngId)) {
                    matchCount++;
                    match = true;
                }
            }

            if (match) {
                foundRezepte.add(new RezeptCount(rezept, matchCount));
                match = false;
            }
        }

        return foundRezepte;
    }

}
