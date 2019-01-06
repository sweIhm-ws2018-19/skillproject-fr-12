package soupit.hilfsklassen;

import soupit.model.Rezept;
import soupit.model.Zutat;
import soupit.model.ZutatMenge;

import java.math.RoundingMode;
import java.util.ArrayList;

import static jdk.nashorn.internal.objects.NativeMath.round;

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

    public static ArrayList<ZutatMenge> getZutaten(Rezept rezept, int portionen) {
        ArrayList<Zutat> alleZutaten = JsonService.zutatenEinlesen();

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
        double mengeRounded = round(menge, 1);

        if ((menge == mengeInt)) {
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
            } else if (mengeInt < 0) {
                response = "none";
            } else
                //gerade Zahl groesser 1
                response += mengeInt;
            // TODO: 21.12.2018 Mengenangabe formatieren
        } else {
            //wenn nicht gerade && nicht 1, 0 oder kleiner 0

            response += menge;
        }

        return response;
    }
}