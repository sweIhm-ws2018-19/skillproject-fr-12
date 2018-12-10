package soupit.model;

import org.json.JSONObject;

public class ZutatMenge {
    private double menge;
    private String einheit;
    private String name;

    public ZutatMenge(double menge, String einheit, String name) {
        this.menge = menge;
        this.einheit = einheit;
        this.name = name;
    }

    public double getMenge() {
        return menge;
    }

    public String getEinheit() {
        return einheit;
    }

    public String getName() {
        return name;
    }
}
