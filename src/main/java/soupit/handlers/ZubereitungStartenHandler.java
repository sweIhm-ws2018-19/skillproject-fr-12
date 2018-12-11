package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import soupit.hilfsklassen.RezeptService;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.hilfsklassen.TextService;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZubereitungStartenHandler implements RequestHandler {
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZubereitungStartenIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = TextService.zutatenVonRezeptVorlesen(RezeptService.getZutaten(0));

        SessionAttributeService.updateLastIntent(input, "ZubereitungStartenIntent");

        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
