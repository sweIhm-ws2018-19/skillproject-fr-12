package main.java.soupit.Hilfsklassen;

import com.sun.org.apache.xerces.internal.xs.StringList;

import java.util.ArrayList;

public class DbRequest {

    public static ArrayList<String> getRecipies(ArrayList<String> ingredientList) {
        ArrayList<String> recipies = new ArrayList();
        if (ingredientList.contains("kartoffel"))
            recipies.add("kartoffelsuppe");
        if (ingredientList.contains("karotte"))
            recipies.add("karottensuppe");
        if (ingredientList.contains("tomate"))
            recipies.add("tomatensuppe");


        return recipies;
    }
}
