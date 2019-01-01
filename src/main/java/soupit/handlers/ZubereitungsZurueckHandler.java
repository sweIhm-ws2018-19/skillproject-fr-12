package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import soupit.hilfsklassen.SessionAttributeService;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ZubereitungsZurueckHandler implements RequestHandler {
    private final static String PORTIONEN_YES_INTENT = "PortionenYesIntent";
    private final static String REZEPT_FOUND = "REZEPT_FOUND";
    private final static String REZEPT_INDEX = "REZEPT_INDEX";
    private final static String CURRENT_REZEPT = "CURRENT_REZEPT";


    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ZubereitungsZurueckIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
//        Request request = input.getRequestEnvelope().getRequest();
//        IntentRequest intentRequest = (IntentRequest) request;
//        Intent intent = intentRequest.getIntent();
//        //get slots from current intent s
//        Map<String, Slot> slots = intent.getSlots();
//
//        String speechText;
//        RezeptCount rezept = (RezeptCount) SessionAttributeService.getSingleSessionAttribute(input, CURRENT_REZEPT);
//
//        String slotValue = SlotFilter.getSingleSlotCheckedValue(slots, "Anzahl");
//
//
//        final int stepsToGo;
//
//        if (slotValue.equals("none"))
//            stepsToGo = -1;
//        else
//            stepsToGo = Integer.parseInt(slotValue)*-1;
//
//        if (rezept == null) {
//            rezept = DbRequest.getRezeptFromDynDB();
//        }
//
//        if (rezept == null) {
//            speechText = "Das weiß ich leider nicht!";
//        } else {
//            rezept.setCount(rezept.getCount() + stepsToGo);
//            ArrayList<String> steps = rezept.getRezept().getSchritte();
//            if (rezept.getCount() < 0 || rezept.getCount() >= steps.size()) {
//                speechText = "Die Zubereitung ist bereits Abgeschlossen.";
//            } else {
//                if (steps.size() == rezept.getCount() - 1) {
//                    speechText = steps.get(rezept.getCount()) + "Ich hoffe die Suppe schmeckt und wünsche einen guten Appetit. Bis zum nächsten Mal.";
//                } else {
//                    speechText = steps.get(rezept.getCount());
//                }
//
//
//                //DynDb update missing
//                SessionAttributeService.setSingleSessionAttribute(input, CURRENT_REZEPT, rezept);
//
//            }
//
//        }
String speechText = "fuck this shit im out";

        SessionAttributeService.updateLastIntent(input, "ZubereitungsZurueckHandler");
        return input.getResponseBuilder()
                .withSimpleCard("SoupitSession", speechText)
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .build();
    }
}
