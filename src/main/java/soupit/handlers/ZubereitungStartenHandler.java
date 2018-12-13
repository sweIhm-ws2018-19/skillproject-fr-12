package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import org.json.JSONArray;
import soupit.hilfsklassen.JsonService;
import soupit.hilfsklassen.RezeptService;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.hilfsklassen.TextService;
import soupit.model.Rezept;

import java.util.ArrayList;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZubereitungStartenHandler implements RequestHandler {
    private final static String PORTIONEN_YES_INTENT = "PortionenYesIntent";
    private final static String REZEPT_FOUND = "REZEPT_FOUND";
    private final static String REZEPT_INDEX = "REZEPT_INDEX";

    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZubereitungStartenIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;

        String lastIntent = SessionAttributeService.getLastIntent(input);

        if (lastIntent.equalsIgnoreCase(PORTIONEN_YES_INTENT)) {
            int suppenIndex = (int) SessionAttributeService.getSingleSessionAttribute(input, REZEPT_INDEX);
            ArrayList<Rezept> rezepte = JsonService.rezepteParsen(new JSONArray((String)SessionAttributeService.getSingleSessionAttribute(input, REZEPT_FOUND)));
            Rezept rezept = rezepte.get(suppenIndex);

            speechText = TextService.schritteVonRezeptVorlesen(rezept);

            SessionAttributeService.updateLastIntent(input, "ZubereitungStartenIntent");
        } else {
            speechText = "Entschuldigung, das habe ich nicht verstanden. Könntest du das bitte wiederholen?";
        }

        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
