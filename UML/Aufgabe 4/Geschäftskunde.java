package UML;

public class Geschäftskunde extends Kunde {
private String firmenname;

private final Adresse domizilAdresse;


    public Geschäftskunde(Konto[] konto, Adresse domizilAdresse) {
        super(konto);
        this.domizilAdresse = domizilAdresse;
    }
}
