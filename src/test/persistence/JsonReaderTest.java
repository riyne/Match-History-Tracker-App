package persistence;

import model.Match;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Tests are templated off the supplied Workroom example for CPSC 210. https://github.com/stleary/JSON-java.git
public class JsonReaderTest extends JsonTest {

//    /*
//     * REQUIRES: Json file must be an Array with entries being Players
//     * EFFECTS: Returns a list of Players from saved data
//     */
//    public ArrayList<Player> readList() throws IOException {
//        String jsonData = readFile(source);
//        JSONArray jsonArray = new JSONArray(jsonData);
//        ArrayList<Player> result = new ArrayList<>();
//        for (int i = 0; i < jsonArray.length(); i++) {
//            result.add(analyzePlayer(jsonArray.getJSONObject(i)));
//        }
//        return result;
//    }

    @Test
    public void testReadListNoFile() {
        JsonReader reader = new JsonReader("data/NonExistentFile.json");
        try {
            reader.readList();
            fail("IOException should've been caught");
        } catch (IOException e) {
            System.out.println("IOException was caught.");
        }
    }


    // Location of example Player list is the same as the same information as the writer equivalent of this test
    @Test
    public void testReadListRegularList() {
        JsonReader reader = new JsonReader("data/testReaderNormalList.json");
        try {
            ArrayList<Player> exampleList = reader.readList();
            assertEquals(2, exampleList.size());

            assertEquals("joe", exampleList.get(0).getName());
            assertEquals("bob", exampleList.get(1).getName());

            assertEquals(10, exampleList.get(0).getLevel());
            assertEquals(5, exampleList.get(1).getLevel());

            assertEquals(1, exampleList.get(0).getRecord().size());
            assertEquals(2, exampleList.get(1).getRecord().size());

            checkRecord("Nasus", false, exampleList.get(1).getRecord().get(0));
            checkRecord("Azir", true, exampleList.get(1).getRecord().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/NonExistentFile.json");
        try {
            Player player = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            System.out.println("IOException was caught!");
            // pass
        }
    }

    @Test
    public void testReaderWithEmptyRecord() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyRecord.json");
        try {
            Player player = reader.read();
            assertEquals("newPlayer", player.getName());
            assertEquals(1, player.getLevel());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderWithNormalRecord() {
        JsonReader reader = new JsonReader("./data/testReaderNormalRecord.json");
        try {
            Player player = reader.read();
            assertEquals("Ryan", player.getName());
            assertEquals(53, player.getLevel());
            ArrayList<Match> record = player.getRecord();
            assertEquals(2, record.size());
            checkRecord("Garen", false, record.get(0));
            checkRecord("Aatrox", true, record.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
