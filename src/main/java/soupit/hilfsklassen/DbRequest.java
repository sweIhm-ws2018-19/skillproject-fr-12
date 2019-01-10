package soupit.hilfsklassen;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import soupit.model.Rezept;
import soupit.model.RezeptCount;
import soupit.model.Zutat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Handler;

public final class DbRequest {
    private final static String CURRENT_REZEPT = "CURRENT_REZEPT";
    private static DbRequest instance;

    private DbRequest() {
        //emptys
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
        for (int index = 0; index < foundRezepte.size() && index < 15; index++) {
            returnRecipies.add(foundRezepte.get(index).getRezept());
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


    public static ArrayList<Rezept> recipiesOutputSizeLimiter(ArrayList<Rezept> allRecipies, int startIndex, int endIndex) {
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


    public static String suppenToString(ArrayList<Rezept> rezepts) {
        String returnString = "";
        for (int index = 0; index < 3 && index < rezepts.size(); index++) {
            if (index == rezepts.size() - 1) {
                returnString = returnString.substring(0, returnString.length() - 2) + " und " + rezepts.get(index).getName();
            } else {
                returnString = returnString.concat(rezepts.get(index).getName()).concat(", ");
            }
        }
        return returnString;
    }

    public static String getRezeptFromDynDB(HandlerInput input) {
        return (String) PersistentAttributeService.getSinglePersistentAttribute(input, CURRENT_REZEPT);
    }
}
