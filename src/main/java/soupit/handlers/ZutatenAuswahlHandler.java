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
import soupit.hilfsklassen.DbRequest;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.hilfsklassen.SlotFilter;

import java.util.*;

import static com.amazon.ask.request.Predicates.intentName;
import static soupit.handlers.ZutatenAbfrageHandler.ZUTAT_KEY;

public class ZutatenAuswahlHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZutatenAuswahlIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();


        final ArrayList<String> zutatStringList = (ArrayList<String>) SlotFilter.getIngredient(slots);

        final String speechText;
        final String repromptText;
        boolean isAskResponse = false;


        SessionAttributeService.setSingleSessionAttribute(input, ZUTAT_KEY, zutatStringList);
        ArrayList<String> recipies = (ArrayList<String>) DbRequest.getRecipies(zutatStringList);

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

}
