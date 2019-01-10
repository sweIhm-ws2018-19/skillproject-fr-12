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
import com.amazon.ask.model.Response;
import soupit.hilfsklassen.PersistentAttributeService;
import soupit.hilfsklassen.SessionAttributeService;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZubereitungAbschliessenHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZubereitungAbschliessenIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Bis zum nächsten mal!";
        SessionAttributeService.setSingleSessionAttribute(input, "CURRENT_REZEPT", null);
        PersistentAttributeService.setSinglePersistentAttribute(input, "CURRENT_REZEPT", null);



        SessionAttributeService.updateLastIntent(input, "ZubereitungAbschliessenIntent ");

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard("SoupitSession", speechText)
                .withShouldEndSession(false)
                .build();
    }
}
