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
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZutatenAbfrageHandler implements RequestHandler {
    public static final String ZUTAT_KEY = "ZUTAT";
    public static final String ZUTAT_SLOT = "Zutat";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZutatenAbfrageIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText;
        String favoriteZutat = (String) input.getAttributesManager().getSessionAttributes().get(ZUTAT_KEY);

        if (favoriteZutat != null && !favoriteZutat.isEmpty()) {
            speechText = String.format("Deine ausgewählte Zutat ist %s. Auf Wiedersehen.", favoriteZutat);
        } else {
            // Since the user's favorite color is not set render an error message.
            speechText = "Ich weiss nicht welches Deine ausgewählte Zutat ist. Nenne mir eine Zutat. Sage zum Beispiel: Die Zutat ist Kartoffel.";
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("ColorSession", speechText)
                .build();
    }
}