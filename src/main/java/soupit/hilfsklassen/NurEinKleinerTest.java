package soupit.hilfsklassen;

public class NurEinKleinerTest {

    public static void main(String... args){
        System.out.println(TextService.zutatenVonRezeptVorlesen(RezeptService.getZutaten(0)));
    }
}
