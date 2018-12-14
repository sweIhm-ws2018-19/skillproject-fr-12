package soupit.hilfsklassen;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    }
}
