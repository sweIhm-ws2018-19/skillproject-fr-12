package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import jdk.internal.org.objectweb.asm.Handle;
import soupit.hilfsklassen.SessionAttributeService;
import soupit.hilfsklassen.SlotFilter;
import soupit.model.Rezept;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class RezeptAuswahlHandler implements RequestHandler {
    private final static String REZEPT_FOUND = "REZEPT_FOUND";
    private final static String REZEPT = "REZEPT";
    private final static String REZEPT_INDEX = "REZEPT_INDEX";
    private final static String WIE_VIELE_PORTIONEN = "Wie viele Portionen möchtest du kochen?";

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("RezeptAuswahlIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        String speechText;
        Rezept[] rezepte = getRezepte(input);
        String[] suppenWahl = SlotFilter.getSuppenWahl(slots);

        // if-Bedingung falls rezepte leer hinzufügen

        if (suppenWahl[0].equals("Zahl")) {
            speechText = WIE_VIELE_PORTIONEN;
            //TODO index der ausgewählten Suppe muss in sessionAttributen gespeichert werden
            int suppenIndex = checkSuppeZahl(suppenWahl[1], rezepte);
            if (suppenIndex >= 0 && suppenIndex <= rezepte.length) {
                SessionAttributeService.setSingleSessionAttribute(input, REZEPT_INDEX, suppenIndex);
                SessionAttributeService.setSingleSessionAttribute(input, REZEPT, rezepte[suppenIndex]);
            } else {
                speechText = "Ich kann dir kein Rezept vorschlagen. Bitte wähle eines der genannten Rezepte aus.";
            }
        } else if (suppenWahl[0].equals("Suppe")) {
            speechText = WIE_VIELE_PORTIONEN;
            //TODO index der ausgewählten Suppe muss in sessionAttributen gespeichert werden
            int suppenIndex = checkSuppeText(suppenWahl[1], rezepte);
            if (suppenIndex >= 0 && suppenIndex <= rezepte.length) {
                SessionAttributeService.setSingleSessionAttribute(input, REZEPT_INDEX, suppenIndex);
                SessionAttributeService.setSingleSessionAttribute(input, REZEPT, rezepte[suppenIndex]);
            } else {
                speechText = "Ich kann dir kein Rezept vorschlagen. Bitte wähle eines der genannten Rezepte aus.";
            }
        } else {
            // Der Nutzer hat keine valide Suppe ausgewählt
            speechText = "Ich kann dir kein Rezept vorschlagen. Bitte wähle zuerst Zutaten aus.";
        }

        SessionAttributeService.updateLastIntent(input, "RezeptAuswahlIntent");

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withShouldEndSession(false)
                .withSimpleCard("SoupitSession", speechText)
                .build();
    }

    /**
     * param input: HandlerInput noch hinzufügen
     *
     * @return falls Rezepete in den Session Attributen gespichert wurden: die ersten drei Rezepte
     */
    public Rezept[] getRezepte(HandlerInput input) {
        Rezept[] rezepte;

        //hier mit .getSessionAttributes() die gespeicherten Rezepte holen
        rezepte = (Rezept[]) SessionAttributeService.getSingleSessionAttribute(input, REZEPT_FOUND);

        return rezepte;
    }

    /**
     * @param suppe:   index der Suppe (als String)
     * @param rezepte: alle vorgeschlagenen Rezepte
     * @return falls index nicht passt: ""
     * falls index passt: string suppe
     */
    public int checkSuppeZahl(String suppe, Rezept[] rezepte) {
        int suppenIndex = -1;

        int index = Integer.parseInt(suppe);

        if (index <= rezepte.length) {
            suppenIndex = index;
        }

        return suppenIndex;
    }

    /**
     * @param suppe:   von nutzer genannte Suppe
     * @param rezepte: alle vorgeschlagenen Rezepte
     * @return falls suppe nicht in rezepten: ""
     * falls suppe in rezepten: string suppe
     */
    public int checkSuppeText(String suppe, Rezept[] rezepte) {
        int suppenIndex = -1;

        for (int i = 0; i < rezepte.length; i++){
            if (suppe.equals(rezepte[i].getName()))
                suppenIndex = i;
        }

        return suppenIndex;
    }

}
