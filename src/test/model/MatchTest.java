package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchTest {

    private Match testMatch;

    @BeforeEach
    public void runBefore() {
        testMatch = new Match("Aatrox", true);
    }

    @Test
    public void testConstructor() {
        assertEquals("Aatrox", testMatch.getChampion());
        assertTrue(testMatch.getResult());
    }
}
