package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import org.junit.Before;
import org.mockito.Mockito;

import java.lang.reflect.Method;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class LaunchRequestHandlerTest {
    private final static String phrase1 = "Willkommen";
    private final static String phrase2 = "Als dein persönlicher Assistent begleite ich dich bei der Suppenzubereitung.";
    private LaunchRequestHandler handler;

    @Before
    public void setup(){
        handler = new LaunchRequestHandler();
    }

    @Test
    public void testCanHandle() {
        HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

//    @Test
//    public void testHandle() {
//        HandlerInput input = TestHelper.mockEmptyInput();
//
//        Response response = handler.handle(input).get();
//        SsmlOutputSpeech ssmlOut = (SsmlOutputSpeech) response.getOutputSpeech();
//        String have = ssmlOut.getSsml();
//
//        Boolean b = have.contains(phrase1);
//        Boolean bo = have.contains(phrase2);
//        assertTrue(b && bo);
//    }

    @Test
    public void testRandomResponse() {
        String have = "";

        try {
            Method method = LaunchRequestHandler.class.getDeclaredMethod("randomResponse");
            method.setAccessible(true);
            have = (String) method.invoke(handler);
        } catch (Exception ex) {
        }

        assertNotNull(have);
        assertNotEquals("", have);
    }
}