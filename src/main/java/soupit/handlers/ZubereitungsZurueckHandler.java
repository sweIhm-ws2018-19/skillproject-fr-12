package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import org.json.JSONObject;
import soupit.hilfsklassen.*;
import soupit.model.RezeptCount;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZubereitungsZurueckHandler implements RequestHandler {



    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZubereitungsZurueckIntent"));
    }

    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        //get slots from current intent s
        Map<String, Slot> slots = intent.getSlots();

        String speechText = ZubereitungsSteuerungsLogik.getNext(input, slots, -1);


        SessionAttributeService.updateLastIntent(input, "ZubereitungsZurueckHandler");
        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
