package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class PortionenAuswahlHandlerTest {
    private PortionenAuswahlHandler handler;

    @Before
    public void setup(){
        handler = new PortionenAuswahlHandler();
    }

    @Test
    public void testCanHandle() {
        HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testHandle() {
        HandlerInput input = TestHelper.mockInputWithEmptySlot("Anzahl");

        String have = "";
        Response response = handler.handle(input).get();
        SsmlOutputSpeech ssmlOut = (SsmlOutputSpeech) response.getOutputSpeech();
        have = ssmlOut.getSsml();

        Boolean b = have.contains("Entschuldigung, ich habe dich nicht verstanden.");

        assertTrue(b);
    }

    @Test
    public void testHandleSinnvoll() {
        HandlerInput input = TestHelper.mockInputWithSlot("Anzahl", "test", true);

        String have = "";
        Response response = handler.handle(input).get();
        SsmlOutputSpeech ssmlOut = (SsmlOutputSpeech) response.getOutputSpeech();
        have = ssmlOut.getSsml();

        Boolean b = have.contains("Entschuldigung, ich habe dich nicht verstanden.");

        assertTrue(b);
    }
}
