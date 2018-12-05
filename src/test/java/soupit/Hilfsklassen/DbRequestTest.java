package soupit.Hilfsklassen;

import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.*;


public class DbRequestTest {

    @Test()
    public void testGetRecipies() {
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
        have = (ArrayList<String>) DbRequest.getRecipies(ingr);

        //assert
        assertEquals(want, have);
    }

    @Test()
    public void testGetRecipiesNullPointer() {
        //arrange
        final ArrayList<String> have;
        final ArrayList<String> want = new ArrayList<>();

        //act
        have = (ArrayList<String>) DbRequest.getRecipies(null);

        //assert
        assertEquals(want, have);
    }

    @Test()
    public void testGetRecipiesNoMatch() {
        //arrange
        final ArrayList<String> have;
        final ArrayList<String> want = new ArrayList<>();
        final ArrayList<String> ingrds = new ArrayList<>();
        ingrds.add("skibrille");

        //act
        have = (ArrayList<String>) DbRequest.getRecipies(ingrds);

        //assert
        assertEquals(want, have);
    }

    @Test
    public void testGetInstance(){
        DbRequest first = DbRequest.getInstance();
        DbRequest second = DbRequest.getInstance();

        assertNotNull(first);
        assertEquals(first, second);
    }
}