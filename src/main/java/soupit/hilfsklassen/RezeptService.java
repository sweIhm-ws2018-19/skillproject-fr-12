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
        // TODO: 06.01.2019 formatieren
        String response = "";
        double mengeRoundedThree = round(menge, 3);
        double mengeRoundedTwo = round(menge, 2);
        double mengeRoundedOne = round(menge, 1);

        if (mengeRoundedThree < 0.000) {
            //kleiner 0
            response = "none";
        } else if (mengeRoundedThree <= 0.125) {

            response = "ein Achtel";

        } else if (mengeRoundedThree <= 0.167) {

            response = "ein Sechstel";

        } else if (mengeRoundedThree <= 0.250) {

            response = "ein Viertel";

        }else if (mengeRoundedThree <= 0.334) {

            response = "ein Drittel";

        }else if (mengeRoundedThree <= 0.500) {

            response = "ein halb";

        }
        else if (mengeRoundedThree < 1.000) {

            response = "ein halb";

        }else if (mengeRoundedThree == 1.000) {
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
            //groesser 1
            round(menge, 0);
        response += menge;


        return response;
    }
}