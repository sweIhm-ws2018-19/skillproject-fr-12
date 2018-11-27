package main.java.soupit.Hilfsklassen;

import com.amazon.ask.model.Slot;

import java.util.ArrayList;
import java.util.Map;

public class IngredientSlotFilter {

    public static ArrayList<String> getIngredient(Map<String, Slot> slots) {

        final ArrayList<String> zutatStringList = new ArrayList<>();
        final ArrayList<String> returnList = new ArrayList<>();


        //get ingredient from slots
        for (Map.Entry<String, Slot> slotEntry : slots.entrySet()) {
            //check if slot is not empty
            if (slotEntry.getValue().getResolutions() != null) {
                // only accept matches from alexa dev console
                if (slotEntry.getValue().getResolutions().getResolutionsPerAuthority().get(0).getStatus().getCode().toString().equals("ER_SUCCESS_MATCH")){
                    zutatStringList.add(slotEntry.getValue().getResolutions().getResolutionsPerAuthority().get(0).getValues().get(0).getValue().getName());
                }
            }
        }
        //filters doubles
        for (String ingredient : zutatStringList){
            if (!returnList.contains(ingredient)){
                returnList.add(ingredient);
            }
        }
        return returnList;
    }

}