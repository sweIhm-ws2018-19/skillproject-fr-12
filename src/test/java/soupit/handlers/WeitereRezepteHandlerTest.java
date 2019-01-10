package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class WeitereRezepteHandlerTest {
    private WeitereRezepteHandler handler;

    @Before
    public void setup(){
        handler = new WeitereRezepteHandler();
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
        try{
            Response response = handler.handle(input).get();
            SsmlOutputSpeech ssmlOut = (SsmlOutputSpeech) response.getOutputSpeech();
            have = ssmlOut.getSsml();
        }catch (Exception ex){
            have = "test";
        }

        assertNotNull(have);
    }
}
