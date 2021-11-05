package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.gui.StartScreen.player;

//https://stackoverflow.com/questions/37629017/java-dialog-with-jlist-and-option-buttons
public class NotebookDialog {
    private JList list;
    private JOptionPane optionPane;
    private ActionListener deleteEvent;
    private JButton deleteButton;
    private JButton emptyButton;
    private JDialog dialog;

    public NotebookDialog(JList listToDisplay) {
        list = listToDisplay;
        createAndDisplayOptionPane();
    }

    public NotebookDialog(String title, JList listToDisplay) {
        this(listToDisplay);
        dialog.setTitle(title);
    }

    private void createAndDisplayOptionPane() {
        setupButtons();
        JPanel pane = layoutComponents();
        optionPane = new JOptionPane(pane);
        optionPane.setOptions(new Object[]{deleteButton, emptyButton});
        dialog = optionPane.createDialog("Gacha notebook");
    }

    private void setupButtons() {
        deleteButton = new JButton("Delete note");
        deleteButton.setActionCommand("Delete note");
        //deleteButton that removes the selected note from the list and the player's data
        deleteButton.addActionListener(this::handleDeleteButtonClick);
        
        emptyButton = new JButton("Empty notebook");
        emptyButton.setActionCommand("Empty notebook");
        //emptyButton removes all notes from player notebook data and resets list model;
        //JList frame closes and message dialog appears
        emptyButton.addActionListener(g -> {
            list = new JList();
            player.removeAllNotes();
            dialog.dispose();
            JOptionPane.showMessageDialog(null, "Your gacha notebook has been cleared.");
        });
    }

    private void handleDeleteButtonClick(ActionEvent e) {
        if (deleteEvent != null) {
            deleteEvent.actionPerformed(e);
        }
    }

    private JPanel layoutComponents() {
        centerListElements();
        JPanel panel = new JPanel(new BorderLayout(5,5));
        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    private void centerListElements() {
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void show() {
        dialog.setVisible(true);
    }

    public void setOnDelete(ActionListener event) {
        deleteEvent = event;
    }
}