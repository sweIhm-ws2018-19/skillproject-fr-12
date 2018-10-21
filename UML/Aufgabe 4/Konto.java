package UML;

public class Konto {

    private String bezeichnung;
    private Kunde[] zeichnungsberechtigter;

    public Konto(Kunde[] zeichnungsberechtigter){
        if(zeichnungsberechtigter == null || zeichnungsberechtigter[0] == null)
            throw new IllegalArgumentException("Konto braucht mindestens einen Kunden.");

        this.zeichnungsberechtigter = zeichnungsberechtigter;
     }

    public GeldBetrag saldo(){
        return null;
    }

    public void einzahlen(GeldBetrag eingBetrag){

    }
    
}
