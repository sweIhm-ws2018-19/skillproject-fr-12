package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static soupit.handlers.ZutatenAusschliessenAbfrageHandler.ZUTAT_AUSSCHLIESSEN_KEY;
import static soupit.hilfsklassen.SlotFilter.getIngredient;

public class ZutatenWiederaufnahmeHandler implements RequestHandler {
    private static final String KEINE_ZUTATEN_AUSGESCHLOSSEN = "Es wurden noch keine Zutaten ausgeschlossen. Sagen Sie zum Beispiel: Ich möchte Kartoffeln ausschließen";

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

        //ausgeschlossene Zutaten bekommen
        ArrayList<String> ausgeschlosseneZutatenListe = (ArrayList<String>) input.getAttributesManager().getSessionAttributes().get(ZUTAT_AUSSCHLIESSEN_KEY);


        final String speechText;
        final String repromptText;
        boolean isAskResponse = false;

        if (ausgeschlosseneZutatenListe != null) {
            if (ausgeschlosseneZutatenListe.containsAll(getIngredient(slots))) {
                //entfernt die Zutaten, von der Liste der ausgeschlossenenZutaten, sodass sie wieder verwendet werden können.
                ausgeschlosseneZutatenListe.removeAll(getIngredient(slots));
                //prüft ob die Liste der ausgeschlossenen Zutaten leer ist. -> individueller speechText
                if (!ausgeschlosseneZutatenListe.isEmpty()) {
                    speechText = "Die Zutat " + getIngredient(slots).toString() + " wurde wiederhergestellt." +
                            " Die Liste der Ausgeschlossenen Zutaten enthält noch " + ausgeschlosseneZutatenListe.toString();
                    repromptText = speechText;
                }else {
                    speechText = "Die Zutat " + getIngredient(slots).toString() + " wurde wiederhergestellt." +
                            " Die Liste der Ausgeschlossenen Zutaten ist jetzt leer.";
                    repromptText = speechText;
                }
            } else {
                speechText = "Zutat wurde nicht ausgeschlossen und konnte deshalb nicht wiederhergestellt werden.";
                repromptText = speechText;
            }
        } else {
            speechText = KEINE_ZUTATEN_AUSGESCHLOSSEN;
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
