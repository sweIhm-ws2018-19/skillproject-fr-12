package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ZutatenAuswahlHandlerTest {
    private ZutatenAuswahlHandler handler;

    @Before
    public void setup() {
        handler = new ZutatenAuswahlHandler();
    }

    @Test
    public void testCanHandle() {
        HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testHandleNoInput() {
        HandlerInput inputMock = TestHelper.mockInputWithoutSlot();

        Response response = handler.handle(inputMock).get();

        assertFalse(response.getShouldEndSession());
        assertTrue(response.getOutputSpeech().toString().contains("Hierzu kann ich dir aktuell leider kein passendes Suppenrezept vorschlagen."));
    }

    @Test
    public void testHandleSingleInput() {
        HandlerInput inputMock = TestHelper.mockInputWithSlot("Zutat", "tomate", true);

        Response response = handler.handle(inputMock).get();

        assertFalse(response.getShouldEndSession());
        assertTrue(response.getOutputSpeech().toString().contains("Ich kann dir folgendes Rezept vorschlagen"));
    }

    @Test
    public void testHandleMultiInput() {
        String[] slotNames = new String[]{"Zutat", "Zutat", "Zutat"};
        String[] slotValues = new String[]{"tomate", "karotte", "lkw"};
        boolean[] valid = new boolean[]{true, true, false};
        HandlerInput inputMock = TestHelper.mockInputWithSlots(slotNames, slotValues, valid);

        Response response = handler.handle(inputMock).get();

        assertFalse(response.getShouldEndSession());
        assertTrue(response.getOutputSpeech().toString().contains("Ich kann dir folgende Rezepte vorschlagen"));
    }
}
