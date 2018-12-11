package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.ArrayList;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZutatenAusschliessenAbfrageHandler implements RequestHandler {

    public static final String ZUTAT_AUSSCHLIESSEN_KEY = "ZUTAT_AUSSCHLIESSEN";

    @Override
    public boolean canHandle(HandlerInput input) { return input.matches(intentName("ZutatenAusschliessenAbfrageIntent")); }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;
        ArrayList<String> zutatenListe = (ArrayList<String>) input.getAttributesManager().getSessionAttributes().get(ZUTAT_AUSSCHLIESSEN_KEY);

        if (zutatenListe != null) {
            if (zutatenListe.size() == 1) {
                speechText = "Deine ausgeählte Zutat ist " + zutatenListe.get(0);
            } else {
                speechText = "Du hast folgende Zutaten ausgewählt: " + zutatenListe.toString();
            }
        } else {
            // es wurden noch keine Zutaten genannt
            speechText = "Ich weiss nicht welches Deine ausgewählte Zutat ist. Nenne mir eine Zutat. Sage zum Beispiel: Die Zutat ist Kartoffel.";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("SoupitSession", speechText)
                .withShouldEndSession(false)
                .build();
    }
}
