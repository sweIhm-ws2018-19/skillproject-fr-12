/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;
import org.json.JSONArray;
import soupit.hilfsklassen.DbRequest;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.hilfsklassen.SlotFilter;
import soupit.model.Rezept;

import java.util.*;

import static com.amazon.ask.request.Predicates.intentName;
import static soupit.handlers.ZutatenAbfrageHandler.ZUTAT_KEY;
import static soupit.handlers.ZutatenAusschliessenAbfrageHandler.ZUTAT_AUSSCHLIESSEN_KEY;

public class ZutatenAuswahlHandler implements RequestHandler {
    private static final String BREAK_SECOND = "<break time=\"1s\"/>";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZutatenAuswahlIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        //get slots from current intent
        Map<String, Slot> slots = intent.getSlots();


        final ArrayList<String> zutatStringList = (ArrayList<String>) SlotFilter.getIngredient(slots);

        String speechText;
        final String repromptText;
        boolean isAskResponse = false;

        ArrayList<String> ausgeschlosseneZutatenListe = (ArrayList<String>) input.getAttributesManager().getSessionAttributes().get(ZUTAT_AUSSCHLIESSEN_KEY);

        if (ausgeschlosseneZutatenListe == null){
            ausgeschlosseneZutatenListe = new ArrayList<String>();
        }

        SessionAttributeService.setSingleSessionAttribute(input, ZUTAT_KEY, zutatStringList);
        ArrayList<Rezept> recipies = DbRequest.getRecipies(zutatStringList, ausgeschlosseneZutatenListe);

        String json = new JSONArray(recipies).toString();
        SessionAttributeService.setSingleSessionAttribute(input, "REZEPT_FOUND", json);


        if (!recipies.isEmpty()) {
            if (recipies.size() == 1) {
                speechText = "Ich kann dir folgendes Rezept vorschlagen " + recipies.get(0).getName();
            } else {
                String rezepte = this.suppenToString(recipies);
                speechText = "Ich kann dir anhand der genannten Zutaten " + recipies.size() +  " Rezepte vorschlagen: " + rezepte;
            }
            speechText += BREAK_SECOND + " Welche Suppe wählst du?";
            repromptText = speechText;

        } else {
            speechText = "Hierzu kann ich dir aktuell leider kein passendes Suppenrezept vorschlagen. Nenne mir eine andere Zutat, zum Beispiel eine Gemüsesorte.";
            repromptText =
                    "Hierzu kann ich dir aktuell leider kein passendes Suppenrezept vorschlagen. Nenne mir eine andere Zutat, zum Beispiel eine Gemüsesorte.";
            isAskResponse = true;

        }

        SessionAttributeService.updateLastIntent(input, "ZutatenAuswahlIntent");

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

    private String suppenToString(ArrayList<Rezept> rezepts){
        String returnString = "";
        for (int index = 0; index < rezepts.size(); index ++){
            if (index == rezepts.size() -1 ){
                returnString = returnString.substring(0,returnString.length()-2) + " und " + rezepts.get(index).getName();
            } else {
                returnString = returnString.concat(rezepts.get(index).getName()).concat(", ");
            }
        }
    return returnString;
    }

}
