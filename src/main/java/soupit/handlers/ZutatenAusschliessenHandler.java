package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static soupit.handlers.ZutatenAusschliessenAbfrageHandler.ZUTAT_AUSSCHLIESSEN_KEY;

public class ZutatenAusschliessenHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) { return input.matches(intentName("ZutatenAusschliessenIntent")); }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        final ArrayList<String> zutatStringList = (ArrayList<String>) soupit.hilfsklassen.SlotFilter.getIngredient(slots);

        final String speechText;
        final String repromptText;
        boolean isAskResponse = false;

        input.getAttributesManager().setSessionAttributes(Collections.singletonMap(ZUTAT_AUSSCHLIESSEN_KEY, zutatStringList));
        ArrayList<String> recipies = (ArrayList<String>) soupit.hilfsklassen.DbRequest.getRecipies(zutatStringList);


        if (!recipies.isEmpty()) {

            if (zutatStringList.size() == 1) {

                speechText =
                        "Volgendes Rezept enthält eine Zutat, die ausgeschlossen wurden " + recipies.get(0);
                repromptText = speechText;
            } else {
                speechText = "Volgende Rezepte enthalten Zutaten, die ausgeschlossen wurden " + recipies.toString();
                repromptText = speechText;
            }

        } else {
            speechText = "Keine Zutaten wurden ausgeschlossen.";
            repromptText = speechText;
            isAskResponse = true;

        }


        ResponseBuilder responseBuilder = input.getResponseBuilder();
        responseBuilder.withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false);
        if (isAskResponse) {
            responseBuilder.withShouldEndSession(false)
                    .withReprompt(repromptText);
        }
        return responseBuilder.build();

//        return input.getResponseBuilder()
//                .withSpeech(speechText)
//                .withSimpleCard("SoupitSession", speechText)
//                .withShouldEndSession(false)
//                .build();}

    }

//    public String[] getIngredient() {
//        String[] ingredient;
//        ingredient= new String[]{"Kartoffel, Tomate, Gurke, Karotte"};
//        return ingredient;
//    }
//
//    public String[] getExcludedIngredient(){
//        String[] excludedIngredient;
//        excludedIngredient= new String[] {"Kartoffel, Karotte"};
//        return excludedIngredient;
//    }
//
//    public String[] removeExcludedIngredient(String[] ingredient, String[] excludedIngredient){
//        String[] ingredientResult;
//        if (ingredient != null){}
//        return null;
//    }
}
