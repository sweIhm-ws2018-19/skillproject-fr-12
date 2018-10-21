package UML;

public class Geschäftskunde extends Kunde {

	private String firmenname;
	private Adresse domizilAdresse;

	public Geschäftskunde(Konto[] konto, Adresse domizilAdresse) {
         super(konto);
         this.domizilAdresse = domizilAdresse;
     }

}
