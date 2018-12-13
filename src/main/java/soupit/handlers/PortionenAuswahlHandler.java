package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.sun.org.apache.regexp.internal.RE;
import soupit.hilfsklassen.RezeptService;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.hilfsklassen.SlotFilter;
import soupit.hilfsklassen.TextService;
import soupit.model.Rezept;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class PortionenAuswahlHandler implements RequestHandler {
    private final static String PORTIONEN = "PORTIONEN";
    private final static String REZEPT = "REZEPT";

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

        String speechText;

        if (slotValue.equalsIgnoreCase("none")) {
            speechText = "Entschuldigung, ich habe dich nicht verstanden. Wiederhole es bitte noch einmal.";
            //würde mehr Sinn machen, entspricht jedoch nicht Invocable...
            //speechText = "Bitte nenne die Anzahl an Portionen.";
        } else {
            int anzahl = Integer.parseInt(slotValue);
            SessionAttributeService.setSingleSessionAttribute(input, PORTIONEN, anzahl);

            Rezept rezept = (Rezept) SessionAttributeService.getSingleSessionAttribute(input, REZEPT);

            speechText = TextService.zutatenVonRezeptVorlesen(RezeptService.getZutaten(rezept, anzahl), anzahl);
        }

        SessionAttributeService.updateLastIntent(input, "PortionenAuswahlIntent");

        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
