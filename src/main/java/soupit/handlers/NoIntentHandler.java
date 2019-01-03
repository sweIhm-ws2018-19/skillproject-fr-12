package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import soupit.hilfsklassen.SessionAttributeService;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class NoIntentHandler implements RequestHandler {
        private final static String BREAK_HALF_SECOND = "<break time=\"500ms\"/>";
        private final static String PORTIONEN_AUSWAHL_INTENT = "PortionenAuswahlIntent";

        @Override
        public boolean canHandle(HandlerInput input) {
            return input.matches(intentName("AMAZON.NoIntent"));
        }

        @Override
        public Optional<Response> handle(HandlerInput input) {
            String speechText;

            String lastIntent = SessionAttributeService.getLastIntent(input);

            if (lastIntent.equalsIgnoreCase(PORTIONEN_AUSWAHL_INTENT)) {
                speechText = BREAK_HALF_SECOND ;

                SessionAttributeService.updateLastIntent(input, "PortionenNoIntent");
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
