package soupit.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;
import com.amazon.ask.model.slu.entityresolution.*;

import java.util.HashMap;
import java.util.Map;

public final class TestHelper {
    private final static String VALID_SLOT = "ER_SUCCESS_MATCH";
    private final static String INVALID_SLOT = "ER_SUCCESS_NO_MATCH";

    private TestHelper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * minimaler HandlerInput
     *
     * @return HandlerInput
     */
    public static HandlerInput mockEmptyInput() {
        //HandlerInput Mock
        HandlerInput inputMock = HandlerInput.builder()
                .withRequestEnvelope(RequestEnvelope.builder()
                        .withSession(Session.builder().build())
                        .build())
                .build();

        return inputMock;
    }

    /**
     * HandlerInput ohne einen Slot
     *
     * @return HandlerInput
     */
    public static HandlerInput mockInputWithoutSlot() {
        //HandlerInput Mock
        HandlerInput inputMock = HandlerInput.builder()
                .withRequestEnvelope(RequestEnvelope.builder()
                        .withRequest(IntentRequest.builder()
                                .withIntent(Intent.builder().build())
                                .build())
                        .withSession(Session.builder().build())
                        .build())
                .build();

        return inputMock;
    }

    /**
     * HandlerInput mit einem Slot
     *
     * @param slotName:  Name des Slots
     * @param slotValue: Value des Slots ("Name")
     * @param valid:     true -> valider Slot; false -> invalider Slot
     * @return HandlerInput
     */
    public static HandlerInput mockInputWithSlot(String slotName, String slotValue, boolean valid) {
        //IntentRequest Mock
        IntentRequest requestMock = IntentRequest.builder()
                .withIntent(Intent.builder()
                        .putSlotsItem("MOCK", mockSlotWithValue(slotName, slotValue, valid))
                        .build())
                .build();

        //HandlerInput Mock
        HandlerInput inputMock = HandlerInput.builder()
                .withRequestEnvelope(RequestEnvelope.builder()
                        .withRequest(requestMock)
                        .withSession(Session.builder().build())
                        .build())
                .build();

        return inputMock;
    }

    /**
     * HandlerInput mit mehreren  Slots
     *
     * @param slotNames:  Namen der Slots
     * @param slotValues: Values der Slots ("Name")
     * @param valid:     true -> valider Slot; false -> invalider Slot
     * @return HandlerInput
     */
    public static HandlerInput mockInputWithSlots(String[] slotNames, String[] slotValues, boolean[] valid) {
        if (slotNames.length == 0 || slotNames.length != slotValues.length || slotValues.length != valid.length)
            throw new IllegalArgumentException("Fehlerhafte Arrays als Input!");

        //IntentRequest Mock
        IntentRequest requestMock = IntentRequest.builder()
                .withIntent(mockIntentWithSlots(slotNames, slotValues, valid))
                .build();

        //HandlerInput Mock
        HandlerInput inputMock = HandlerInput.builder()
                .withRequestEnvelope(RequestEnvelope.builder()
                        .withRequest(requestMock)
                        .withSession(Session.builder().build())
                        .build())
                .build();

        return inputMock;
    }

    /**
     * Intent mit mehreren  Slots
     *
     * @param slotNames:  Namen der Slots
     * @param slotValues: Values der Slots ("Name")
     * @param valid:     true -> valider Slot; false -> invalider Slot
     * @return Intent
     */
    private static Intent mockIntentWithSlots(String[] slotNames, String[] slotValues, boolean[] valid){
        Intent.Builder builder = Intent.builder();

        for(int i = 0; i < slotNames.length; i++){
            builder.putSlotsItem("MOCK" + i, mockSlotWithValue(slotNames[i], slotValues[i], valid[i]));
        }

        return builder.build();
    }

    /**
     * einzelner Slot mit Inhalt
     *
     * @param slotName:  Name des Slots
     * @param slotValue: Value des Slots ("Name")
     * @param valid:     true -> valider Slot; false -> invalider Slot
     * @return Slot
     */
    public static Slot mockSlotWithValue(String slotName, String slotValue, boolean valid) {
        //Slot Mock
        Slot slotMock = Slot.builder()
                .withResolutions(Resolutions.builder()
                        .addResolutionsPerAuthorityItem(Resolution.builder()
                                .withStatus(Status.builder()
                                        .withCode(StatusCode.valueOf(valid ? VALID_SLOT : INVALID_SLOT))
                                        .build())
                                .addValuesItem(ValueWrapper.builder()
                                        .withValue(Value.builder()
                                                .withName(slotValue)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .withName(slotName)
                .build();

        return slotMock;
    }

    /**
     * HandlerInput mit beliebig vielen (aber mindestens einem) Session Attributen
     *
     * @param keys: keys der Session Attribute
     * @param attributes: tatsächliche Session Attribute
     * @return HandlerInput
     */
    public static HandlerInput mockInputWithSessionAttributes(String[] keys, Object[] attributes){
        if (keys.length == 0 || keys.length != attributes.length)
            throw new IllegalArgumentException("Fehlerhafte Arrays als Input!");

        HashMap<String, Object> attributeMap = new HashMap<>();

        for(int i = 0; i < keys.length; i++){
            attributeMap.put(keys[i], attributes[i]);
        }

        //HandlerInput Mock
        HandlerInput inputMock = HandlerInput.builder()
                .withRequestEnvelope(RequestEnvelope.builder()
                        .withSession(Session.builder()
                                .withAttributes(attributeMap)
                                .build())
                        .build())
                .build();

        return inputMock;
    }
}