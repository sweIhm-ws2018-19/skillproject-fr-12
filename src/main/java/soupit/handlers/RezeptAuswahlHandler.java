package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import soupit.hilfsklassen.SlotFilter;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class RezeptAuswahlHandler implements RequestHandler {

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

        // Es wurden keine Zutaten genannt -> es können keine Rezepte vorgeschlagen werden -> speechText passt so
        String speechText;
        String[] rezepte = getRezepte();
        String[] suppenWahl = SlotFilter.getSuppenWahl(slots);

        // if-bedingung falls rezepte leer hinzufügen

        if (suppenWahl[0].equals("Zahl")) {
            speechText = String.format("Alles klar. Wir werden eine %s kochen.", checkSuppeZahl(suppenWahl[1], rezepte));
        } else if (suppenWahl[0].equals("Suppe")) {
            speechText = String.format("Alles klar. Wir werden eine %s kochen.", checkSuppeText(suppenWahl[1], rezepte));
        } else {
            // Der Nutzer hat keine valide Suppe ausgewählt
            speechText = "Ich kann dir kein Rezept vorschlagen. Bitte wähle zuerst Zutaten aus.";
        }

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
    public String[] getRezepte() {
        String[] rezepte;

        //hier mit .getSessionAttributes() die gespeicherten Rezepte holen

        //übergangslosung
        rezepte = new String[]{"kartoffelsuppe", "karottensuppe", "tomatensuppe"};

        return rezepte;
    }

    /**
     * @param suppe:   index der Suppe (als String)
     * @param rezepte: alle vorgeschlagenen Rezepte
     * @return falls index nicht passt: ""
     * falls index passt: string suppe
     */
    public String checkSuppeZahl(String suppe, String[] rezepte) {
        String dieSuppe = "";

        int index = Integer.parseInt(suppe);

        if (index <= rezepte.length) {
            dieSuppe = rezepte[index - 1];
        }

        return dieSuppe;
    }

    /**
     * @param suppe:   von nutzer genannte Suppe
     * @param rezepte: alle vorgeschlagenen Rezepte
     * @return falls suppe nicht in rezepten: ""
     * falls suppe in rezepten: string suppe
     */
    public String checkSuppeText(String suppe, String[] rezepte) {
        String dieSuppe = "";

        for (String rezept : rezepte) {
            if (suppe.equals(rezept))
                dieSuppe = suppe;
        }

        return dieSuppe;
    }

}
