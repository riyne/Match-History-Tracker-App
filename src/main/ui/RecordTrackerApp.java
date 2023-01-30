package ui;


import model.Match;
import model.Player;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// The match history tracker application
public class RecordTrackerApp {
    private static final String JSON_STORE = "./data/playerProfile.json";
    private Player newPlayer;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    /*
     * EFFECTS: Runs the tracker application
     */
    public RecordTrackerApp() {
        input = new Scanner(System.in);
        newPlayer = new Player("NewPlayer", 0);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Process user inputs
     */
    private void runApp() {
        boolean programStatus = true;
        String keyCommand;

        init();

        startMenuName();
        String username = input.next();

        startMenuLevel();
        int level = input.nextInt();

        doAddPlayer(username, level);

        while (programStatus) {
            mainMenu();
            keyCommand = input.next();

            if (keyCommand.equals("q")) {
                programStatus = false;
            } else {
                processInput(keyCommand);
            }
        }

        System.out.println("\nGoodbye!");
    }

    /*
     * EFFECTS: Displays start menu prompting users to enter their name
     */
    private void startMenuName() {
        System.out.println("Enter your name:");
    }

    /*
     * EFFECTS: Displays start menu prompting users to enter their level
     */
    private void startMenuLevel() {
        System.out.println("Enter your account level:");
    }

    /*
     * EFFECTS: Displays main menu with commands available to users
     */
    private void mainMenu() {
        System.out.println("\nProfile: " + newPlayer.getName() + "\t(Level: " + newPlayer.getLevel() + ")");
        System.out.println("Select from:");
        System.out.println("\tAdd game -> a");
        System.out.println("\tView history -> h");
        System.out.println("\tSave profile -> s");
        System.out.println("\tLoad profile -> l");
        System.out.println("\tQuit program -> q");
    }

    /*
     * EFFECTS: Displays the match history of the player
     */
    private void historyMenu() {

        System.out.println("\nHere is your current history:");
        if (newPlayer.getRecord().size() == 0) {
            System.out.println("\tThere are no games in your history!");
        }
        for (Match match : newPlayer.getRecord()) {
            System.out.println("\n\tResult: " + ((match.getResult()) ? "Win" : "Loss"));
            System.out.println("\tChampion: " + match.getChampion());
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Process the user command
     */
    private void processInput(String keyCommand) {
        if (keyCommand.equals("a")) {
            doAddGame(enterMatchChampion(), enterMatchResult());
        } else if (keyCommand.equals("h")) {
            historyMenu();
        } else if (keyCommand.equals("s")) {
            saveProfile();
        } else if (keyCommand.equals("l")) {
            loadProfile();
        } else {
            System.out.println("\nInput command is not valid.");
        }
    }

    /*
     * EFFECTS: Returns the result of the match to be added
     */
    private boolean enterMatchResult() {
        String result;

        System.out.println("\nWhat was the result of your game?");
        System.out.println("\tWin -> w");
        System.out.println("\tLoss -> l");

        result = input.next();

        if (result.equals("w")) {
            return true;
        } else if (result.equals("l")) {
            return false;
        }
        System.out.println("Invalid input!");
        return enterMatchResult();
    }

    /*
     * EFFECTS: Returns the name of the character played in the match to be added
     */
    private String enterMatchChampion() {
        System.out.println("Enter the name of the champion you played:");
        return input.next();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Creates a new player with users given name input
     */
    private void doAddPlayer(String playerName, int level) {
        newPlayer = new Player(playerName, level);

    }

    /*
     * MODIFIES: this
     * EFFECTS: Adds a game to the users current record
     */
    private void doAddGame(String championName, boolean result) {
        Match newMatch = new Match(championName, result);
        newPlayer.getRecord().add(newMatch);
        System.out.println("Added match to your history!");
    }

    /*
     * EFFECTS: Saves current profile to a file
     */
    private void saveProfile() {
        try {
            jsonWriter.open();
            jsonWriter.write(newPlayer);
            jsonWriter.close();
            System.out.println("Profile for " + newPlayer.getName() + " (Level: " + newPlayer.getLevel() + ") has "
                    + "been saved");
        } catch (FileNotFoundException e) {
            System.out.println("Could not write to file " + JSON_STORE);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Loads profile from existing file
     */
    private void loadProfile() {
        try {
            newPlayer = jsonReader.read();
            System.out.println("Profile for " + newPlayer.getName() + "from" + JSON_STORE + "has been loaded");
        } catch (IOException e) {
            System.out.println("Could not read from the file: " + JSON_STORE);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Initializes scanner to detect user input in terminal
     */
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }
}

