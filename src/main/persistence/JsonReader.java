package persistence;

import model.Match;
import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// JsonReader is templated off the supplied Workroom example for CPSC 210. https://github.com/stleary/JSON-java.git
// Represents a reader that will read the JSON player data stored in a file
public class JsonReader {
    private String source;

    /*
     * Creates a reader that will read from the source file
     */
    public JsonReader(String source) {
        this.source = source;
    }

    /*
     * EFFECTS: Reads the player from file and returns it and throws IOException if there's an error reading the file
     */
    public Player read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return analyzePlayer(jsonObject);
    }

    /*
     * REQUIRES: Json file must be an Array with entries being Players
     * EFFECTS: Returns a list of Players from saved data
     */
    public ArrayList<Player> readList() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        ArrayList<Player> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(analyzePlayer(jsonArray.getJSONObject(i)));
        }
        return result;
    }

    /*
     * EFFECTS: Reads the sources file as a string and returns it
     */
    public String readFile(String source) throws IOException {
        StringBuilder dataBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> dataBuilder.append(s));
        }
        return dataBuilder.toString();
    }

    /*
     * EFFECTS: Analyzes player from JSON object and returns it
     */
    private Player analyzePlayer(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String level = jsonObject.getString("level");
        Player player = new Player(name, Integer.valueOf(level));
        addMatches(player, jsonObject);
        return player;
    }

    /*
     * MODIFIES: player
     * EFFECTS: Analyzes matches from JSON object and adds it to player
     */
    private void addMatches(Player player, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("record");
        for (Object json : jsonArray) {
            JSONObject nextMatch = (JSONObject) json;
            addMatch(player, nextMatch);
        }
    }

    /*
     * MODIFIES: players
     * EFFECTS: Analyzes individual match from JSON object and adds it to player
     */
    private void addMatch(Player player, JSONObject jsonObject) {
        String name = jsonObject.getString("champion name");
        Boolean result = (jsonObject.optBoolean("result"));
        Match match = new Match(name, result);
        player.addMatch(match.getChampion(), match.getResult());
    }
}
