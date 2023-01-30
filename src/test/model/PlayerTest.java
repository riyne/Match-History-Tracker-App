package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player noRecordPlayer;
    private Player playerWithOneGame;
    private Player playerWithThreeGames;
    private ArrayList<Match> emptyRecord;

    @BeforeEach
    public void runBefore() {
        noRecordPlayer = new Player("Abe", -1);
        playerWithOneGame = new Player("Bob", 10);
        playerWithThreeGames = new Player("Cody", 0);
        emptyRecord = new ArrayList<>();
        playerWithOneGame.addMatch("Aatrox", true);
        playerWithThreeGames.addMatch("Garen", true);
        playerWithThreeGames.addMatch("Alistar", true);
        playerWithThreeGames.addMatch("Yasuo", false);
    }

    @Test
    public void testConstructor() {
        assertEquals("Abe", noRecordPlayer.getName());
        assertEquals("Bob", playerWithOneGame.getName());
        assertEquals(emptyRecord, noRecordPlayer.getRecord());
        assertEquals("Aatrox", playerWithOneGame.getRecord().get(0).getChampion());
        assertTrue(playerWithOneGame.getRecord().get(0).getResult());
        assertEquals("Garen", playerWithThreeGames.getRecord().get(0).getChampion());
        assertTrue(playerWithThreeGames.getRecord().get(0).getResult());
        assertEquals("Alistar", playerWithThreeGames.getRecord().get(1).getChampion());
        assertTrue(playerWithThreeGames.getRecord().get(1).getResult());
        assertEquals("Yasuo", playerWithThreeGames.getRecord().get(2).getChampion());
        assertFalse(playerWithThreeGames.getRecord().get(2).getResult());
    }

    @Test
    public void testConstructorWithNegLevel() {
        assertEquals(0, noRecordPlayer.getLevel());
    }

    @Test
    public void testConstructorWithInitialLevel() {
        assertEquals(10, playerWithOneGame.getLevel());
    }

    @Test
    public void testAddMatchOnce() {
        noRecordPlayer.addMatch("Aatrox", true);
        assertEquals("Aatrox", noRecordPlayer.getRecord().get(0).getChampion());
        assertTrue(noRecordPlayer.getRecord().get(0).getResult());
    }

    @Test
    public void testAddMatchMultiple() {
        playerWithOneGame.addMatch("Sett", false);
        playerWithOneGame.addMatch("Zed", true);
        assertEquals("Sett", playerWithOneGame.getRecord().get(1).getChampion());
        assertFalse(playerWithOneGame.getRecord().get(1).getResult());
        assertEquals("Zed", playerWithOneGame.getRecord().get(2).getChampion());
        assertTrue(playerWithOneGame.getRecord().get(2).getResult());
    }
}