package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import soupit.hilfsklassen.*;
import soupit.model.Rezept;
import soupit.model.RezeptCount;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZubereitungsWeiterHandler implements RequestHandler {
    private final static String PORTIONEN_YES_INTENT = "PortionenYesIntent";
    private final static String REZEPT_FOUND = "REZEPT_FOUND";
    private final static String REZEPT_INDEX = "REZEPT_INDEX";
    private final static String CURRENT_REZEPT = "CURRENT_REZEPT";


    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZubereitungsWeiterIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        //get slots from current intent s
        Map<String, Slot> slots = intent.getSlots();

        String speechText;
        String rezeptString = (String) input.getAttributesManager().getSessionAttributes().get(CURRENT_REZEPT);


        String slotValue = SlotFilter.getSingleSlotCheckedValue(slots, "Anzahl");


        final int stepsToGo;

        if (slotValue.equals("none")) {
            stepsToGo = 1;
        } else {
            stepsToGo = Integer.parseInt(slotValue);
        }


        if (rezeptString == null) {
            rezeptString = DbRequest.getRezeptFromDynDB(input);
        }

        if (rezeptString == null || rezeptString.equals("none")) {
            speechText = "Das weiß ich leider nicht!";
        }
        else {
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


        //SessionAttributeService.updateLastIntent(input, "ZubereitungsWeiterHandler");
        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
