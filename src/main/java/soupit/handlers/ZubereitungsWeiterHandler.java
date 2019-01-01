package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import org.json.JSONArray;
import soupit.hilfsklassen.JsonService;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.hilfsklassen.TextService;
import soupit.model.Rezept;

import java.util.ArrayList;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZubereitungsWeiterHandler implements RequestHandler {
    private final static String PORTIONEN_YES_INTENT = "PortionenYesIntent";
    private final static String REZEPT_FOUND = "REZEPT_FOUND";
    private final static String REZEPT_INDEX = "REZEPT_INDEX";
    private final static String CURRENT_REZEPT_STEP = "CURRENT_REZEPT_STEP";
    private final static String CURRENT_REZEPT = "CURRENT_REZEPT";


    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZubereitungsSteuerungIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;
        Rezept rezept = (Rezept) SessionAttributeService.getSingleSessionAttribute(input, CURRENT_REZEPT);
        Integer currStep = (Integer) SessionAttributeService.getSingleSessionAttribute(input, CURRENT_REZEPT_STEP);

        if (rezept == null || currStep == null) {
            speechText = "Das weiß ich leider nicht!";
        } else {
            ArrayList<String> steps = rezept.getSchritte();
            if (currStep < 0 || currStep >= steps.size()) {
                speechText = "Die Zubereitung ist bereits Abgeschlossen.";
            } else {
                if (steps.size() == currStep - 1) {
                    speechText = steps.get(currStep) + "Ich hoffe die Suppe schmeckt und wünsche einen guten Appetit. Bis zum nächsten Mal.";
                } else {
                    speechText = steps.get(currStep);
                }
                currStep++;
                SessionAttributeService.setSingleSessionAttribute(input, CURRENT_REZEPT_STEP, currStep);

            }

        }


        SessionAttributeService.updateLastIntent(input, "ZubereitungsWeiterHandler");
        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
