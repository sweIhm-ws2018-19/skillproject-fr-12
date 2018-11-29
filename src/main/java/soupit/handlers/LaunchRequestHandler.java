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
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import java.util.Optional;
import java.util.Random;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Willkommen bei Soup-IT! Als dein persönlicher Assistent begleite ich dich bei der Suppenzubereitung. " +
                randomResponse();
        String repromptText = randomResponse();
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
