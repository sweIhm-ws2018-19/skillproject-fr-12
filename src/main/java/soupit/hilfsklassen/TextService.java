package soupit.hilfsklassen;

import soupit.model.Rezept;
import soupit.model.ZutatMenge;

import java.util.ArrayList;

public class TextService {
    private static TextService instance;

    private static final String BREAK_TWO_SECONDS = "<break time=\"2s\"/>";
    private static final String BREAK_SECOND = "<break time=\"1s\"/>";
    private static final String BREAK_HALF_SECOND = "<break time=\"500ms\"/>";
    private static final String SPACE = " ";
    private static final String NONE = "none";

    private TextService() {
        //empty
    }

    public static TextService getInstance() {
        if (instance == null)
            instance = new TextService();

        return instance;
    }

    public static String zutatenVonRezeptVorlesen(ArrayList<ZutatMenge> zutaten, int portionen) {
        StringBuilder response = new StringBuilder();
        response.append("Für die Zubereitung für " + portionen + " Personen benötigst du mehrere Zutaten. Lege dir folgendes bereit: ");

        for (ZutatMenge zutat : zutaten) {
            response.append(SPACE + BREAK_HALF_SECOND + SPACE);
            response.append((!zutat.getMenge().equalsIgnoreCase(NONE) ? (zutat.getMenge()) : "")
                    + (!zutat.getEinheit().equalsIgnoreCase(NONE) ? (SPACE + zutat.getEinheit()) : "")
                    + SPACE + zutat.getName());
        }

        return response.toString() + ". " + BREAK_SECOND + " Hast du alle Zutaten vorrätig?";
    }

    public static String schritteVonRezeptVorlesen(Rezept rezept) {
        StringBuilder response = new StringBuilder();
        response.append("Alles klar. Lass uns mit der Zubereitung der " + rezept.getName() + " beginnen.");

        ArrayList<String> schritte = rezept.getSchritte();

        for (String schritt : schritte) {
            response.append(SPACE + BREAK_TWO_SECONDS + SPACE + schritt);
        }

        response.append(SPACE + "Ich hoffe die Suppe schmeckt und wünsche einen guten Appetit. Bis zum nächsten Mal.");

        return response.toString();
    }
}
