package ui;

import model.Match;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the gui where users can add matches to profiles
public class AddMatchScreen extends JPanel implements ActionListener {
    private JButton addGameBtn;
    private JFrame frame;
    private JPanel panel;
    private JButton winLoss;
    private JTextField championEnter;
    private JLabel champion;
    private JLabel result;
    private JLabel btnIcon;
    private ImageIcon img = new ImageIcon("data/BtnCheckMark.png");
    private JList<String> matches;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private Player player;

    /*
     * REQUIRES: playerName
     * EFFECTS: Constructs a new window for players to add games to their profile
     */
    public AddMatchScreen(String playerName, Player plyr) {

        if (plyr.getRecord() != null) {
            for (Match m : plyr.getRecord()) {
                model.addElement("Played: " + m.getChampion() + "  " + "Result: " + getResultAsString(m.getResult()));
            }
        }

        player = plyr;

        initializeBtns();
        initializeField();
        initializeLabels();

        matches = new JList<>();
        matches.setModel(model);
        matches.setBounds(200, 15, 190, 240);

        initializePanel();

        frame = new JFrame();
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Match Adder - " + playerName);
        frame.setVisible(true);

    }

    /*
     * EFFECTS: Creates a panel for all the visual elements
     */
    private void initializePanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.add(addGameBtn);
        panel.add(champion);
        panel.add(result);
        panel.add(winLoss);
        panel.add(championEnter);
        panel.add(matches);
        panel.add(btnIcon);
    }

    /*
     * EFFECTS: Creates JLabels to indicate to users where and what information needs to be entered
     */
    private void initializeLabels() {
        champion = new JLabel("Champion:");
        champion.setBounds(15, 15, 150, 25);
        result = new JLabel("Result:");
        result.setBounds(15, 80, 150, 25);
        btnIcon = new JLabel();
        btnIcon.setIcon(scaleImage(img));
        btnIcon.setBounds(70, 170, 40, 40);
        btnIcon.setVisible(false);
    }

    /*
     * EFFECTS: Creates the JTextFields for users to enter information
     */
    private void initializeField() {
        championEnter = new JTextField();
        championEnter.setBounds(15, 50, 150, 25);
    }

    /*
     * EFFECTS: Creates buttons for user to edit match result and to add a match
     */
    private void initializeBtns() {
        addGameBtn = new JButton("Add Game");
        addGameBtn.addActionListener(this);
        addGameBtn.setBounds(15, 230, 150, 25);

        winLoss = new JButton("Win");
        winLoss.addActionListener(this);
        winLoss.setBounds(15, 110, 150, 25);
    }

    /*
     * EFFECTS: Gives results "Win" or "Loss" as a boolean
     */
    public boolean getResultAsBoolean(JButton btn) {
        if (btn.getText() == "Win") {
            return true;
        } else {
            return false;
        }
    }

    /*
     * EFFECTS: Gives result of match as String from a boolean
     */
    public String getResultAsString(boolean b) {
        if (b == true) {
            return "Win";
        } else {
            return "Loss";
        }
    }

    /*
     *  EFFECTS: Scales the green check mark images to an appropriate size
     */
    public ImageIcon scaleImage(ImageIcon i) {
        return new ImageIcon(i.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String champName = championEnter.getText();
        boolean resultBoolean = getResultAsBoolean(winLoss);
        String resultString = winLoss.getText();

        if (e.getSource() == winLoss && winLoss.getText() == "Win") {
            winLoss.setText("Loss");
        } else if (e.getSource() == winLoss && winLoss.getText() == "Loss") {
            winLoss.setText("Win");
        } else {
            player.addMatch(champName, resultBoolean);
            model.addElement("Played: " + champName + "  " + "Result: " + resultString);
            championEnter.setText("");
            displayButton();
        }
    }

    /*
     * EFFECTS: Displays a green check mark by the "Add Game" button. Will disappear after 2 seconds
     */
    private void displayButton() {
        ActionListener buttonStop = e -> btnIcon.setVisible(false);

        btnIcon.setVisible(true);

        Timer timer = new Timer(0, buttonStop);
        timer.setRepeats(false);
        timer.setInitialDelay(1500);
        timer.start();
    }
}


