package test.java.soupit.Hilfsklassen;

import com.amazon.ask.model.Slot;
import main.java.soupit.Hilfsklassen.SlotFilter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IngredientSlotFilterTest {

    @Test
    void getIngredientEmptyMap() {
        //arrange
        ArrayList<String> have = new ArrayList<>();
        Map<String, Slot> slotMap = new HashMap<>();


        //act
        ArrayList<String> want = SlotFilter.getIngredient(slotMap);
        //assert
        assertEquals(want, have);
    }

    @Test
    void getIngredientNullPointer() {
        //arrange
        ArrayList<String> have = new ArrayList<>();
        Map<String, Slot> slotMap = new HashMap<>();


        //act
        ArrayList<String> want = SlotFilter.getIngredient(slotMap);
        //assert
        assertEquals(want, have);
    }



}