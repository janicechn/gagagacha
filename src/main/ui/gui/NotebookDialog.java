package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.gui.StartScreen.player;

// Notebook view pane of notes and function buttons when viewing notebook
public class NotebookDialog {
    private JList list;
    private JOptionPane optionPane;
    private ActionListener deleteEvent;
    private JButton deleteButton;
    private JButton emptyButton;
    private JDialog dialog;

    // MODIFIES: this
    // EFFECTS: calls to show list dialog in plane
    public NotebookDialog(JList listToDisplay) {
        list = listToDisplay;
        createAndDisplayOptionPane();
    }

    // MODIFIES: this
    // EFFECTS: sets list to show in dialog and title of dialog
    public NotebookDialog(String title, JList listToDisplay) {
        this(listToDisplay);
        dialog.setTitle(title);
    }

    // EFFECTS: creates pane with function buttons
    private void createAndDisplayOptionPane() {
        setupButtons();
        JPanel pane = layoutComponents();
        optionPane = new JOptionPane(pane);
        optionPane.setOptions(new Object[]{deleteButton, emptyButton});
        dialog = optionPane.createDialog("Gacha notebook");
    }

    // EFFECTS: adds buttons and calls on action when acted on
    private void setupButtons() {
        deleteButton = new JButton("Delete note");
        deleteButton.setActionCommand("Delete note");
        deleteButton.addActionListener(this::handleDeleteButtonClick);
        
        emptyButton = new JButton("Empty notebook");
        emptyButton.setActionCommand("Empty notebook");
        // emptyButton removes all notes from player notebook data and resets list model;
        // JList frame closes and message dialog appears
        emptyButton.addActionListener(g -> {
            list = new JList();
            player.removeAllNotes();
            dialog.dispose();
            JOptionPane.showMessageDialog(null, "Your gacha notebook has been cleared.");
        });
    }

    // EFFECTS: removes the selected note from the list and the player's data
    private void handleDeleteButtonClick(ActionEvent e) {
        if (deleteEvent != null) {
            deleteEvent.actionPerformed(e);
        }
    }

    // EFFECTS: sets panel with the list
    private JPanel layoutComponents() {
        centerListElements();
        // https://stackoverflow.com/questions/37629017/java-dialog-with-jlist-and-option-buttons
        JPanel panel = new JPanel(new BorderLayout(5,5));
        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    // EFFECTS: layout for panel of notebook
    private void centerListElements() {
        // https://stackoverflow.com/questions/37629017/java-dialog-with-jlist-and-option-buttons
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // EFFECTS: shows notebook dialog pane
    public void show() {
        dialog.setVisible(true);
    }

    // EFFECTS: set deleteEvent for delete button
    public void setOnDelete(ActionListener event) {
        deleteEvent = event;
    }
}