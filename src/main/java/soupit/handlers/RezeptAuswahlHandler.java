package main.java.soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.slu.entityresolution.Resolution;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class RezeptAuswahlHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("RezeptAuswahlIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;
        String[] rezepte = getRezepte(input);

        if (rezepte[0] != null && rezepte[0] != "") {
            speechText = "Alles klar. Es wird eine Tomatensuppe gekocht.";
        } else {
            // Es wurden noch keine Zutaten genannt -> es können keine Rezepte vorgeschlagen werden
            speechText = "Ich kann dir kein Rezept vorschlagen. Bitte wähle zuerst Zutaten aus.";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("ColorSession", speechText)
                .build();
    }

    /**
     * @param input: HandlerInput
     * @return falls Rezepete in den Session Attributen gespichert wurden: die ersten drei Rezepte
     */
    private String[] getRezepte(HandlerInput input) {
        String[] rezepte = new String[3];

        //hier mit .getSessionAttributes() die gespeicherten Rezepte holen

        //übergangslosung
        rezepte = new String[]{"kartoffelsuppe", "karottensuppe", "tomatensuppe"};

        return rezepte;
    }


    public String[] getSuppenWahl(Map<String, Slot> slots) {
        String[] ret = new String[2];

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
