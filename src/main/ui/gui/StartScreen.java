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
    //https://stackoverflow.com/questions/31526190/jframe-with-background-image-and-a-jpanel?noredirect=1&lq=1
    //https://stackoverflow.com/questions/22162398/how-to-set-a-background-picture-in-jpanel

    static Player player;

    public StartScreen() {
        Panel background = new Panel();

        JFrame frame = new JFrame("GAGAGACHA");
        frame.setContentPane(background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.add(new Gui());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        boolean keepGoing = true;

        while (keepGoing) {
            // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
            String s = (String)JOptionPane.showInputDialog(frame,  "Enter your name:\n",
                    "Create new player", JOptionPane.PLAIN_MESSAGE, null, null, 
                    "");

            //If a string was returned, say so.
            //https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
            if ((s != null) && (s.length() > 0)) {
                keepGoing = false;
                player = new Player(s, 10);
            }
            else {
                JOptionPane.showMessageDialog(frame,
                        "Oops! Error in entering your player name. Unable to launch game.",
                        "Input error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static class Panel extends JPanel {

        BufferedImage image;

        public Panel() {
            setLayout(new BorderLayout());
            try {
                image = ImageIO.read(new URL("https://i.ibb.co/31dnR7d/gagagacha.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public Dimension getPreferredSize() {
            return image == null ? super.getPreferredSize() : new Dimension(image.getWidth(), image.getHeight());
        }

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

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            JButton playButton = new JButton("Play gacha machine");
            add(playButton, gbc);
            playButton.setMnemonic(KeyEvent.VK_P);
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
                    deleteButton.addActionListener(f -> {
                        //This method can be called only if there's a valid selection
                        //so go ahead and remove whatever's selected.
                        int index = list.getSelectedIndex();
                        model.remove(index);
                        player.removeNote(index);

                        if (model.getSize() == 0) { //Nobody's left, disable deleting.
                            deleteButton.setEnabled(false);
                            emptyButton.setEnabled(false);

                        } else { //Select an index.
                            if (index == model.getSize()) {
                                //removed item in last position
                                index--;
                            }

                            list.setSelectedIndex(index);
                            list.ensureIndexIsVisible(index);
                        }
                    });

                    emptyButton = new JButton("Empty notebook");
                    emptyButton.setActionCommand("Empty notebook");
                    emptyButton.addActionListener(g -> {
                        model = new DefaultListModel<>();
                        deleteButton.setEnabled(false);
                        emptyButton.setEnabled(false);
                        player.removeAllNotes();
                        list.updateUI();
                        frame.dispose();
                        JOptionPane.showMessageDialog(null, "Your gacha notebook has been cleared.");
                    });

                    JPanel buttonPane = new JPanel();
                    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
                    buttonPane.add(deleteButton);
                    buttonPane.add(Box.createHorizontalStrut(5));
                    buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
                    buttonPane.add(Box.createHorizontalStrut(5));
                    buttonPane.add(emptyButton);
                    buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

                    frame.setLayout(new BorderLayout());
                    frame.getContentPane().add(listScroller, BorderLayout.CENTER);
                    frame.getContentPane().add(buttonPane, BorderLayout.SOUTH);
                    frame.setSize(900, 500);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            });

            JButton saveButton = new JButton("Save current gacha notebook data");
            add(saveButton, gbc);
            saveButton.setMnemonic(KeyEvent.VK_S);
            saveButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, player.getName() + " data saved!");
            });

            JButton loadButton = new JButton("Load gacha notebook data");
            add(loadButton, gbc);
            loadButton.setMnemonic(KeyEvent.VK_L);
            loadButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(null, player.getName() + " data loaded!");
            });
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
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