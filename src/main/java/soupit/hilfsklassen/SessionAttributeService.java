package soupit.hilfsklassen;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;

import java.util.Map;

/**
 * Diese Klasse bietet Hilfsmethoden zur Verwaltung der SessionAttribute.
 * <p>
 * Metoden:
 * - addMapToAttributes: fügt Attribute zu Session-Attributen hinzu
 * - updateLastIntent: aktualisiert das Session-Attribut LAST_INTENT
 */
public final class SessionAttributeService {
    private static SessionAttributeService instance;

    private final static String LAST_INTENT = "LAST_INTENT";

    private SessionAttributeService() {
        //empty
    }

    public static SessionAttributeService getInstance() {
        if (instance == null)
            instance = new SessionAttributeService();

        return instance;
    }

    /**
     * Fügt beliebig viele Attribute zu den bisherigen Session-Attributen hinzu.
     * Bei bereits vorhandenen Attributen werden nur die Werte aktualisiert.
     *
     * @param input:         HandlerInput des aktuellen Intents
     * @param newAttributes: neue Attribute die hinzugefügt werden sollen
     */
    public static void addMapToAttributes(HandlerInput input, Map<String, Object> newAttributes) {
        Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        sessionAttributes.putAll(newAttributes);
        input.getAttributesManager().setSessionAttributes(sessionAttributes);
    }

    /**
     * Aktualisiert das SessionAttribut LAST_INTENT
     *
     * @param input
     * @param intentName
     */
    public static void updateLastIntent(HandlerInput input, String intentName) {
        Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        sessionAttributes.put(LAST_INTENT, intentName);
        input.getAttributesManager().setSessionAttributes(sessionAttributes);
    }

    /**
     * Liefert den Wert des SessionAttributs LAST_INTENT
     * (liefert "none", falls SessionAttribut nicht vorhanden)
     *
     * @param input
     * @return
     */
    public static String getLastIntent(HandlerInput input) {
        Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();

        return (String) sessionAttributes.getOrDefault(LAST_INTENT, "none");
    }

    /**
     * liefert den wert eines SessionAttributs
     * (liefert "none", falls das SessionAttribut nicht vorhanden ist)
     *
     * @param input
     * @param key
     * @return
     */
    public static Object getSingleSessionAttribute(HandlerInput input, String key){
        Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();

        return sessionAttributes.getOrDefault(key, "none");
    }

    /**
     * Fügt ein neues Attribut zu den bisherigen SessionAttributen hinzu.
     * Bei bereits vorhandenen Attributen wird nur der Wert aktualisiert.
     *
     * @param input
     * @param key
     * @param value
     */
    public static void setSingleSessionAttribute(HandlerInput input, String key, Object value){
        Map<String, Object> sessionAttributes = input.getAttributesManager().getSessionAttributes();
        sessionAttributes.put(key, value);
        input.getAttributesManager().setSessionAttributes(sessionAttributes);
    }
}
