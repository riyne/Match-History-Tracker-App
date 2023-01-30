package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a player with a name and an associated match history
public class Player implements Writable {

    private String name;
    private ArrayList<Match> record = new ArrayList<>();
    private int level;

    /*
     * REQUIRES: name can not be null
     * EFFECTS: Constructs a new Player
     */
    public Player(String name, int initialLevel) {
        this.name = name;
        if (initialLevel >= 0) {
            level = initialLevel;
        } else {
            level = 0;
        }
        EventLog.getInstance().logEvent(new Event("A new profile has been added: " + "\n" + this.name
                + " - level " + this.level));
    }

    /*
     * REQUIRES: champion should be a character from the game League of Legends
     * MODIFIES: this
     * EFFECTS: Adds a match to the players current record
     */
    public void addMatch(String champion, boolean result) {
        Match newMatch = new Match(champion, result);
        record.add(newMatch);
        EventLog.getInstance().logEvent(new Event("A new Match has been added to " + this.name));
    }

    /*
     * EFFECTS: Returns the name of the player
     */
    public String getName() {
        return name;
    }

    /*
     * EFFECTS: Returns the players record as a list of Match objects
     */
    public ArrayList<Match> getRecord() {
        return record;
    }

    /*
     * EFFECTS: Returns the players level
     */
    public int getLevel() {
        return level;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("level", Integer.toString(level));
        json.put("record", matchesToJson());
        return json;
    }

    private JSONArray matchesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Match m : record) {
            jsonArray.put(m.toJson());
        }
        return jsonArray;
    }
}
