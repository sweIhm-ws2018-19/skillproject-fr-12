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

    public static ArrayList<ZutatMenge> getZutaten(int rezeptID){
        ArrayList<Zutat> alleZutaten = JsonService.zutatenEinlesen();
        Rezept rezept = getRezept(rezeptID);

        ArrayList<Integer> zutaten = rezept.getZutaten();
        ArrayList<Double> mengen = rezept.getMengen();

        ArrayList<ZutatMenge> zutatenMitMenge = new ArrayList<>();

        for(int i = 0; i < zutaten.size(); i++){
            Zutat ztt = alleZutaten.get(zutaten.get(i));

            zutatenMitMenge.add(zutatMengeBauen(ztt, mengen.get(i)));
        }

        return zutatenMitMenge;
    }

    public static Rezept getRezept(int rezeptID){
        ArrayList<Rezept> rezepte = JsonService.rezepteEinlesen();

        return rezepte.get(rezeptID);
    }

    private static ZutatMenge zutatMengeBauen(Zutat zutat, Double menge){
        return new ZutatMenge(menge, zutat.getEinheitSingular(), zutat.getSingular());
    }
}