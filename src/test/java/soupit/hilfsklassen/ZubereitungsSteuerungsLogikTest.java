package soupit.hilfsklassen;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Slot;
import org.junit.Test;
import soupit.handlers.TestHelper;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ZubereitungsSteuerungsLogikTest {
    @Test
    public void testGetInstance(){
        ZubereitungsSteuerungsLogik first = ZubereitungsSteuerungsLogik.getInstance();
        ZubereitungsSteuerungsLogik second = ZubereitungsSteuerungsLogik.getInstance();

        assertNotNull(first);
        assertEquals(first, second);
    }

    @Test
    public void testGetNext(){
        HandlerInput input = TestHelper.mockInputWithoutSlot();
        Map<String, Slot> slots = new HashMap<>();
        int direction = 0;

        String have = ZubereitungsSteuerungsLogik.getNext(input, slots, direction);

        assertNotNull(have);
    }
}
