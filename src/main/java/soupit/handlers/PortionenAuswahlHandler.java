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
    private final static String PORTIONEN = "PORTIONEN";

    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("PortionenAuswahlIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();
        String slotValue = SlotFilter.getSingleSlotInputValue(slots, "Anzahl");

        String speechText = "test";

        if (slotValue.equalsIgnoreCase("none")) {
            speechText = "Bitte nenne die Anzahl an Portionen.";
        } else {
            int anzahl = Integer.parseInt(slotValue);
            SessionAttributeService.setSingleSessionAttribute(input, PORTIONEN, anzahl);

            speechText = TextService.zutatenVonRezeptVorlesen(RezeptService.getZutaten(0, anzahl));
        }

        SessionAttributeService.updateLastIntent(input, "PortionenAuswahlIntent");

        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
