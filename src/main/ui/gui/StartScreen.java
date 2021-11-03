package ui.gui;

import model.Player;
import ui.FortuneMachine;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class StartScreen {
    // https://stackoverflow.com/questions/31526190/jframe-with-background-image-and-a-jpanel?noredirect=1&lq=1
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

        private MainMenuPane mainMenuPane;
        private CardLayout cardLayout;

        public Gui() {

            setOpaque(false);

            setVisible(true);
            cardLayout = new CardLayout();
            setLayout(cardLayout);

            mainMenuPane = new MainMenuPane();

            add(mainMenuPane, "MainMenu");

            cardLayout.show(this, "MainMenu");

        }

    }

    public static class MainMenuPane extends JPanel {
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
                ImageIcon icon = createImageIcon("https://media.istockphoto.com/vectors/gachapon-vector-id"
                        + "1263011147?k=20&m=1263011147&s=612x612&w=0&h=8Tq8ZE761QkM-ns9jiNlBlVv2UZ4NwlDwMR" +
                        "Bu0lVmPA=");

                player.addNotebook(message);
                JOptionPane.showMessageDialog(null,
                        "You won a message!\n\t" + "\"" + message + "\""
                                + "\n\nYour message was added to your notebook.", "Gacha Capsule",
                        JOptionPane.INFORMATION_MESSAGE, icon);
            });

            JButton notebookButton = new JButton("View gacha notebook");
            add(notebookButton, gbc);
            notebookButton.setMnemonic(KeyEvent.VK_N);
            notebookButton.addActionListener(e -> {
                JList list = new JList(player.getNotebook().toArray(new String[0])); //data has type Object[]
                list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                list.setLayoutOrientation(JList.VERTICAL);
                list.setVisibleRowCount(-1);

                JScrollPane listScroller = new JScrollPane(list);
                listScroller.setPreferredSize(new Dimension(250, 80));
            });

            JButton saveButton = new JButton("Save current gacha notebook data");
            add(saveButton, gbc);
            saveButton.setMnemonic(KeyEvent.VK_S);

            JButton loadButton = new JButton("Load gacha notebook data");
            add(loadButton, gbc);
            loadButton.setMnemonic(KeyEvent.VK_L);
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