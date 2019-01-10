package soupit.hilfsklassen;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Slot;
import org.json.JSONObject;
import soupit.model.RezeptCount;

import java.util.ArrayList;
import java.util.Map;

public class ZubereitungsSteuerungsLogik {
    private static ZubereitungsSteuerungsLogik instance;

    private final static String CURRENT_REZEPT = "CURRENT_REZEPT";

    private ZubereitungsSteuerungsLogik() {
        //empty
    }

    public static ZubereitungsSteuerungsLogik getInstance() {
        if (instance == null)
            instance = new ZubereitungsSteuerungsLogik();

        return instance;
    }

    public static String getNext(HandlerInput input, Map<String, Slot> slots, int direction) {
        final String speechText;
        String rezeptString = (String) input.getAttributesManager().getSessionAttributes().get(CURRENT_REZEPT);


        String slotValue = SlotFilter.getSingleSlotCheckedValue(slots, "Anzahl");


        final int stepsToGo;

        if (slotValue.equals("none")) {
            stepsToGo = direction;
        } else {
            stepsToGo = Integer.parseInt(slotValue) * (direction);
        }


        if (rezeptString == null) {
            rezeptString = DbRequest.getRezeptFromDynDB(input);
        }

        if (rezeptString == null || rezeptString.equals("none")) {
            speechText = "Das weiß ich leider nicht!";
        } else {
            RezeptCount rezept = JsonService.rezeptCountParsen(new JSONObject(rezeptString));
            rezept.setCount(rezept.getCount() + stepsToGo);
            ArrayList<String> steps = rezept.getRezept().getSchritte();
            if (rezept.getCount() < 0 || rezept.getCount() >= steps.size()) {
                speechText = "Die Zubereitung ist bereits Abgeschlossen.";
            } else {
                if (steps.size() == rezept.getCount() - 1) {
                    speechText = steps.get(rezept.getCount()) + "Ich hoffe die Suppe schmeckt und wünsche einen guten Appetit. Um das Rezept abzuschließen sage: Rezept abschließen.";
                } else {
                    speechText = steps.get(rezept.getCount());
                }


                String currRezString = new JSONObject(rezept).toString();
                SessionAttributeService.setSingleSessionAttribute(input, CURRENT_REZEPT, currRezString);
                PersistentAttributeService.setSinglePersistentAttribute(input, CURRENT_REZEPT, currRezString);

            }

        }
        return speechText;
    }
}
