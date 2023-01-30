package persistence;

import org.json.JSONObject;

// Writable is templated off the supplied Workroom example for CPSC 210. https://github.com/stleary/JSON-java.git
public interface Writable {
    /*
     * EFFECTS: returns this as a JSON object
     */
    JSONObject toJson();
}
