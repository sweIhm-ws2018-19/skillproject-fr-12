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

public class ZubereitungsSteuerungHandler implements RequestHandler {
    private final static String PORTIONEN_YES_INTENT = "PortionenYesIntent";
    private final static String REZEPT_FOUND = "REZEPT_FOUND";
    private final static String REZEPT_INDEX = "REZEPT_INDEX";
    private final static String CURRENT_REZEPT_STEP = "CURRENT_REZEPT_STEP";
    private final static String CURRENT_REZEPT = "CURRENT_REZEPT";


    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZubereitungStartenIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;
    SessionAttributeService.getSingleSessionAttribute(input, CURRENT_REZEPT);
    SessionAttributeService.getSingleSessionAttribute(input, CURRENT_REZEPT_STEP);

    if ()





        SessionAttributeService.updateLastIntent(input, "ZubereitungsSteuerungHandler");
        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
