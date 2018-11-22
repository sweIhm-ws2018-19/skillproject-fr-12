/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package main.java.soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static main.java.soupit.handlers.ZutatenAbfrageHandler.ZUTAT_KEY;
import static main.java.soupit.handlers.ZutatenAbfrageHandler.ZUTAT_SLOT;

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

        // Get the zutat slot from the list of slots.
        Slot zutatSlot = slots.get(ZUTAT_SLOT);

        String speechText, repromptText;
        boolean isAskResponse = false;

        // Check for favorite color and create output to user.
        if (zutatSlot != null) {
            // Store the user's favorite color in the Session and create response.
            String favoriteZutat = zutatSlot.getValue();
            input.getAttributesManager().setSessionAttributes(Collections.singletonMap(ZUTAT_KEY, favoriteZutat));

            if(favoriteZutat != null){
                speechText =
                        String.format("Deine ausgeählte Zutat ist %s. Du kannst mich jetzt nach Deiner Zutat fragen. "
                                + "Frage einfach: was ist meine zutat?", favoriteZutat);
                repromptText =
                        "Frage nach meiner Zutat.";
            }
            else{
                speechText = "Ich kenne Deine Zutat nicht. Bitte versuche es noch einmal.";
                repromptText =
                        "Ich weiss nicht welches Deine ausgewählte Zutat ist. Sag mir Deine Zutat. Sage zum Beispiel: Die Zutat ist Kartoffel.";
                isAskResponse = true;
            }


        } else {
            // Render an error since we don't know what the users favorite color is.
            speechText = "Ich kenne Deine Zutat nicht. Bitte versuche es noch einmal.";
            repromptText =
                    "Ich weiss nicht welches Deine ausgewählte Zutat ist. Sag mir Deine Zutat. Sage zum Beispiel: Die Zutat ist Kartoffel.";
            isAskResponse = true;
        }

        ResponseBuilder responseBuilder = input.getResponseBuilder();

        responseBuilder.withSimpleCard("ColorSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false);

        if (isAskResponse) {
            responseBuilder.withShouldEndSession(false)
                    .withReprompt(repromptText);
        }

        return responseBuilder.build();
    }

}
