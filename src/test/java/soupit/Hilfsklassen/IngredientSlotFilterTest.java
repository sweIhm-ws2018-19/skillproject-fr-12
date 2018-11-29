package test.java.soupit.Hilfsklassen;

import com.amazon.ask.model.Slot;
import main.java.soupit.Hilfsklassen.SlotFilter;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IngredientSlotFilterTest {

    @Test
    public void getIngredientEmptyMap() {
        //arrange
        ArrayList<String> have = new ArrayList<>();
        Map<String, Slot> slotMap = new HashMap<>();


        //act
        ArrayList<String> want = SlotFilter.getIngredient(slotMap);
        //assert
        assertEquals(want, have);
    }

    @Test
    public void getIngredientNullPointer() {
        //arrange
        ArrayList<String> have = new ArrayList<>();
        Map<String, Slot> slotMap = new HashMap<>();


        //act
        ArrayList<String> want = SlotFilter.getIngredient(slotMap);
        //assert
        assertEquals(want, have);
    }



}