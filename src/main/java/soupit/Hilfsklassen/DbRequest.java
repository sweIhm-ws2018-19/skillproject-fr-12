package soupit.Hilfsklassen;

import java.util.ArrayList;

public final class DbRequest {

    public static ArrayList<String> getRecipies(ArrayList<String> ingredientList) {
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
}
