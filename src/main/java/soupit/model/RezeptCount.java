package soupit.model;

public class RezeptCount {
    private Rezept rezept;
    private int     count;

    public RezeptCount(Rezept rezept, int count) {
        this.rezept = rezept;
        this.count = count;
    }

    public Rezept getRezept() {
        return rezept;
    }

    public int getCount() {
        return count;
    }
}
