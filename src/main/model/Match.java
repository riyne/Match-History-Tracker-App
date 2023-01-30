package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a match that includes the character played and result of the match
public class Match implements Writable {

    private String champion; // name of character played
    private boolean result;  // the result of the match (True = win, False = loss)

    /*
     * REQUIRES: champion should be a character from the game League of Legends
     * EFFECTS: Constructs a new match
     */
    public Match(String champion, boolean result) {
        this.champion = champion;
        this.result = result;
    }

    /*
     * EFFECTS: Returns the name of the champion played in the given match
     */
    public String getChampion() {
        return champion;
    }

    /*
     * EFFECTS: Returns the result of the given match
     */
    public boolean getResult() {
        return result;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("champion name", champion);
        json.put("result", result);
        return json;
    }

}
