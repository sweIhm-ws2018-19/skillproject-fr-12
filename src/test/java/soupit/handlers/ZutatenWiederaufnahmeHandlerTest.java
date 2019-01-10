package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ZutatenWiederaufnahmeHandlerTest {
    private ZutatenWiederaufnahmeHandler handler;

    @Before
    public void setup(){
        handler = new ZutatenWiederaufnahmeHandler();
    }

    @Test
    public void testCanHandle() {
        HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testHandle() {
        HandlerInput input = TestHelper.mockInputWithoutSlot();

        String have = "";
        Response response = handler.handle(input).get();
        SsmlOutputSpeech ssmlOut = (SsmlOutputSpeech) response.getOutputSpeech();
        have = ssmlOut.getSsml();

        Boolean b = have.contains("Es wurden noch keine Zutaten ausgeschlossen.");

        assertTrue(b);
    }

    @Test
    public void testHandleWithSlot() {
        HandlerInput input = TestHelper.mockInputWithSlot("ZUTAT_AUSSCHLIESSEN", new ArrayList<String>().toString(), true);

        String have = "";
        Response response = handler.handle(input).get();
        SsmlOutputSpeech ssmlOut = (SsmlOutputSpeech) response.getOutputSpeech();
        have = ssmlOut.getSsml();

        Boolean b = have.contains("Es wurden noch keine Zutaten ausgeschlossen.");

        assertTrue(b);
    }
}
