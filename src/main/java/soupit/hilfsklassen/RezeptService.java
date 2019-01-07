package soupit.hilfsklassen;

import soupit.model.Rezept;
import soupit.model.Zutat;
import soupit.model.ZutatMenge;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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

        System.out.println("MengeOrginal: " + menge);

        //Formatter Three
        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
        //formatter.setRoundingMode(RoundingMode.HALF_UP);
        formatter.applyPattern("0.000");
        String formattedThreeString = formatter.format(menge);
        Double formattedThreeDouble = Double.parseDouble(formattedThreeString);
        //Test
        System.out.println("Three String: " + formattedThreeString + " Double: " + formattedThreeDouble);


        //Formatter Zero
        //Double mengeDoubleFormatedThree = Double.parseDouble(dfThree.format(menge));
        DecimalFormat formatterZero = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
        formatterZero.applyPattern("#");
        //dfZero.setRoundingMode(RoundingMode.HALF_UP);
        String formattedZeroString = formatterZero.format(menge);
        Double formattedZeroDouble = Double.parseDouble(formattedZeroString);
        //Test
        System.out.println("Zero String: " + formattedZeroString + " Double: " + formattedZeroDouble);

        System.out.println();

        if (formattedThreeDouble < 0.000) {
            //kleiner 0
            response = "none";
        } else if (formattedThreeDouble <= 0.125) {

            response = "ein Achtel";

        } else if (formattedThreeDouble <= 0.167) {

            response = "ein Sechstel";

        } else if (formattedThreeDouble <= 0.250) {

            response = "ein Viertel";

        } else if (formattedThreeDouble <= 0.334) {

            response = "ein Drittel";

        } else if (formattedThreeDouble <= 0.500) {
            String[] strs = { "zwiebel","knoblauchzehe","karotte" };
            List<String> list = new ArrayList<>(Arrays.asList(strs));

            if (list.contains(zutat.getSingular())){
                response = "eine halbe";
            }else {
                response = "ein halb";
            }

        } else if (formattedThreeDouble < 1.000) {

            response = "ein halb";

        } else if (formattedThreeDouble == 1.000) {
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
            //response = "" + "genau eins";
        } else {
            //groesser 1

            response = formattedZeroString;


        }

        return response;
    }
}