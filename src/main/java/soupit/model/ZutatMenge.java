package soupit.model;

public class ZutatMenge {
    private int menge;
    private String einheit;
    private String name;

    public ZutatMenge(int menge, String einheit, String name) {
        this.menge = menge;
        this.einheit = einheit;
        this.name = name;
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
