package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;
import org.json.JSONArray;
import soupit.hilfsklassen.DbRequest;
import soupit.hilfsklassen.JsonService;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.model.Rezept;

import java.util.ArrayList;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class WeitereRezepteHandler implements RequestHandler {
    private static final String BREAK_SECOND = "<break time=\"1s\"/>";
    private static final String REZEPT_FOUND = "REZEPT_FOUND";
    private static final String MORE_RECIPIES_GIVEN = "MORE_RECIPIES_GIVEN";
    private static final String ALL_MATCHED_RECIPIES = "ALL_MATCHED_RECIPIES";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("WeitereRezepteIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;
        final String repromptText;
        boolean isAskResponse = false;


        final String allRecipiesString = (String) SessionAttributeService.getSingleSessionAttribute(input, ALL_MATCHED_RECIPIES);
        Integer moreRead = (Integer) SessionAttributeService.getSingleSessionAttribute(input, MORE_RECIPIES_GIVEN);

        //check for nullpointers
        if (moreRead == null || allRecipiesString == null) {
            speechText = "Das weiß ich gerade nicht!";
            repromptText = speechText;
        } else {

            ArrayList<Rezept> allRecipies = JsonService.rezepteParsen(new JSONArray(allRecipiesString));
            ArrayList<Rezept> recipies = DbRequest.recipiesOutputSizeLimiter(allRecipies, moreRead, moreRead + 3);

            SessionAttributeService.setSingleSessionAttribute(input, MORE_RECIPIES_GIVEN, moreRead + recipies.size());

            final String json = new JSONArray(recipies).toString();
            SessionAttributeService.setSingleSessionAttribute(input, REZEPT_FOUND, json);


            if (recipies.isEmpty()) {
                speechText = "Hierzu kann ich dir leider kein weiteres Suppenrezept vorschlagen. Nenne mir eine andere Zutat, zum Beispiel eine Gemüsesorte.";
                repromptText = speechText;
                isAskResponse = true;

            } else {
                if (recipies.size() == 1) {
                    speechText = "Ich kann dir noch folgendes Rezept vorschlagen " + recipies.get(0).getName();
                } else {
                    String rezepte = DbRequest.suppenToString(recipies);
                    speechText = "Ich kann dir anhand der genannten Zutaten " + allRecipies.size() + " Rezepte vorschlagen: " + rezepte;
                }
                speechText += BREAK_SECOND + " Welche Suppe wählst du?";
                repromptText = speechText;

            }
        }

        SessionAttributeService.updateLastIntent(input, "WeitereRezepteIntent");

        ResponseBuilder responseBuilder = input.getResponseBuilder();

        responseBuilder.withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false);

        if (isAskResponse) {
            responseBuilder.withShouldEndSession(false)
                    .withReprompt(repromptText);
        }

        return responseBuilder.build();
    }

}
