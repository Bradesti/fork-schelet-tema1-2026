package main;

import java.util.HashMap;
import java.util.Map;

/**
 * Clasa care se ocupă de templateurile de fișiere și stocarea lor
 */
public class Repository {
    private Map<String, Map<String, Document<?>>> documents;

    public Repository() {
        documents = new HashMap<>();
        documents.put("DamPrices", new HashMap<>());
        documents.put("DamPricesPrediction", new HashMap<>());
        documents.put("GridLoad", new HashMap<>());
        documents.put("GridLoadPrediction", new HashMap<>());
        documents.put("ImbalancePrices", new HashMap<>());
        documents.put("ImbalanceVolumes", new HashMap<>());
    }

    public Document<?> Select(String docType, String date) {
        return (Document<?>) documents.get(docType).get(date);
    }

    public void Insert(String docType, String date, Document<?> doc) {
        documents.get(docType).put(date, doc);
    }
}
