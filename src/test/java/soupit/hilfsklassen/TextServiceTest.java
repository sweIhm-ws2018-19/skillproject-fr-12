package soupit.hilfsklassen;

import org.junit.Test;
import soupit.handlers.TestHelper;
import soupit.model.Rezept;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TextServiceTest {
    @Test
    public void testGetInstance(){
        TextService first = TextService.getInstance();
        TextService second = TextService.getInstance();

        assertNotNull(first);
        assertEquals(first, second);
    }

    @Test
    public void testZutatenVonRezeptVorlesen(){

    }

    @Test
    public void testSchritteVonRezeptVorlesen(){
        Rezept rezept = TestHelper.generateRezept("test", 0);
        String have = TextService.schritteVonRezeptVorlesen(rezept);

        assertNotNull(have);
        assertTrue(have.contains("Alles klar. Lass uns mit der Zubereitung der"));
        assertTrue(have.contains("Ich hoffe die Suppe schmeckt und wünsche einen guten Appetit. Bis zum nächsten Mal."));
    }
}
