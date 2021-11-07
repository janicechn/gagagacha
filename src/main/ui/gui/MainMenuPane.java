package ui.gui;

import persistence.JsonReader;
import persistence.JsonWriter;
import ui.FortuneMachine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.List;
import java.util.Random;

import static ui.gui.StartScreen.player;

// Main menu components of each button/option the player has
public class MainMenuPane extends JPanel {
    DefaultListModel<String> model;

    // EFFECTS: Set layout and add buttons
    public MainMenuPane() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Create play button and play() on event of button
        JButton playButton = new JButton("Play gacha machine");
        add(playButton, gbc);
        playButton.setMnemonic(KeyEvent.VK_P);
        playButton.addActionListener(e -> play());
        notebook(gbc);
        save(gbc);
        load(gbc);
    }

    // EFFECTS: create and add notebook button and viewNotebook() when acted on
    private void notebook(GridBagConstraints gbc) {
        JButton notebookButton = new JButton("View gacha notebook");
        add(notebookButton, gbc);
        notebookButton.setMnemonic(KeyEvent.VK_N);
        notebookButton.addActionListener(e -> viewNotebook());
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

    // EFFECTS: shows dialog of random note generated from FortuneMachine and adds it to player's notebook
    private void play() {
        Random rand = new Random();
        List<String> fortunes = FortuneMachine.fortune;
        String message = fortunes.get(rand.nextInt(fortunes.size()));
        ImageIcon icon = new ImageIcon("data/capsule.png"); //image made by me using Paint and Paint3D

        player.addNotebook(message);
        JOptionPane.showMessageDialog(null,
                "You won a message!\n\n\t" + "\"" + message + "\""
                        + "\n\nYour message was added to your notebook.", "Gacha Capsule",
                JOptionPane.INFORMATION_MESSAGE, icon);
    }

    // EFFECTS: if player's notebook is empty, error message shows; otherwise show notebook in a dialog pane with
    //          function buttons at the bottom to either delete a selected note, or empty notebook completely
    private void viewNotebook() {
        if (player.getNotebook().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No notes in your gacha notebook." + "\n\nStart playing to get notes!",
                    "Empty gacha notebook", JOptionPane.ERROR_MESSAGE);
        } else {
            model = new DefaultListModel<>();
            for (String s : player.getNotebook()) {
                model.addElement(s);
            }
            JList list = new JList(model);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setSelectedIndex(0);

            //JFrame frame = new JFrame("Gacha Notebook");
            NotebookDialog dialog = new NotebookDialog("Your gacha notebook!", list);
            dialog.setOnDelete(e -> deleteNote(list));
            dialog.show();
        }
    }

    // MODIFIES: player
    // EFFECTS: deletes selected note in player notebook and on dialog; error dialog shown if no note selected to delete
    //          and message dialog shown when no more notes left to delete
    private void deleteNote(JList list) {
        if (list.getSelectedIndex() != -1) {
            //remove note at the selected index in the list and player's data
            int index = list.getSelectedIndex();
            model.remove(index);
            player.removeNote(index);

            //removed item in last position if the index is at the last position
            if (index == model.getSize()) {
                index--;
            }

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        } else {
            JOptionPane.showMessageDialog(null, "Oops! No note selected to delete.",
                    "Delete note error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
