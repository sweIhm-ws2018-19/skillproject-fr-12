package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class NoIntentHandlerTest {
    private NoIntentHandler handler;

    @Before
    public void setup(){
        handler = new NoIntentHandler();
    }

    @Test
    public void testCanHandle() {
        HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testHandle() {
        HandlerInput input = TestHelper.mockEmptyInput();

        Response response = handler.handle(input).get();
        SsmlOutputSpeech ssmlOut = (SsmlOutputSpeech) response.getOutputSpeech();
        String have = ssmlOut.getSsml();

        Boolean b = have.contains("Entschuldigung,");

        assertFalse(response.getShouldEndSession());
        assertTrue(b);
    }

    @Test
    public void testHandleWithSlot() {
        HandlerInput input = TestHelper.mockInputWithSessionAttributes(new String[]{"LAST_INTENT", "REZEPT_FOUND"}, new String[]{"PortionenAuswahlIntent", "[]"});

        Response response = handler.handle(input).get();
        SsmlOutputSpeech ssmlOut = (SsmlOutputSpeech) response.getOutputSpeech();
        String have = ssmlOut.getSsml();

        Boolean b = have.contains("Welches Rezept möchtest du stattdessen kochen?");

        assertFalse(response.getShouldEndSession());
        assertTrue(b);
    }
}
