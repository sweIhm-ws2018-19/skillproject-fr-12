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

        // TODO: 13.12.2018 If(!AusgeschlosseneZutat isEmpty()) momentan -> If(!wiederherZustellendeZutatAusMomentanemHandler isEmpty())
        // TODO: 13.12.2018  speechText anpassen
        // TODO: 13.12.2018 entferne "wieder aufzunehmende Zutat" aus List von "auszuschließenden Zutaten"
        if(!ausgeschlosseneZutatenListe.isEmpty()){
            if (ausgeschlosseneZutatenListe.containsAll(getIngredient(slots))){
            ausgeschlosseneZutatenListe.remove(getIngredient(slots).get(0));
                speechText = "Die Zutat " + getIngredient(slots).toString() + " soll wiederhergestellt werden." +
                " Die Liste der Ausgeschlossenen Zutaten enthält noch die folgenden Zutaten " + ausgeschlosseneZutatenListe.toString();
                repromptText = speechText;
            } else {
                speechText = "Zutat wurde nicht ausgeschlossen und konnte deshalb nicht wiederhergestellt werden.";
                repromptText = speechText;
            }
//            speechText = "test";
//            repromptText = speechText;
        } else {
            //wenn Auszuschließende Zutat kein Amazon.food oder die Liste ist leer -> fehler
            speechText = "Es wurde keine Zutat ausgeschlossen.";
            repromptText = speechText;
            //isAskResponse = true;
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
