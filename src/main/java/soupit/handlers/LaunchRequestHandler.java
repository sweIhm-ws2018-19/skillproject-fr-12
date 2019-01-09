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
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import org.json.JSONObject;
import soupit.hilfsklassen.DbRequest;
import soupit.hilfsklassen.JsonService;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.model.Rezept;
import soupit.model.RezeptCount;

import java.util.Optional;
import java.util.Random;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {
    private final static String CURRENT_REZEPT = "CURRENT_REZEPT";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String rezeptCountString = DbRequest.getRezeptFromDynDB(input);

        final   String speechText;
        final  String repromptText;
        if (rezeptCountString == null || rezeptCountString.equals("none")) {

           speechText =  "<say-as interpret-as=\"interjection\">Willkommen</say-as> bei <lang xml:lang=\"en-US\">Soup It</lang> ! Als dein persönlicher Assistent begleite ich dich bei der Suppenzubereitung. " +
                    randomResponse();
            repromptText = randomResponse();

        }
        else {
            RezeptCount rezeptCount = JsonService.rezeptCountParsen(new JSONObject(rezeptCountString));
            SessionAttributeService.setSingleSessionAttribute(input, CURRENT_REZEPT, rezeptCountString);
            speechText = "Du hast die " + rezeptCount.getRezept().getName() + " nochnicht abgeschlossen. Sage weiter für den nächsten Schritt oder Rezept abschließen.";
            repromptText = null;
        }


        SessionAttributeService.updateLastIntent(input, "LaunchRequest");

        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withReprompt(repromptText)
                .build();
    }

    /**
     *
     * @return String response
     */
    private String randomResponse(){
        String[] phrases = {"Welche Zutaten möchtest du verwenden?", "Nenne mir Zutaten, die du zum Kochen verwenden möchtest.",
                "Mit welchen Zutaten möchtest du eine Suppe kochen?"};
        String response;

        int rnd = new Random().nextInt(phrases.length);
        response = phrases[rnd];

        return response;
    }
}
