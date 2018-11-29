package main.java.soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
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
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        String speechText;
        String[] rezepte = getRezepte(input);
        String[] suppenWahl = getSuppenWahl(slots);

        if (rezepte[0] != null && rezepte[0] != "") {
            if(suppenWahl[0].equals("Zahl")){
                speechText = String.format("Alles klar. Wir werden eine %s kochen.", checkSuppeZahl(suppenWahl[1], rezepte));
            }
            else if(suppenWahl[0].equals("Suppe")){
                speechText = String.format("Alles klar. Wir werden eine %s kochen.", checkSuppeText(suppenWahl[1], rezepte));
            }
            else{
                speechText = "Ich kann dir kein Rezept vorschlagen. Bitte wähle zuerst Zutaten aus.";
            }

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

    private String checkSuppeZahl(String suppe, String[] rezepte){
        String dieSuppe = "";

        int index = Integer.parseInt(suppe) + 1;

        if(index <= rezepte.length){
            dieSuppe = rezepte[index - 1];
        }

        return dieSuppe;
    }

    private String checkSuppeText(String suppe, String[] rezepte){
        String dieSuppe = "";

        for(String rezept: rezepte){
            if(suppe.equals(rezept))
                dieSuppe = suppe;
        }

        return dieSuppe;
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
