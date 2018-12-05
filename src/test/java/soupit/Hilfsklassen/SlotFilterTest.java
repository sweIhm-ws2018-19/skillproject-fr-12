package soupit.Hilfsklassen;

import com.amazon.ask.model.Slot;
import org.junit.Test;
import soupit.handlers.TestHelper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SlotFilterTest {

    @Test
    public void testGetIngredientEmptyMap() {
        //arrange
        ArrayList<String> have = new ArrayList<>();
        HashMap<String, Slot> slotMap = new HashMap<>();

        //act
        ArrayList<String> want = SlotFilter.getIngredient(slotMap);

        //assert
        assertEquals(want, have);
    }

    @Test
    public void testGetIngredientValidSlot() {
        ArrayList<String> want = new ArrayList<>();
        HashMap<String, Slot> slotMap = new HashMap<>();
        slotMap.put("MOCK", TestHelper.mockSlotWithValue("Zutat", "kartoffel", true));

        ArrayList<String> have = SlotFilter.getIngredient(slotMap);
        want.add("kartoffel");

        //assert
        assertEquals(want, have);
    }

    @Test
    public void testGetIngredientNullPointer() {
        //arrange
        ArrayList<String> have = new ArrayList<>();
        Map<String, Slot> slotMap = new HashMap<>();

        //act
        ArrayList<String> want = SlotFilter.getIngredient(slotMap);

        //assert
        assertEquals(want, have);
    }
}