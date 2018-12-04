package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;
import com.amazon.ask.model.slu.entityresolution.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class RezeptAuswahlHandlerTest {
    private RezeptAuswahlHandler handler;

    @Before
    public void setup() {
        handler = new RezeptAuswahlHandler();
    }

    @Test
    public void testCanHandle(){
        HandlerInput inputMock = Mockito.mock(HandlerInput.class);
        when(inputMock.matches(any())).thenReturn(true);
        assertTrue(handler.canHandle(inputMock));
    }

    @Test
    public void testHandle(){
        //Resolution Mock
        Resolution resolutionMock = Resolution.builder()
                .withStatus(Status.builder()
                        .withCode(StatusCode.valueOf("ER_SUCCESS_MATCH"))
                        .build())
                .addValuesItem(ValueWrapper.builder()
                        .withValue(Value.builder()
                                .withName("2")
                                .build())
                        .build())
                .build();

        //IntentRequest Mock
        IntentRequest requestMock = IntentRequest.builder()
                .withIntent(Intent.builder()
                        .putSlotsItem("zutat", Slot.builder()
                                .withResolutions(Resolutions.builder()
                                        .addResolutionsPerAuthorityItem(resolutionMock)
                                        .build())
                                .withName("Zahl")
                                .build())
                        .build())
                .build();

        //HandlerInput Mock
        HandlerInput inputMock = HandlerInput.builder()
                .withRequestEnvelope(RequestEnvelope.builder()
                        .withRequest(requestMock)
                        .build())
                .build();

        Response response = handler.handle(inputMock).get();

        assertFalse(response.getShouldEndSession());
        assertTrue(response.getOutputSpeech().toString().contains("Wir werden eine karottensuppe kochen."));
    }

    @Test
    public void testGetRezepte(){
        HandlerInput inputMock = Mockito.mock(HandlerInput.class);

        String[] want = new String[]{"kartoffelsuppe", "karottensuppe", "tomatensuppe"};
        String[] have = handler.getRezepte(inputMock);

        for(int i = 0; i < want.length; i++){
            assertEquals(want[i], have[i]);
        }
    }

    @Test
    public void testCheckSuppeZahl(){
        String want = "kartoffelsuppe";
        String have = handler.checkSuppeZahl("1", new String[]{"kartoffelsuppe", "karottensuppe", "tomatensuppe"});

        assertEquals(want, have);
    }

    @Test
    public void testCheckSuppeText(){
        String want = "tomatensuppe";
        String have = handler.checkSuppeText("tomatensuppe", new String[]{"kartoffelsuppe", "karottensuppe", "tomatensuppe"});

        assertEquals(want, have);
    }
}
