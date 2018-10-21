package UML;

public class Konto {
    private String bezeichnung;
    private final Kunde[] zeichnungsberechtigter;

    public Konto(Kunde[] zeichnungsberechtigter) {
        if(zeichnungsberechtigter[0] == null)
            throw new IllegalArgumentException("Konto braucht mind einen Kunden");

        this.zeichnungsberechtigter = zeichnungsberechtigter;
    }


    public double saldo(){
        return 0.0;
    }

    public void einzahlen(double eingBetrag){

    }

}
