package test.java.soupit.Hilfsklassen;

import main.java.soupit.Hilfsklassen.DbRequest;
import org.junit.jupiter.api.Test;
import org.junit.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class DbRequestTest {


    @Test()
    void getRecipies() {
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
    void getRecipiesNullPointer() {
        //arrange

        final ArrayList<String> have;
        final ArrayList<String> want = new ArrayList<>();


        //act
        have =  DbRequest.getRecipies(null);

        //assert

        assertEquals(want, have);
    }

    @Test()
    void getRecipiesNoMatch() {
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
    void shouldFailTest() {
        //assert
        assertTrue(false);
    }


}