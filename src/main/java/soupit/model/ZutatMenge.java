package soupit.model;

import org.json.JSONObject;

public class ZutatMenge {
    private String menge;
    private String einheit;
    private String name;

    public ZutatMenge(String menge, String einheit, String name) {
        this.menge = menge;
        this.einheit = einheit;
        this.name = name;
    }

    public String getMenge() {
        return menge;
    }

    public String getEinheit() {
        return einheit;
    }

    public String getName() {
        return name;
    }
}
