package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import soupit.hilfsklassen.PersistentAttributeService;
import soupit.hilfsklassen.SessionAttributeService;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZubereitungAbschliessenHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZubereitungAbschliessenIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Bis zum nächsten mal!";
        SessionAttributeService.setSingleSessionAttribute(input, "CURRENT_REZEPT", null);
        PersistentAttributeService.setSinglePersistentAttribute(input, "CURRENT_REZEPT", null);

        SessionAttributeService.updateLastIntent(input, "ZubereitungAbschliessenIntent ");

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("SoupitSession", speechText)
                .withShouldEndSession(false)
                .build();
    }
}
