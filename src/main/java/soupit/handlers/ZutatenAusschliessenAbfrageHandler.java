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
                speechText = "Deine ausgeschlosse Zutat ist " + zutatenListe.get(0);
            } else {
                speechText = "Deine ausgeschlossenen Zutaten sind: " + zutatenListe.toString();
            }
        } else {
            // es wurden noch keine Zutaten genannt
            speechText = "Du hast keine Zutaten ausgeschlossen. Nenne mir eine Zutat. Sage zum Beispiel: Ich möchte Kartoffel ausschließen.";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("SoupitSession", speechText)
                .withShouldEndSession(false)
                .build();
    }
}
