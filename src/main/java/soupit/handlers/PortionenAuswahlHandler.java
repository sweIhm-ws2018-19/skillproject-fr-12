package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import soupit.hilfsklassen.RezeptService;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.hilfsklassen.SlotFilter;
import soupit.hilfsklassen.TextService;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class PortionenAuswahlHandler implements RequestHandler {
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("PortionenAuswahlIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        String slotValue = SlotFilter.getSingleSlotValue(slots, "Anzahl");

        String speechText = "test";

        if(slotValue.equalsIgnoreCase("none")){
            speechText = "Bitte nenne die Anzahl an Portionen.";
        } else{
            int anzahl = Integer.parseInt(slotValue);
            speechText = "Alles klar. Wir kochen eine Suppe für " + anzahl + " Personen.";
        }

        SessionAttributeService.updateLastIntent(input, "PortionenAuswahlIntent");

        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
