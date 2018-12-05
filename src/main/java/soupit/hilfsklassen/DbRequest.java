package soupit.hilfsklassen;

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
}
