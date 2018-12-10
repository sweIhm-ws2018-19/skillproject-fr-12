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
import static soupit.handlers.ZutatenAbfrageHandler.ZUTAT_KEY;

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

        input.getAttributesManager().setSessionAttributes(Collections.singletonMap(ZUTAT_KEY, zutatStringList));
        ArrayList<String> recipies = (ArrayList<String>) soupit.hilfsklassen.DbRequest.getRecipies(zutatStringList);


        if (!recipies.isEmpty()) {

            if (zutatStringList.size() == 1) {

                speechText =
                        "Ich kann dir folgendes Rezept vorschlagen " + recipies.get(0);
                repromptText = speechText;
            } else {
                speechText = "Ich kann dir folgende Rezepte vorschlagen " + recipies.toString();
                repromptText = speechText;
            }

        } else {
            speechText = "Hierzu kann ich dir aktuell leider kein passendes Suppenrezept vorschlagen. Nenne mir eine andere Zutat, zum Beispiel eine Gemüsesorte.";
            repromptText = speechText;
            isAskResponse = true;

        }


        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .withSimpleCard("SoupitSession", speechText)
                .build();
    }

    public String[] getIngredient() {
        String[] ingredient;
        ingredient= new String[]{"Kartoffel, Tomate, Gurke, Karotte"};
        return ingredient;
    }

    public String[] getExcludedIngredient(){
        String[] excludedIngredient;
        excludedIngredient= new String[] {"Kartoffel, Karotte"};
        return excludedIngredient;
    }

    public String[] removeExcludedIngredient(String[] ingredient, String[] excludedIngredient){
        String[] ingredientResult;
        if (ingredient != null){

        }


        return null;
    }
}
