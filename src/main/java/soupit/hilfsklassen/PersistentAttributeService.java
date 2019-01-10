package soupit.hilfsklassen;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;

import java.util.Map;

public final class PersistentAttributeService {
    private static PersistentAttributeService instance;

    private PersistentAttributeService() {
        //empty
    }

    public static PersistentAttributeService getInstance() {
        if (instance == null)
            instance = new PersistentAttributeService();

        return instance;
    }

    /**
     * liefert den wert eines PersistentAttributs
     * (liefert "none", falls das PersistentAttribut nicht vorhanden ist)
     *
     * @param input
     * @param key
     * @return
     */
    public static Object getSinglePersistentAttribute(HandlerInput input, String key) {
        Map<String, Object> persistentAttributes = input.getAttributesManager().getPersistentAttributes();

        return persistentAttributes.getOrDefault(key, "none");
    }

    /**
     * Fügt ein neues Attribut zu den bisherigen PersistentAttributen hinzu.
     * Bei bereits vorhandenen Attributen wird nur der Wert aktualisiert.
     *
     * @param input
     * @param key
     * @param value
     */
    public static void setSinglePersistentAttribute(HandlerInput input, String key, Object value) {
        Map<String, Object> persistentAttributes = input.getAttributesManager().getPersistentAttributes();
        persistentAttributes.put(key, value);
        input.getAttributesManager().setPersistentAttributes(persistentAttributes);
        input.getAttributesManager().savePersistentAttributes();
    }
}
