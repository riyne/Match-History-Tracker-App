package persistence;

import model.Match;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

// Tests are templated off the supplied Workroom example for CPSC 210. https://github.com/stleary/JSON-java.git
public class JsonWriterTest extends JsonTest {
    private ArrayList<Player> exampleList;

    @BeforeEach
    public void runBefore() {
        Player joe = new Player("joe", 10);
        joe.addMatch("Garen", true);

        Player bob = new Player("bob", 5);
        bob.addMatch("Nasus", false);
        bob.addMatch("Azir", true);

        exampleList = new ArrayList<>();
        exampleList.add(joe);
        exampleList.add(bob);
    }

    @Test
    public void testGuiWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("/data/Invalid.fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            System.out.println("IOException was caught.");
            // pass
        }
    }

    @Test
    public void testGuiWriterNormalFile() {
        try {
            JsonWriter writer = new JsonWriter("data/testWriterNormalList.json");
            writer.open();
            writer.writeGui(exampleList);
            writer.close();
        } catch (IOException e) {
            fail("Exception shouldn't be thrown");
        }

        try {
            JsonReader reader = new JsonReader("data/testWriterNormalList.json");
            exampleList = reader.readList();
            assertEquals("joe", exampleList.get(0).getName());
            assertEquals(10, exampleList.get(0).getLevel());
            assertEquals(1, exampleList.get(0).getRecord().size());
            assertEquals("Garen", exampleList.get(0).getRecord().get(0).getChampion());
            assertTrue(exampleList.get(0).getRecord().get(0).getResult());
        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }

    @Test
    public void testWriterInvalidFile() {
        try {
            Player player = new Player("Ryan", 10);
            JsonWriter writer = new JsonWriter("./data/A\0Invalid.fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            System.out.println("IOException was caught!");
            // pass
        }
    }

    @Test
    public void testWriterEmptyRecord() {
        try {
            Player player = new Player("Ryan", 10);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRecord.json");
            writer.open();
            writer.write(player);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRecord.json");
            player = reader.read();
            assertEquals("Ryan", player.getName());
            assertEquals(10, player.getLevel());
            assertEquals(0, player.getRecord().size());
        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }

    @Test
    public void testWriterWithNormalRecords() {
        try {
            Player player = new Player("Ryan", 10);
            player.addMatch("Aatrox", true);
            player.addMatch("Garen", true);
            player.addMatch("Trundle", false);
            JsonWriter writer = new JsonWriter("./data/testWriterWithNormalRecord.json");
            writer.open();
            writer.write(player);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterWithNormalRecord.json");
            player = reader.read();
            assertEquals("Ryan", player.getName());
            assertEquals(10, player.getLevel());
            ArrayList<Match> record = player.getRecord();
            assertEquals(3, record.size());
            checkRecord("Aatrox", true, record.get(0));
            checkRecord("Garen", true, record.get(1));
            checkRecord("Trundle", false, record.get(2));
        } catch (IOException e) {
            fail("Exception shouldn't have been thrown");
        }
    }
}
