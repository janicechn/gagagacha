package ui.gui;

import model.Player;
import ui.FortuneMachine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class StartScreen {
    static Player player;

    public StartScreen() {
        Panel background = new Panel();

        JFrame frame = new JFrame("GAGAGACHA");
        frame.setContentPane(background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.add(new Gui());
        // open in center of screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //continue to prompt for name to create player until told to stop (when keepGoing is false)
        boolean keepGoing = true;
        while (keepGoing) {
            String s = (String)JOptionPane.showInputDialog(frame,  "Enter your name:\n",
                    "Create new player", JOptionPane.PLAIN_MESSAGE, null, null,
                    "");
            //stop prompting for player name when a string is entered
            if ((s != null) && (s.length() > 0)) {
                keepGoing = false;
                player = new Player(s, 10);
            }
            //dialog of error message when invalid input/trying to not input a name (close/cancel)
            else {
                JOptionPane.showMessageDialog(frame,
                        "Oops! Error in entering your player name. Unable to launch game.",
                        "Input error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //set panel with background image
    public static class Panel extends JPanel {

        BufferedImage image;

        public Panel() {
            setLayout(new BorderLayout());
            try {
                //image drawn by me, uploaded using https://imgbb.com/
                image = ImageIO.read(new URL("https://i.ibb.co/31dnR7d/gagagacha.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //have the size fit the background image
        @Override
        public Dimension getPreferredSize() {
            return image == null ? super.getPreferredSize() : new Dimension(image.getWidth(), image.getHeight());
        }

        //draw background image
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }

    }

    public static class Gui extends JPanel {

        public Gui() {

            setOpaque(false);

            setVisible(true);
            CardLayout cardLayout = new CardLayout();
            setLayout(cardLayout);

            //add main menu (4 buttons) onto the frame
            MainMenuPane mainMenuPane = new MainMenuPane();
            add(mainMenuPane, "MainMenu");
            cardLayout.show(this, "MainMenu");
        }
    }

    public static class MainMenuPane extends JPanel {
        private JButton deleteButton;
        private JButton emptyButton;
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
            playButton.addActionListener(e -> {
                Random rand = new Random();
                List<String> fortunes = FortuneMachine.fortune;
                String message = fortunes.get(rand.nextInt(fortunes.size()));
                ImageIcon icon = createImageIcon("https://i.ibb.co/d5QJV8T/capsule.png");

                player.addNotebook(message);
                JOptionPane.showMessageDialog(null,
                        "You won a message!\n\n\t" + "\"" + message + "\""
                                + "\n\nYour message was added to your notebook.", "Gacha Capsule",
                        JOptionPane.INFORMATION_MESSAGE, icon);
            });

            JButton notebookButton = new JButton("View gacha notebook");
            add(notebookButton, gbc);
            notebookButton.setMnemonic(KeyEvent.VK_N);
            //if player's notebook is empty, error message shows when acting on the notebook button;
            //otherwise show notebook in a JList with a new frame and utilizing a scrolling pane;
            //includes the two function buttons at the bottom
            notebookButton.addActionListener(e -> {
                if (player.getNotebook().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "No notes in your gacha notebook."
                                    + "\n\nStart playing to get notes!",
                            "Empty gacha notebook", JOptionPane.ERROR_MESSAGE);
                } else {
                    model = new DefaultListModel<>();
                    for(String s : player.getNotebook()){
                        model.addElement(s);
                    }
                    JList list = new JList(model);
                    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    list.setLayoutOrientation(JList.VERTICAL);
                    list.setSelectedIndex(0);
                    list.setVisibleRowCount(5);

                    JScrollPane listScroller = new JScrollPane(list);
                    JFrame frame = new JFrame("Gacha Notebook");

                    deleteButton = new JButton("Delete note");
                    deleteButton.setActionCommand("Delete note");
                    //deleteButton that removes the selected note from the list and the player's data
                    deleteButton.addActionListener(f -> {
                        //remove note at the selected index in the list and player's data
                        int index = list.getSelectedIndex();
                        model.remove(index);
                        player.removeNote(index);

                        //disable delete and empty button when no notes left to possibly delete
                        if (model.getSize() == 0) {
                            deleteButton.setEnabled(false);
                            emptyButton.setEnabled(false);

                        } else {
                            //removed item in last position if the index is at the last position
                            if (index == model.getSize()) {
                                index--;
                            }

                            list.setSelectedIndex(index);
                            list.ensureIndexIsVisible(index);
                        }
                    });

                    emptyButton = new JButton("Empty notebook");
                    emptyButton.setActionCommand("Empty notebook");
                    //emptyButton removes all notes from player notebook data and resets list model;
                    //JList frame closes and message dialog appears
                    emptyButton.addActionListener(g -> {
                        model = new DefaultListModel<>();
                        player.removeAllNotes();
                        frame.dispose();
                        JOptionPane.showMessageDialog(null, "Your gacha notebook has been cleared.");
                    });

                    //create panel for buttons
                    JPanel buttonPane = new JPanel();
                    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
                    buttonPane.add(deleteButton);
                    buttonPane.add(Box.createHorizontalStrut(5));
                    buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
                    buttonPane.add(Box.createHorizontalStrut(5));
                    buttonPane.add(emptyButton);
                    buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

                    //add listScroller and buttonPane to the frame that comes up when view notebook button acted on
                    frame.setLayout(new BorderLayout());
                    frame.getContentPane().add(listScroller, BorderLayout.CENTER);
                    frame.getContentPane().add(buttonPane, BorderLayout.SOUTH);
                    frame.setSize(1200, 500);
                    frame.pack();
                    //open in center of screen
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            });

            JButton saveButton = new JButton("Save current gacha notebook data");
            add(saveButton, gbc);
            saveButton.setMnemonic(KeyEvent.VK_S);
            //button acted on will then show dialog saying player's data is saved
            saveButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, player.getName() + " data saved!");
            });

            //button acted on will then show dialog saying player's data is loaded
            JButton loadButton = new JButton("Load gacha notebook data");
            add(loadButton, gbc);
            loadButton.setMnemonic(KeyEvent.VK_L);
            loadButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, player.getName() + " data loaded!");
            });
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
}