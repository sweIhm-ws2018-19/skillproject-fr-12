package soupit.hilfsklassen;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RezeptServiceTest {
    @Test
    public void testGetInstance(){
        RezeptService first = RezeptService.getInstance();
        RezeptService second = RezeptService.getInstance();

        assertNotNull(first);
        assertEquals(first, second);
    }
}
