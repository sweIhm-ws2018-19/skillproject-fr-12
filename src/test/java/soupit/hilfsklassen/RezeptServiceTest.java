package soupit.hilfsklassen;

import org.junit.Test;
import soupit.handlers.TestHelper;
import soupit.model.Rezept;
import soupit.model.Zutat;
import soupit.model.ZutatMenge;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RezeptServiceTest {
    @Test
    public void testGetInstance(){
        RezeptService first = RezeptService.getInstance();
        RezeptService second = RezeptService.getInstance();

        assertNotNull(first);
        assertEquals(first, second);
    }

    @Test
    public void testGetZutaten(){
        Rezept rezept = TestHelper.generateRezept("test", 0);

        ArrayList<ZutatMenge> have = RezeptService.getZutaten(rezept, 2);

        assertTrue(have.size() == 3);
    }

    @Test
    public void testGetRezept(){
        Rezept have = RezeptService.getRezept(0);

        assertNotNull(have);
        assertNotNull(have.getName());
        assertNotNull(have.getZutaten());
    }

    @Test
    public void testZutatMengeBauen(){
        ZutatMenge have = null;

        Zutat zutat =  TestHelper.generateDummyZutat();
        Double menge = 2.0;

        try {
            Method method = RezeptService.class.getDeclaredMethod("zutatMengeBauen", Zutat.class, Double.class);
            method.setAccessible(true);
            have = (ZutatMenge) method.invoke(null, zutat, menge);
        } catch (Exception ex) {
        }

        assertNotNull(have);
    }

    @Test
    public void testMengeFormatieren(){
        String have = null;

        Zutat zutat =  TestHelper.generateDummyZutat();
        Double menge = 2.0;

        try {
            Method method = RezeptService.class.getDeclaredMethod("mengeFormatieren", Zutat.class, Double.class);
            method.setAccessible(true);
            have = (String) method.invoke(null, zutat, menge);
        } catch (Exception ex) {
        }

        assertNotNull(have);
    }

    @Test
    public void testMengeFormatierenNegativ(){
        String want = "none";
        String have = null;

        Zutat zutat =  TestHelper.generateDummyZutat();
        Double menge = -2.0;

        try {
            Method method = RezeptService.class.getDeclaredMethod("mengeFormatieren", Zutat.class, Double.class);
            method.setAccessible(true);
            have = (String) method.invoke(null, zutat, menge);
        } catch (Exception ex) {
        }

        assertNotNull(have);
        assertEquals(want, have);
    }

    @Test
    public void testMengeFormatierenNoInteger(){
        String want = "2.5";
        String have = null;

        Zutat zutat =  TestHelper.generateDummyZutat();
        Double menge = 2.5;

        try {
            Method method = RezeptService.class.getDeclaredMethod("mengeFormatieren", Zutat.class, Double.class);
            method.setAccessible(true);
            have = (String) method.invoke(null, zutat, menge);
        } catch (Exception ex) {
        }

        assertNotNull(have);
        assertEquals(want, have);
    }

    @Test
    public void testMengeFormatierenOne(){
        String want = "ein";
        String have = null;

        Zutat zutat =  TestHelper.generateDummyZutat();
        Double menge = 1.0;

        try {
            Method method = RezeptService.class.getDeclaredMethod("mengeFormatieren", Zutat.class, Double.class);
            method.setAccessible(true);
            have = (String) method.invoke(null, zutat, menge);
        } catch (Exception ex) {
        }

        assertNotNull(have);
        assertEquals(want, have);
    }

    @Test
    public void testMengeFormatierenOneFemale(){
        String want = "eine";
        String have = null;

        Zutat zutat =  TestHelper.generateDummyZutat("w");
        Double menge = 1.0;

        try {
            Method method = RezeptService.class.getDeclaredMethod("mengeFormatieren", Zutat.class, Double.class);
            method.setAccessible(true);
            have = (String) method.invoke(null, zutat, menge);
        } catch (Exception ex) {
        }

        assertNotNull(have);
        assertEquals(want, have);
    }

    @Test
    public void testMengeFormatierenOneMale(){
        String want = "einen";
        String have = null;

        Zutat zutat =  TestHelper.generateDummyZutat("m");
        Double menge = 1.0;

        try {
            Method method = RezeptService.class.getDeclaredMethod("mengeFormatieren", Zutat.class, Double.class);
            method.setAccessible(true);
            have = (String) method.invoke(null, zutat, menge);
        } catch (Exception ex) {
        }

        assertNotNull(have);
        assertEquals(want, have);
    }
}
