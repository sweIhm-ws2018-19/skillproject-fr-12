package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import org.json.JSONArray;
import soupit.hilfsklassen.JsonService;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.model.Rezept;

import java.util.ArrayList;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class NoIntentHandler implements RequestHandler {
    private final static String BREAK_HALF_SECOND = "<break time=\"500ms\"/>";
    private final static String PORTIONEN_AUSWAHL_INTENT = "PortionenAuswahlIntent";

    private final static String REZEPT_INDEX = "REZEPT_INDEX";
    private final static String REZEPT_FOUND = "REZEPT_FOUND";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.NoIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;
        String repromptText;

        String lastIntent = SessionAttributeService.getLastIntent(input);

        if (lastIntent.equalsIgnoreCase(PORTIONEN_AUSWAHL_INTENT)) {
            int suppenIndex = (int) SessionAttributeService.getSingleSessionAttribute(input, REZEPT_INDEX);
            ArrayList<Rezept> rezepte = JsonService.rezepteParsen(new JSONArray((String) SessionAttributeService.getSingleSessionAttribute(input, REZEPT_FOUND)));
            Rezept rezept = rezepte.get(suppenIndex);

            speechText = "Welches Rezept möchtest du statdessen kochen? Ich kann dir folgende Rezepte vorschlagen: " + getRezipes(rezepte);
            repromptText = speechText;

            SessionAttributeService.updateLastIntent(input, "PortionenNoIntent");
        } else {
            speechText = "Entschuldigung, das habe ich nicht verstanden. Könntest du das bitte wiederholen?";
            repromptText = speechText;
        }

        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .withShouldEndSession(false)
                .build();
    }

    public String getRezipes(ArrayList<Rezept> rezepte){
        String alleRezepte = "";
        for (Rezept aRezepte : rezepte) {
            new StringBuilder().append(alleRezepte).append(" ").append(aRezepte.getName());
        }
        return alleRezepte;
    }

}
