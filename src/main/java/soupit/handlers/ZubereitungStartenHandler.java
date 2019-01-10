package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import soupit.hilfsklassen.JsonService;
import soupit.hilfsklassen.PersistentAttributeService;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.model.Rezept;
import soupit.model.RezeptCount;

import java.util.ArrayList;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZubereitungStartenHandler implements RequestHandler {
    private final static String PORTIONEN_YES_INTENT = "PortionenYesIntent";
    private final static String REZEPT_FOUND = "REZEPT_FOUND";
    private final static String REZEPT_INDEX = "REZEPT_INDEX";
    private final static String CURRENT_REZEPT = "CURRENT_REZEPT";

    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZubereitungStartenIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;

        String lastIntent = SessionAttributeService.getLastIntent(input);

        if (lastIntent.equalsIgnoreCase(PORTIONEN_YES_INTENT)) {
            int suppenIndex = (int) SessionAttributeService.getSingleSessionAttribute(input, REZEPT_INDEX);
            ArrayList<Rezept> rezepte = JsonService.rezepteParsen(new JSONArray((String) SessionAttributeService.getSingleSessionAttribute(input, REZEPT_FOUND)));
            Rezept rezept = rezepte.get(suppenIndex);


            RezeptCount rezeptCount = new RezeptCount(rezept, 0);
            String rezeptCountJsonO = new JSONObject(rezeptCount).toString();

            SessionAttributeService.setSingleSessionAttribute(input, CURRENT_REZEPT, rezeptCountJsonO);
            PersistentAttributeService.setSinglePersistentAttribute(input, CURRENT_REZEPT, rezeptCountJsonO);

            speechText = rezept.getSchritte().get(0);


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
