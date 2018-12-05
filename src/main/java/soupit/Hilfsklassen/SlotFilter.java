package soupit.Hilfsklassen;

import com.amazon.ask.model.Slot;
import com.amazon.ask.model.slu.entityresolution.Resolution;

import java.util.ArrayList;
import java.util.Map;

public final class SlotFilter {

    private SlotFilter() {
        throw new IllegalStateException("Utility class");
    }

    public static ArrayList<String> getIngredient(Map<String, Slot> slots) {

        final ArrayList<String> zutatStringList = new ArrayList<>();
        final ArrayList<String> returnList = new ArrayList<>();

        if (slots == null || slots.isEmpty())
            return returnList;

        //get ingredient from slots
        for (Map.Entry<String, Slot> slotEntry : slots.entrySet()) {
            //check if slot is not empty
            if (slotEntry.getValue().getResolutions() != null) {
                // only accept matches from alexa dev console
                if (slotEntry.getValue().getResolutions().getResolutionsPerAuthority().get(0).getStatus().getCode().toString().equals("ER_SUCCESS_MATCH")) {
                    zutatStringList.add(slotEntry.getValue().getResolutions().getResolutionsPerAuthority().get(0).getValues().get(0).getValue().getName());
                }
            }
        }
        //filters doubles
        for (String ingredient : zutatStringList) {
            if (!returnList.contains(ingredient)) {
                returnList.add(ingredient);
            }
        }
        return returnList;
    }


    /**
     * @param slots: alle slots von input
     * @return {slotName, value} (beides "" wenn kein "ER_SUCCESS_MATCH"
     */
    public static String[] getSuppenWahl(Map<String, Slot> slots) {
        String[] ret = new String[2];
        ret[0] = ret[1] = "";

        //get ingredient from slots
        for (Map.Entry<String, Slot> slotEntry : slots.entrySet()) {

            //check if slot is not empty
            if (slotEntry.getValue().getResolutions() != null) {
                Resolution resolution = slotEntry.getValue().getResolutions().getResolutionsPerAuthority().get(0);

                // only accept matches
                if (resolution.getStatus().getCode().toString().equals("ER_SUCCESS_MATCH")) {
                    ret[0] = slotEntry.getValue().getName();
                    ret[1] = resolution.getValues().get(0).getValue().getName();
                }
            }
        }

        return ret;
    }

}