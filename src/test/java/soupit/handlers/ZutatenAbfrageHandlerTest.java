package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ZutatenAbfrageHandlerTest {
    private ZutatenAbfrageHandler handler;

    @Before
    public void setup() {
        handler = new ZutatenAbfrageHandler();
    }

    @Test
    public void testCanHandle() {
        HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testHandleNoSessionAttributes(){
        HandlerInput inputMock = TestHelper.mockEmptyInput();

        Response response = handler.handle(inputMock).get();

        assertFalse(response.getShouldEndSession());
        assertTrue(response.getOutputSpeech().toString().contains("Ich weiss nicht welches Deine ausgewählte Zutat ist. Nenne mir eine Zutat."));
    }

    @Test
    public void testHandleSingleIngredient(){
        String[] keys = new String[]{"ZUTAT"};
        ArrayList<String> att = new ArrayList<>(Arrays.asList(new String[]{"kartoffel"}));
        Object[] attributes = new Object[]{att};

        HandlerInput inputMock = TestHelper.mockInputWithSessionAttributes(keys, attributes);

        Response response = handler.handle(inputMock).get();

        assertFalse(response.getShouldEndSession());
        assertTrue(response.getOutputSpeech().toString().contains("Deine ausgeählte Zutat ist"));
    }

    @Test
    public void testHandleMultipleIngredients(){
        String[] keys = new String[]{"ZUTAT"};
        ArrayList<String> att = new ArrayList<>(Arrays.asList(new String[]{"kartoffel", "karotte", "tomate"}));
        Object[] attributes = new Object[]{att};

        HandlerInput inputMock = TestHelper.mockInputWithSessionAttributes(keys, attributes);

        Response response = handler.handle(inputMock).get();

        assertFalse(response.getShouldEndSession());
        assertTrue(response.getOutputSpeech().toString().contains("Du hast folgende Zutaten ausgewählt:"));
    }
}
