package ui.gui;

import model.EventLog;
import model.Player;
import persistence.JsonReader;
import java.io.IOException;
import javax.swing.*;

// Overall frame and components of the game app
public class StartScreen {
    //https://stackoverflow.com/questions/22162398/how-to-set-a-background-picture-in-jpanel
    protected static Player player;
    private JFrame frame;
    Boolean keepGoing = true;

    // EFFECTS: frame of the game made with a background image and having a menu implemented; instantiatePlayer frame
    //          prompted overtop until told to stop.
    public StartScreen() {
        Panel background = new Panel();

        frame = new JFrame("GAGAGACHA");
        //https://stackoverflow.com/questions/31526190/jframe-with-background-image-and-a-jpanel?noredirect=1&lq=1
        frame.setContentPane(background);
        //https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                player.playerEventLog(EventLog.getInstance());
                System.exit(0);
            }
        });
        frame.pack();
        frame.add(new MainMenu());
        frame.setLocationRelativeTo(null); // open in center of screen
        frame.setVisible(true);

        while (keepGoing) {
            instantiatePlayer(frame);
        }
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to either create a player or load a player until player is set; error dialog otherwise
    private void instantiatePlayer(JFrame frame) {
        boolean keepGoingHere = true;
        // https://stackoverflow.com/questions/13334198/java-custom-buttons-in-showinputdialog
        Object[] options = { "Create player", "Load player data"};
        JPanel panel = new JPanel();
        panel.add(new JLabel("Welcome to GAGAGACHA!"));
        int result = JOptionPane.showOptionDialog(frame, panel, "Set player data",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, null);
        while (keepGoingHere) {
            if (result == JOptionPane.YES_OPTION) {
                createPlayerPrompt(frame);
                keepGoingHere = false;
            } else if (result == JOptionPane.NO_OPTION) {
                loadPlayer();
                keepGoingHere = false;
            } else {
                System.exit(2);
            }
        }
    }

    // MODIFIES: player
    // EFFECTS: Prompts player to enter name for player and creates new player;
    //          error message if no player name entered, or if player name is/contains spaces
    private void createPlayerPrompt(JFrame frame) {
        // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
        String s = (String)JOptionPane.showInputDialog(frame,  "Enter your name:\n",
                "Create new player", JOptionPane.PLAIN_MESSAGE, null, null,
                "");
        if (!s.contains(" ") && s.length() > 0) {
            keepGoing = false;
            player = new Player(s, 10);
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Oops! Error in entering your player name. Unable to launch game.",
                    "Input error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: player
    // EFFECTS: loads player if it exists; error message otherwise
    private void loadPlayer() {
        try {
            JsonReader reader = new JsonReader("./data/player.json");
            player = reader.read();
            JOptionPane.showMessageDialog(null, player.getName() + " data loaded!");
            keepGoing = false;
        } catch (IOException exception) { //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            JOptionPane.showMessageDialog(null, "Oops! Error in loading player data.",
                    "Load error", JOptionPane.ERROR_MESSAGE);
        }
    }
}