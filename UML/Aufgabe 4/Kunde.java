package UML;

public abstract class Kunde {

    private final Konto[] konto;
    
    public Kunde(Konto[] konto) {
         if (konto == null || konto[0] == null)
             throw new IllegalArgumentException("Kunde braucht mindestens ein Konto");

         this.konto = konto;
     }

}
