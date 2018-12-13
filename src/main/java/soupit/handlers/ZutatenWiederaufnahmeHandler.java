package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;
import soupit.hilfsklassen.SlotFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static soupit.handlers.ZutatenAusschliessenAbfrageHandler.ZUTAT_AUSSCHLIESSEN_KEY;
import static soupit.hilfsklassen.SlotFilter.getIngredient;

public class ZutatenWiederaufnahmeHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZutatenWiederaufnahmeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        //get slots from current intend
        Map<String, Slot> slots = intent.getSlots();

        final ArrayList<String> zutatStringList = (ArrayList<String>) SlotFilter.getIngredient(slots);
        //ausgeschlossene Zutaten
        //input.getAttributesManager().setSessionAttributes(Collections.singletonMap(ZUTAT_AUSSCHLIESSEN_KEY, zutatStringList));
        ArrayList<String> ausgeschlosseneZutatenListe = (ArrayList<String>) input.getAttributesManager().getSessionAttributes().get(ZUTAT_AUSSCHLIESSEN_KEY);


        final String speechText;
        final String repromptText;
        boolean isAskResponse = false;

        // TODO: 13.12.2018  speechText anpassen
        if (ausgeschlosseneZutatenListe != null
            //|| !ausgeschlosseneZutatenListe.isEmpty()
                ) {
            if (ausgeschlosseneZutatenListe.containsAll(getIngredient(slots))) {
                ausgeschlosseneZutatenListe.removeAll(getIngredient(slots));
                speechText = "Die Zutat " + getIngredient(slots).toString() + " soll wiederhergestellt werden." +
                        " Die Liste der Ausgeschlossenen Zutaten enthält noch " + ausgeschlosseneZutatenListe.toString();
                repromptText = speechText;
            } else {
                speechText = "Zutat wurde nicht ausgeschlossen und konnte deshalb nicht wiederhergestellt werden.";
                repromptText = speechText;
            }
        } else {
            speechText = "Es wurden noch keine Zutaten ausgeschlossen. Sagen Sie zum Beispiel: Ich möchte Kartoffeln ausschließen";
            repromptText = speechText;
            isAskResponse = true;
        }



        ResponseBuilder responseBuilder = input.getResponseBuilder();

        responseBuilder.withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false);

        if (isAskResponse) {
            responseBuilder.withShouldEndSession(false)
                    .withReprompt(repromptText);
        }

        return responseBuilder.build();
    }
}
