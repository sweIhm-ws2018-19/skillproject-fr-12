package test.java.soupit.Hilfsklassen;

import main.java.soupit.Hilfsklassen.DbRequest;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;


 public class DbRequestTest {


    @Test()
    public void getRecipies() {
        //arrange

        final ArrayList<String> have;
        final ArrayList<String> want = new ArrayList<>();
        want.add("kartoffelsuppe");
        want.add("karottensuppe");
        want.add("tomatensuppe");

        final ArrayList<String> ingr = new ArrayList<>();
        ingr.add("kartoffel");
        ingr.add("tomate");
        ingr.add("karotte");

        //act
        have =  DbRequest.getRecipies(ingr);

        //assert

        assertEquals(want, have);
    }

    @Test()
    public void getRecipiesNullPointer() {
        //arrange

        final ArrayList<String> have;
        final ArrayList<String> want = new ArrayList<>();


        //act
        have =  DbRequest.getRecipies(null);

        //assert

        assertEquals(want, have);
    }

    @Test()
    public void getRecipiesNoMatch() {
        //arrange

        final ArrayList<String> have;
        final ArrayList<String> want = new ArrayList<>();
        final ArrayList<String> ingrds = new ArrayList<>();
        ingrds.add("skibrille");


        //act
        have =  DbRequest.getRecipies(ingrds);

        //assert

        assertEquals(want, have);
    }

    //zum überprüfen, ob travis die tests ausführt
    @Test()
    public void shouldFailTest() {
        //assert
        assertTrue(true);
    }


}