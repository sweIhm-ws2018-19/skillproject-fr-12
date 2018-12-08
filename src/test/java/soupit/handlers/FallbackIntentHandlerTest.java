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

public class FallbackIntentHandlerTest {
    private FallbackIntentHandler handler;

    @Before
    public void setup(){
        handler = new FallbackIntentHandler();
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

        Boolean b = have.contains("Tut mir leid, das weiss ich nicht. Sage einfach Hilfe.");

        assertTrue(b);
    }
}
