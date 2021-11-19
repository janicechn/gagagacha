package ui.gui;

import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;

import static ui.gui.StartScreen.player;

// Main menu components of each button/option the player has
public class MainMenuPane extends JPanel {

    // EFFECTS: Set layout and add buttons
    public MainMenuPane() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Create play button and play new gacha machine on event of button
        JButton playButton = new JButton("Play gacha machine");
        add(playButton, gbc);
        playButton.setMnemonic(KeyEvent.VK_P);
        playButton.addActionListener(e -> new PlayGachaMachine());

        // Create mini-game button and new mini-game on event of button
        JButton miniGameButton = new JButton("Play mini-game");
        add(miniGameButton, gbc);
        miniGameButton.setMnemonic(KeyEvent.VK_G);
        miniGameButton.addActionListener(e -> new MiniGame());

        balance(gbc);
        notebook(gbc);
        save(gbc);
        load(gbc);
    }

    private static void balanceMessage(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
                "You have " + player.getBalance() + " coins."
                        + "\nPlay mini-games to earn more coins to play more gacha machines!");
    }

    // EFFECTS: creates and adds balanceButton and shows message of player's coin balance when acted on
    private void balance(GridBagConstraints gbc) {
        JButton balanceButton = new JButton("Check coin balance");
        add(balanceButton, gbc);
        balanceButton.setMnemonic(KeyEvent.VK_B);
        balanceButton.addActionListener(MainMenuPane::balanceMessage);
    }

    // EFFECTS: create and add notebook button and viewNotebook() when acted on
    private void notebook(GridBagConstraints gbc) {
        JButton notebookButton = new JButton("View gacha notebook");
        add(notebookButton, gbc);
        notebookButton.setMnemonic(KeyEvent.VK_N);
        notebookButton.addActionListener(e -> new Notebook());
    }

    // EFFECTS: saves current player data when acted on and shows dialog indicating player's data is saved; error
    //          dialog otherwise.
    private void save(GridBagConstraints gbc) {
        JButton saveButton = new JButton("Save current player data");
        add(saveButton, gbc);
        saveButton.setMnemonic(KeyEvent.VK_S);
        saveButton.addActionListener(e -> {
            JsonWriter jsonWriter = new JsonWriter("./data/player.json");
            try {
                jsonWriter.open();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Oops! Error in saving player data.",
                        "Save error", JOptionPane.ERROR_MESSAGE);
            }
            jsonWriter.write(player);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, player.getName() + " data saved!");
        });
    }

    // MODIFIES: player
    // EFFECTS: loads player data when acted on and shows dialog that player's data is loaded; error dialog otherwise
    private void load(GridBagConstraints gbc) {
        JButton loadButton = new JButton("Load player data");
        add(loadButton, gbc);
        loadButton.setMnemonic(KeyEvent.VK_S);
        loadButton.addActionListener(e -> {
            try {
                JsonReader reader = new JsonReader("./data/player.json");
                player = reader.read();
                JOptionPane.showMessageDialog(null, player.getName() + " data loaded!");
            } catch (IOException exception) { //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
                JOptionPane.showMessageDialog(null, "Oops! Error in loading player data.",
                        "Load error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
