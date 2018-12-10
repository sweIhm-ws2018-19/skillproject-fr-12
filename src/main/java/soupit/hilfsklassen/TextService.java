package soupit.hilfsklassen;

import soupit.model.ZutatMenge;

import java.util.ArrayList;

public class TextService {
    private TextService instance;

    private static final String BREAK_SECOND = "<break time=\"1s\"/>";
    private static final String SPACE = " ";

    private TextService() {
        //empty
    }

    public TextService getInstance() {
        if (instance == null)
            instance = new TextService();

        return instance;
    }

    public static String zutatenVonRezeptVorlesen(ArrayList<ZutatMenge> zutaten) {
        String response = "Für die Zubereitung benötigst du folgende Zutaten";

        for (ZutatMenge zutat : zutaten) {
            response += SPACE + BREAK_SECOND + SPACE;
            response += zutat.getMenge() + (!zutat.getEinheit().equalsIgnoreCase("none") ? (SPACE + zutat.getEinheit()) : "") + SPACE + zutat.getName();
        }

        return response + ".";
    }
}
