package soupit.hilfsklassen;

import soupit.model.Rezept;
import soupit.model.RezeptCount;
import soupit.model.Zutat;

import java.util.ArrayList;
import java.util.List;

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

    //hardcoded example should be removed
    public static List<String> getRecipies(List<String> ingredientList) {
        ArrayList<String> recipies = new ArrayList();
        if (ingredientList == null){
            return recipies;
        }

        //fill for Prototype
        if (ingredientList.contains("kartoffel"))
            recipies.add("kartoffelsuppe");
        if (ingredientList.contains("karotte"))
            recipies.add("karottensuppe");
        if (ingredientList.contains("tomate"))
            recipies.add("tomatensuppe");


        return recipies;
    }

    public static ArrayList<Rezept> dynamoTest(ArrayList<String> ingrdsStringList) {
        ArrayList<RezeptCount> foundRezepte = new ArrayList<>();

        ArrayList<Rezept> allRezepte = JsonService.rezepteEinlesen();
        ArrayList<Integer> ingrdID = DbRequest.getIngrId(ingrdsStringList);


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
            }
        }

        //here should be some best match algoritmic which returns max 3 elements
        ArrayList<Rezept> returnRecipies = new ArrayList<>();
        for (RezeptCount rc : foundRezepte){
            returnRecipies.add(rc.getRezept());
        }



        return returnRecipies;
    }

    public static ArrayList<Integer> getIngrId(ArrayList<String> ingrdsStringList) {
        ArrayList<Integer> ingrdID = new ArrayList<>();
        ArrayList<Zutat> allZutatenListe = JsonService.zutatenEinlesen();
        for (String ingr : ingrdsStringList) {
            for (Zutat ztat : allZutatenListe) {
                if (ztat.getSingular().equals(ingr)) {
                    ingrdID.add(ztat.getZutatID());
                }
            }
        }
        return ingrdID;
    }


}
