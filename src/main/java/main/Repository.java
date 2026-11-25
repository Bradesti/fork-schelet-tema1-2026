package main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clasa care se ocupă de templateurile de fișiere și stocarea lor
 */
public class Repository {

    private Map <String, Map <String, Document<?>>> documents;
}
