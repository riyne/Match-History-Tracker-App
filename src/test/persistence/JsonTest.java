package persistence;

import model.Match;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test is templated off the supplied Workroom example for CPSC 210. https://github.com/stleary/JSON-java.git
public class JsonTest {
    protected void checkRecord(String champion, boolean result, Match match) {
        assertEquals(champion, match.getChampion());
        assertEquals(result, match.getResult());
    }
}
