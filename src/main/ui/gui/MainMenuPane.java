package ui.gui;

import persistence.JsonReader;
import ui.FortuneMachine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import static ui.gui.StartScreen.player;

public class MainMenuPane extends JPanel {
    DefaultListModel<String> model;

    public MainMenuPane() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        //Add button for play, view, save, load with spaces between
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        JButton playButton = new JButton("Play gacha machine");
        add(playButton, gbc);
        playButton.setMnemonic(KeyEvent.VK_P);
        //play button shows dialog of random note generated from FortuneMachine and adds it to player's notebook
        playButton.addActionListener(e -> play());

        JButton notebookButton = new JButton("View gacha notebook");
        add(notebookButton, gbc);
        notebookButton.setMnemonic(KeyEvent.VK_N);
        //if player's notebook is empty, error message shows when acting on the notebook button;
        //otherwise show notebook in a JList with a new frame and utilizing a scrolling pane;
        //includes the two function buttons at the bottom
        notebookButton.addActionListener(e -> viewNotebook());

        JButton saveButton = new JButton("Save current player data");
        add(saveButton, gbc);
        saveButton.setMnemonic(KeyEvent.VK_S);
        //button acted on will then show dialog saying player's data is saved
        saveButton.addActionListener(e ->
                JOptionPane.showMessageDialog(null, player.getName() + " data saved!"));

        load(gbc);
    }

    private void load(GridBagConstraints gbc) {
        JButton loadButton = new JButton("Load player data");
        add(loadButton, gbc);
        loadButton.setMnemonic(KeyEvent.VK_S);
        //button acted on will then show dialog saying player's data is saved
        loadButton.addActionListener(e -> {
            try {
                loadPlayer();
            } catch (IOException f) {
                //dialog of error message when invalid file load
                JOptionPane.showMessageDialog(null, "Oops! Error in loading player data.",
                        "Load error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void loadPlayer() throws IOException {
        JFileChooser fc = new JFileChooser();

        int returnValue = fc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
        } else {
            throw new IOException();
        }

        String filename = fc.getSelectedFile().toString();
        //https://stackoverflow.com/questions/55568338/filechooser-how-to-show-an-error-message-while-saving-
        // json-file-as-png-or-any
        if (!filename.endsWith(".json")) {
            JOptionPane.showMessageDialog(null,
                    "Failed to load. You should load a file with a .json extension!");
        } else {
            JsonReader reader = new JsonReader(fc.getSelectedFile().toString());
            reader.read();
            JOptionPane.showMessageDialog(null, player.getName() + " data loaded!");
        }
    }

    private void play() {
        Random rand = new Random();
        List<String> fortunes = FortuneMachine.fortune;
        String message = fortunes.get(rand.nextInt(fortunes.size()));
        ImageIcon icon = createImageIcon("https://i.ibb.co/d5QJV8T/capsule.png");

        player.addNotebook(message);
        JOptionPane.showMessageDialog(null,
                "You won a message!\n\n\t" + "\"" + message + "\""
                        + "\n\nYour message was added to your notebook.", "Gacha Capsule",
                JOptionPane.INFORMATION_MESSAGE, icon);
    }

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

    private void deleteNote(JList list) {
        if (list.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Oops! No note selected to delete.",
                    "Delete note error", JOptionPane.ERROR_MESSAGE);
        } else {
            //remove note at the selected index in the list and player's data
            int index = list.getSelectedIndex();
            model.remove(index);
            player.removeNote(index);
            if (list.getSize().equals(0)) {
                JOptionPane.showMessageDialog(null, "Your gacha notebook is empty.");
            } else {
                //removed item in last position if the index is at the last position
                if (index == model.getSize()) {
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    // Returns ImageIcon or null (if path invalid)
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = null;
        try {
            imgURL = new URL(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void load(ActionEvent e) {
        try {
            loadPlayer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
