package ui;

import model.Event;
import model.EventLog;
import model.Player;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Represents the starting screen of the gui
public class StartScreen extends JPanel implements ActionListener {

    private static final String JSON_STORE = "data/fullPlayerData.json";
    private JLabel name;
    private JLabel level;
    private JFrame frame;
    private JPanel panel;
    private JButton addPlayerBtn;
    private JButton saveBtn;
    private JButton loadBtn;
    private JButton editPlayer;
    private JTextField nameEnter;
    private JTextField levelEnter;
    private JList<String> addedPlayers;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private ArrayList<Player> playerList = new ArrayList<>();
    private ImageIcon img = new ImageIcon("data/BtnCheckMark.png");
    private JLabel btnIconAP;
    private JLabel btnIconSD;
    private JLabel btnIconLD;

    /*
     * EFFECTS: Constructs a new start screen
     */
    public StartScreen() {
        initializeBtns();
        initializeLabels();
        initializeFields();
        makeList();
        createPanel();
        createFrame();
    }

    /*
     * EFFECTS: Creates a JList for users to see added players
     */
    private void makeList() {
        addedPlayers = new JList<>();
        addedPlayers.setModel(model);
        addedPlayers.setBounds(30, 55, 500, 400);
    }

    /*
     * EFFECTS: Creates the JTextFields for users to enter information
     */
    private void initializeFields() {
        nameEnter = new JTextField(20);
        nameEnter.setBounds(70, 20, 165, 25);

        levelEnter = new JTextField(20);
        levelEnter.setBounds(290, 20, 50, 25);
    }

    /*
     * EFFECTS: Indicates to user what information should be entered in the text boxes and initializes green check boxes
     *          to indicate to user their action has been performed.
     */
    private void initializeLabels() {
        name = new JLabel("Name:");
        name.setBounds(30, 20, 60, 25);

        level = new JLabel("Level:");
        level.setBounds(250, 20, 60, 25);

        btnIconAP = new JLabel();
        btnIconAP.setIcon(scaleImage(img));
        btnIconAP.setBounds(500, 18, 30, 30);
        btnIconAP.setVisible(false);

        btnIconSD = new JLabel();
        btnIconSD.setIcon(scaleImage(img));
        btnIconSD.setBounds(640, 490, 30, 30);
        btnIconSD.setVisible(false);

        btnIconLD = new JLabel();
        btnIconLD.setIcon(scaleImage(img));
        btnIconLD.setBounds(490, 490, 30, 30);
        btnIconLD.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Creates the JFrame where all visual elements will be placed on
     */
    private void createFrame() {
        frame = new JFrame();
        frame.add(panel);
        frame.setSize(750, 600);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event e : EventLog.getInstance()) {
                    System.out.println(e.toString() + "\n");
                }
                System.exit(0);
            }
        });

        frame.setTitle("Match History Tracker");
        frame.setVisible(true);
    }

    /*
     * EFFECTS: Creates the panel with all visual elements
     */
    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.add(name);
        panel.add(nameEnter);
        panel.add(level);
        panel.add(levelEnter);
        panel.add(addPlayerBtn);
        panel.add(addedPlayers);
        panel.add(saveBtn);
        panel.add(loadBtn);
        panel.add(editPlayer);
        panel.add(btnIconAP);
        panel.add(btnIconSD);
        panel.add(btnIconLD);
    }

    /*
     * EFFECTS: Creates the buttons on the panel
     */
    public void initializeBtns() {
        addPlayerBtn = new JButton("Add Player");
        addPlayerBtn.addActionListener(this);
        addPlayerBtn.setBounds(350, 20, 150, 25);

        saveBtn = new JButton("Save");
        saveBtn.addActionListener(this);
        saveBtn.setBounds(580, 525, 150, 25);

        loadBtn = new JButton("Load");
        loadBtn.addActionListener(this);
        loadBtn.setBounds(430, 525, 150, 25);

        editPlayer = new JButton("Edit Player");
        editPlayer.addActionListener(this);
        editPlayer.setBounds(22, 460, 150, 25);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nameVal = nameEnter.getText();
        String lvlVal = levelEnter.getText();

        if (e.getSource() == addPlayerBtn) {
            playerList.add(new Player(nameVal, Integer.parseInt(lvlVal)));
            model.addElement("User: " + nameVal + " - level " + lvlVal);
            clearTextFields();
            displayButton(btnIconAP);
        } else if (e.getSource() == saveBtn) {
            writePlayerList(playerList);
            displayButton(btnIconSD);
            System.out.println("Data saved!");
        } else if (e.getSource() == loadBtn) {
            try {
                readFile();
                displayButton(btnIconLD);
                System.out.println("Successfully loaded data!");
            } catch (IOException ex) {
                System.out.println("Could not read from the file: " + JSON_STORE);
            }
        } else {
            new AddMatchScreen(addedPlayers.getSelectedValue().split(" ")[1],
                    playerList.get(addedPlayers.getSelectedIndex()));
        }
    }

    /*
     * EFFECTS: Resets the text fields that players enter information in
     */
    private void clearTextFields() {
        nameEnter.setText("");
        levelEnter.setText("");
    }

    /*
     * EFFECTS: Writes player list onto json file as an array where each entry is a player
     */
    private void writePlayerList(ArrayList<Player> playerList) {
        JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
        try {
            jsonWriter.open();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + JSON_STORE);
        }
        jsonWriter.writeGui(playerList);
        jsonWriter.close();
    }

    /*
     * EFFECTS: Reads from file containing stored data
     */
    private void readFile() throws IOException {
        JsonReader jsonReader = new JsonReader(JSON_STORE);
        ArrayList<Player> readList = jsonReader.readList();

        model.clear();
        for (Player p : readList) {
            model.addElement("User: " + p.getName() + " - level " + p.getLevel());
            playerList.add(p);
        }
    }

    /*
     * EFFECTS: Displays a green check mark by the pressed button. Will disappear after 2 seconds
     */
    private void displayButton(JLabel l) {
        ActionListener buttonStop = e -> l.setVisible((false));

        l.setVisible(true);

        Timer timer = new Timer(0, buttonStop);
        timer.setRepeats(false);
        timer.setInitialDelay(1500);
        timer.start();
    }

    /*
     *  EFFECTS: Scales the green check mark images to an appropriate size
     */
    public ImageIcon scaleImage(ImageIcon i) {
        return new ImageIcon(i.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    }
}
