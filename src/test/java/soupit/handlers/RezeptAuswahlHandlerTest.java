package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;
import com.amazon.ask.model.slu.entityresolution.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
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

    /*
    @Test
    public void testHandleNumberInput() {
        //HandlerInput Mock
        HandlerInput inputMock = TestHelper.mockInputWithSlot("Zahl", "2", true);

        Response response = handler.handle(inputMock).get();

        assertFalse(response.getShouldEndSession());
        assertTrue(response.getOutputSpeech().toString().contains(WIE_VIELE_PORTIONEN));
    }

    @Test
    public void testHandleTextInput() {
        //HandlerInput Mock
        HandlerInput inputMock = TestHelper.mockInputWithSlot("Suppe", "kartoffelsuppe", true);

        Response response = handler.handle(inputMock).get();

        assertFalse(response.getShouldEndSession());
        assertTrue(response.getOutputSpeech().toString().contains(WIE_VIELE_PORTIONEN));
    }

    @Test
    public void testHandleWrongInput() {
        //HandlerInput Mock
        HandlerInput inputMock = TestHelper.mockInputWithSlot("Zutat", "karotten", true);

        Response response = handler.handle(inputMock).get();

        assertFalse(response.getShouldEndSession());
        assertTrue(response.getOutputSpeech().toString().contains("Bitte wähle zuerst Zutaten aus."));
    }

    @Test
    public void testGetRezepte() {
        String[] want = new String[]{"kartoffelsuppe", "karottensuppe", "tomatensuppe"};
        String[] have = handler.getRezepte();

        for (int i = 0; i < want.length; i++) {
            assertEquals(want[i], have[i]);
        }
    }
    */

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
