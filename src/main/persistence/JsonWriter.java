package persistence;

import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

// JsonWriter is templated off the supplied Workroom example for CPSC 210. https://github.com/stleary/JSON-java.git
// Represents a writer that writes the JSON representation of a player to a file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    /*
     * EFFECTS: Creates writer to write onto destination file
     */
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /*
     * MODIFIES: this
     * EFFECTS: Opens the writer and throws FileNotFoundException if the destination file can't be opened to write on
     */
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    public void write(Player player) {
        JSONObject json = player.toJson();
        saveToFile(json.toString(TAB));
    }

    /*
     * EFFECTS: Writes data of Players to Json file. (Saves data)
     */
    public void writeGui(ArrayList<Player> playerList) {
        JSONArray jsonArray = new JSONArray();
        for (Player p : playerList) {
            jsonArray.put(p.toJson());
        }
        saveToFile((jsonArray.toString(TAB)));
    }

    /*
     * MODIFIES: this
     * EFFECTS: Closes the writer
     */
    public void close() {
        writer.close();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Writes the string to the file
     */
    private void saveToFile(String json) {
        writer.print(json);
    }
}
