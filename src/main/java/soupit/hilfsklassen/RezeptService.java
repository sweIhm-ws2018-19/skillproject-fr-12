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
        }
        //1/8
        else if (formattedThreeDouble <= 0.125) {
            switch (zutat.getEinheitGeschlecht()) {
                case "w":
                    response = "eine Achtel";
                    break;
                case "m":
                    response = "ein Achtel";
                    break;
                default:
                    response = "ein Achtel";
            }
        }
        //1/6
        else if (formattedThreeDouble <= 0.167) {
            switch (zutat.getEinheitGeschlecht()) {
                case "w":
                    response = "eine Sechstel";
                    break;
                case "m":
                    response = "ein Sechstel";
                    break;
                default:
                    response = "ein Sechstel";
            }
        } else if (formattedThreeDouble <= 0.250) {
            switch (zutat.getEinheitGeschlecht()) {
                case "w":
                    response = "eine Viertel";
                    break;
                case "m":
                    response = "ein Viertel";
                    break;
                default:
                    response = "ein Viertel";
            }
        } else if (formattedThreeDouble <= 0.334) {
            switch (zutat.getEinheitGeschlecht()) {
                case "w":
                    response = "eine Drittel";
                    break;
                case "m":
                    response = "ein Drittel";
                    break;
                default:
                    response = "ein Drittel";
            }
        } else if (formattedThreeDouble <= 0.500) {
//            String[] strs = { "zwiebel","knoblauchzehe","karotte"};
//            List<String> list = new ArrayList<>(Arrays.asList(strs));
//
//            if (list.contains(zutat.getSingular())){
//                response = "eine halbe";
//            }else {
//                response = "ein halb";
//            }
            switch (zutat.getEinheitGeschlecht()) {
                case "w":
                    response = "eine halbe";
                    break;
                case "m":
                    response = "einen halben";
                    break;
                default:
                    response = "ein halbes";
            }
        } else if (formattedThreeDouble < 1.000){
            //
            switch (zutat.getEinheitGeschlecht()) {
                case "w":
                    //Bsp. die Tomate
                    response = "eine drei Viertelte";
                    break;
                case "m":
                    //Bsp. der Salat
                    response = "ein drei Viertelter";
                    break;
                default:
                    //Bsp. das Ei
                    response = "ein drei Vierteltes";
            }
        } else if (formattedThreeDouble == 1.000) {
            switch (zutat.getEinheitGeschlecht()) {
                case "w":
                    response = "eine";
                    break;
                case "m":
                    response = "ein";
                    break;
                default:
                    response = "ein";
            }
        }  else {
            //groesser 1
            response = formattedZeroString;
        }
        return response;
    }
}