package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import org.json.JSONArray;
import soupit.hilfsklassen.*;
import soupit.model.Rezept;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class PortionenAuswahlHandler implements RequestHandler {
    private static final String PORTIONEN = "PORTIONEN";
    private static final String REZEPT_FOUND = "REZEPT_FOUND";
    private static final String REZEPT_INDEX = "REZEPT_INDEX";

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
        } else {
            int anzahl = Integer.parseInt(slotValue);
            SessionAttributeService.setSingleSessionAttribute(input, PORTIONEN, anzahl);

            int suppenIndex = (int) SessionAttributeService.getSingleSessionAttribute(input, REZEPT_INDEX);
            ArrayList<Rezept> rezepte = JsonService.rezepteParsen(new JSONArray((String)SessionAttributeService.getSingleSessionAttribute(input, REZEPT_FOUND)));
            Rezept rezept = rezepte.get(suppenIndex);

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
