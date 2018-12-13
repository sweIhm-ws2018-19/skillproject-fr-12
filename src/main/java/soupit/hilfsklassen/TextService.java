package soupit.hilfsklassen;

import soupit.model.Rezept;
import soupit.model.ZutatMenge;

import java.util.ArrayList;

public class TextService {
    private TextService instance;

    private static final String BREAK_SECOND = "<break time=\"1s\"/>";
    private static final String BREAK_HALF_SECOND = "<break time=\"500ms\"/>";
    private static final String SPACE = " ";

    private TextService() {
        //empty
    }

    public TextService getInstance() {
        if (instance == null)
            instance = new TextService();

        return instance;
    }

    public static String zutatenVonRezeptVorlesen(ArrayList<ZutatMenge> zutaten, int portionen) {
        String response = "Für die Zubereitung für " + portionen + " Personen benötigst du mehrere Zutaten. Lege dir folgendes bereit: ";

        for (ZutatMenge zutat : zutaten) {
            response += SPACE + BREAK_HALF_SECOND + SPACE;
            response += zutat.getMenge()
                    + (!zutat.getEinheit().equalsIgnoreCase("none") ? (SPACE + zutat.getEinheit()) : "")
                    + SPACE + zutat.getName();
        }

        return response + ". " + BREAK_SECOND + " Hast du alle Zutaten vorrätig?";
    }

    public static String schritteVonRezeptVorlesen(Rezept rezept){
        String response = "Alles klar. Lass uns mit der Zubereitung der Kartoffelcremesuppe beginnen.";

        ArrayList<String> schritte = rezept.getSchritte();

        for(String schritt: schritte){
            response += SPACE + BREAK_SECOND + SPACE + schritt;
        }

        return response;
    }
}
