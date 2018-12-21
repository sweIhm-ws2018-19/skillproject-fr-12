package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import soupit.hilfsklassen.SessionAttributeService;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class YesIntentHandler implements RequestHandler {
    private final static String BREAK_HALF_SECOND = "<break time=\"500ms\"/>";
    private final static String PORTIONEN_AUSWAHL_INTENT = "PortionenAuswahlIntent";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.YesIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;

        String lastIntent = SessionAttributeService.getLastIntent(input);

        if (lastIntent.equalsIgnoreCase(PORTIONEN_AUSWAHL_INTENT)) {
            speechText = "Super! " + BREAK_HALF_SECOND + "Sobald du mit dem Kochen anfangen möchtest, sage: Rezept starten"; //TextService.schritteVonRezeptVorlesen(null);

            SessionAttributeService.updateLastIntent(input, "PortionenYesIntent");
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
