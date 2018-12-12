package soupit.hilfsklassen;

import soupit.model.Rezept;
import soupit.model.Zutat;
import soupit.model.ZutatMenge;

import java.util.ArrayList;

public final class RezeptService {
    private static RezeptService instance;

    private RezeptService() {
        //empty
    }

    public static RezeptService getInstance() {
        if (instance == null)
            instance = new RezeptService();

        return instance;
    }

    public static ArrayList<ZutatMenge> getZutaten(int rezeptID, int portionen) {
        ArrayList<Zutat> alleZutaten = JsonService.zutatenEinlesen();
        Rezept rezept = getRezept(rezeptID);

        ArrayList<Integer> zutaten = rezept.getZutaten();
        ArrayList<Double> mengen = rezept.getMengen();

        ArrayList<ZutatMenge> zutatenMitMenge = new ArrayList<>();

        for (int i = 0; i < zutaten.size(); i++) {
            Zutat ztt = alleZutaten.get(zutaten.get(i));

            zutatenMitMenge.add(zutatMengeBauen(ztt, mengen.get(i) * portionen));
        }

        return zutatenMitMenge;
    }

    public static Rezept getRezept(int rezeptID) {
        ArrayList<Rezept> rezepte = JsonService.rezepteEinlesen();

        return rezepte.get(rezeptID);
    }

    private static ZutatMenge zutatMengeBauen(Zutat zutat, Double menge) {
        return new ZutatMenge(mengeFormatieren(zutat, menge), zutat.getEinheitSingular(), zutat.getSingular());
    }

    private static String mengeFormatieren(Zutat zutat, Double menge) {
        String response = "";
        int mengeInt = menge.intValue();

        if ((menge == mengeInt) && !Double.isInfinite(menge)) {
            if (mengeInt == 1) {
                switch (zutat.getEinheitGeschlecht()) {
                    case "w":
                        response = "eine";
                        break;
                    case "m":
                        response = "einen";
                        break;
                    default:
                        response = "ein";
                }
            } else
                response += mengeInt;
        } else {
            response += menge;
        }

        return response;
    }
}