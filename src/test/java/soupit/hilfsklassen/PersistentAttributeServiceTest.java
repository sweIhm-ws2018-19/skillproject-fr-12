package soupit.hilfsklassen;

import org.junit.Test;
import soupit.handlers.TestHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PersistentAttributeServiceTest {
    @Test
    public void testGetInstance(){
        PersistentAttributeService first = PersistentAttributeService.getInstance();
        PersistentAttributeService second = PersistentAttributeService.getInstance();

        assertNotNull(first);
        assertEquals(first, second);
    }

    @Test
    public void testSetSinglePersistentAttribute(){
        PersistentAttributeService.setSinglePersistentAttribute(TestHelper.mockInputWithoutSlot(), "test", "test");
    }
}
