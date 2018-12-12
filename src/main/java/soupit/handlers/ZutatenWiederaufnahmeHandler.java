package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.amazon.ask.response.ResponseBuilder;
import soupit.hilfsklassen.SlotFilter;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZutatenWiederaufnahmeHandler implements RequestHandler {
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZutatenAuswahlIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        //get slots from current intend
        Map<String, Slot> slots = intent.getSlots();

        final ArrayList<String> zutatStringList = (ArrayList<String>) SlotFilter.getIngredient(slots);

        final String speechText;
        final String repromptText;
        boolean isAskResponse = false;

        speechText = "";
        repromptText = "";


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
