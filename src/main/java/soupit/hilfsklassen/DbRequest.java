package soupit.hilfsklassen;

import soupit.model.Rezept;
import soupit.model.RezeptCount;
import soupit.model.Zutat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class DbRequest {

    private static DbRequest instance;

    private DbRequest() {
        //empty
    }

    public static DbRequest getInstance() {
        if (instance == null)
            instance = new DbRequest();

        return instance;
    }

    public static ArrayList<Rezept> getRecipies(ArrayList<String> ingrdsStringList, ArrayList<String> woIngrList) {

        //find indrdID for the string ingrids
        ArrayList<Integer> ingrdID = DbRequest.getIngrId(ingrdsStringList);
        ArrayList<Integer> woIngrdID = DbRequest.getIngrId(woIngrList);

        //searching recipes for all recipies that contain the ingids
        ArrayList<RezeptCount> foundRezepte = DbRequest.searchMatching(ingrdID, woIngrdID);


        //sorts list and returns top 3 elements
        Collections.sort(foundRezepte, new Comparator<RezeptCount>() {
            @Override
            public int compare(RezeptCount o1, RezeptCount o2) {
                return o2.getCount() - o1.getCount();
            }
        });

        ArrayList<Rezept> returnRecipies = new ArrayList<>();
        for (RezeptCount rc : foundRezepte) {
            returnRecipies.add(rc.getRezept());
        }

        return returnRecipies;
    }

    private static ArrayList<Integer> getIngrId(ArrayList<String> ingrdsStringList) {
        ArrayList<Integer> ingrdID = new ArrayList<>();
        ArrayList<Zutat> allZutatenListe = JsonService.zutatenEinlesen();

        for (String ingr : ingrdsStringList) {
            for (Zutat ztat : allZutatenListe) {
                if (ztat.getSingular().contains(ingr) || ztat.getPlural().contains(ingr)) {
                    ingrdID.add(ztat.getZutatID());
                }
            }
        }
        return ingrdID;
    }


    private static ArrayList<RezeptCount> searchMatching(ArrayList<Integer> ingrdID, ArrayList<Integer> woIngrdID) {
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

            for (Integer currIngId : woIngrdID) {
                if (rezept.getZutaten().contains(currIngId)) {
                    match = false;
                }
            }

            if (match) {
                foundRezepte.add(new RezeptCount(rezept, matchCount));
            }
        }

        return foundRezepte;
    }



    public static ArrayList<Rezept> recipiesOutputSizeLimiter(ArrayList<Rezept> allRecipies, int startIndex, int endIndex){
        ArrayList<Rezept> recipies = new ArrayList<>();
        if (allRecipies.size() < 4) {
            recipies = allRecipies;
        } else {
            for (int index = startIndex; index < endIndex && index < allRecipies.size(); index++) {
                recipies.add(allRecipies.get(index));
            }
        }

        return recipies;
    }


}
