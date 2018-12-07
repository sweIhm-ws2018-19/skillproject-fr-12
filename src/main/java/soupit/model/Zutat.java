package soupit.model;

public class Zutat {
    private int zutatID;
    private String singular;
    private String plural;
    private String einheitSingular;
    private String einheitPlural;
    private String einheitGeschlecht;

    public Zutat(int zutatID, String singular, String plural, String einheitSingular, String einheitPlural, String einheitGeschlecht) {
        this.zutatID = zutatID;
        this.singular = singular;
        this.plural = plural;
        this.einheitSingular = einheitSingular;
        this.einheitPlural = einheitPlural;
        this.einheitGeschlecht = einheitGeschlecht;
    }

    public int getZutatID() {
        return zutatID;
    }

    public String getSingular() {
        return singular;
    }

    public String getPlural() {
        return plural;
    }

    public String getEinheitSingular() {
        return einheitSingular;
    }

    public String getEinheitPlural() {
        return einheitPlural;
    }

    public String getEinheitGeschlecht() {
        return einheitGeschlecht;
    }
}
