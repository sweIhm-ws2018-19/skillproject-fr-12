package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;
import com.amazon.ask.model.slu.entityresolution.*;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import soupit.hilfsklassen.JsonService;
import soupit.model.Rezept;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class RezeptAuswahlHandlerTest {
    private final static String WIE_VIELE_PORTIONEN = "Wie viele Portionen möchtest du kochen?";

    private RezeptAuswahlHandler handler;

    @Before
    public void setup() {
        handler = new RezeptAuswahlHandler();
    }

    @Test
    public void testCanHandle() {
        HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testGetRezepte() {
        ArrayList<Rezept> want = new ArrayList<>();

        Rezept r = TestHelper.generateRezept("kartoffelsuppe", 0);
        JSONArray j = new JSONArray(new Rezept[]{r});
        HandlerInput inputmock = TestHelper.mockInputWithSessionAttributes(new String[]{"REZEPT_FOUND"}, new Object[]{j.toString()});

        ArrayList<Rezept> have = handler.getRezepte(inputmock);

        for (int i = 0; i < want.size(); i++) {
            assertEquals(want.get(i), have.get(i));
        }
    }

    @Test
    public void testCheckSuppeZahlValid() {
        int want = 0;

        Rezept r = TestHelper.generateRezept("kartoffelsuppe", 0);
        int have = handler.checkSuppeZahl("1", new ArrayList<>(Arrays.asList(new Rezept[]{r, r, r})));

        assertEquals(want, have);
    }

    @Test
    public void testCheckSuppeZahlInvalid() {
        int want = -1;

        Rezept r = TestHelper.generateRezept("kartoffelsuppe", 0);
        int have = handler.checkSuppeZahl("17", new ArrayList<>(Arrays.asList(new Rezept[]{r, r, r})));

        assertEquals(want, have);
    }

    @Test
    public void testCheckSuppeText() {
        int want = 0;

        Rezept r = TestHelper.generateRezept("kartoffelsuppe", 0);
        Rezept rez = TestHelper.generateRezept("tomatensuppe", 1);
        int have = handler.checkSuppeText("kartoffelsuppe", new ArrayList<>(Arrays.asList(new Rezept[]{r, rez, rez})));

        assertEquals(want, have);
    }
}
