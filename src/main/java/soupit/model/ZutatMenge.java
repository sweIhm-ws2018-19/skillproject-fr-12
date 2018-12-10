package soupit.model;

import org.json.JSONObject;

public class ZutatMenge {
    private int menge;
    private String einheit;
    private String name;

    public ZutatMenge(int menge, String einheit, String name) {
        this.menge = menge;
        this.einheit = einheit;
        this.name = name;
    }

    public ZutatMenge(JSONObject json) {
        this((int) json.get("menge"), (String) json.get("einheit"), (String) json.get("name"));
    }

    public int getMenge() {
        return menge;
    }

    public String getEinheit() {
        return einheit;
    }

    public String getName() {
        return name;
    }
}
