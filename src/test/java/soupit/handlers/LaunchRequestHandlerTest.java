package test.java.soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.SsmlOutputSpeech;
import main.java.soupit.handlers.LaunchRequestHandler;

import java.lang.reflect.Method;

import org.junit.Test;
import static org.junit.Assert.*;

public class LaunchRequestHandlerTest {
    private final static String phrase1 = "<speak>Willkommen bei Soup-IT! Als dein persönlicher Assistent begleite ich dich bei der Suppenzubereitung.";

    @Test
   public void canHandle() {
    }

    @Test
    public void handle() {
        HandlerInput.Builder builder = HandlerInput.builder();
        builder.withRequestEnvelope(RequestEnvelope.builder().build());
        HandlerInput input = builder.build();
        LaunchRequestHandler handler = new LaunchRequestHandler();

        Response response = handler.handle(input).get();
        SsmlOutputSpeech ssmlOut = (SsmlOutputSpeech) response.getOutputSpeech();
        String have = ssmlOut.getSsml();

        Boolean b = have.contains(phrase1);
        assertTrue(b);
    }

    @Test
  public void randomResponse() {
        LaunchRequestHandler handler = new LaunchRequestHandler();
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