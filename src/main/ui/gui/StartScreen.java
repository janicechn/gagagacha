package ui.gui;

import model.Player;
import persistence.JsonReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class StartScreen {
    //https://stackoverflow.com/questions/22162398/how-to-set-a-background-picture-in-jpanel
    protected static Player player;
    private JFrame frame;
    Boolean keepGoing = true;

    public StartScreen() {
        Panel background = new Panel();

        frame = new JFrame("GAGAGACHA");
        //https://stackoverflow.com/questions/31526190/jframe-with-background-image-and-a-jpanel?noredirect=1&lq=1
        frame.setContentPane(background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.add(new Gui());
        // open in center of screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while (keepGoing) {
            instantiatePlayer(frame);
        }
    }

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
                try {
                    loadPlayer();
                } catch (IOException e) {
                    //dialog of error message when invalid file load
                    JOptionPane.showMessageDialog(frame, "Oops! Error in loading player data.",
                            "Load error", JOptionPane.ERROR_MESSAGE);
                    keepGoingHere = false;
                }
            } else {
                System.exit(2);
            }
        }
    }

    private void createPlayerPrompt(JFrame frame) {
        // https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
        String s = (String)JOptionPane.showInputDialog(frame,  "Enter your name:\n",
                "Create new player", JOptionPane.PLAIN_MESSAGE, null, null,
                "");
        //stop prompting for player name when a string is entered
        if ((s != null) && (s.length() > 0)) {
            keepGoing = false;
            player = new Player(s, 10);
        } else {
            //dialog of error message when invalid input/trying to not input a name (close/cancel)
            JOptionPane.showMessageDialog(frame,
                    "Oops! Error in entering your player name. Unable to launch game.",
                    "Input error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPlayer() throws IOException {
        JFileChooser fc = new JFileChooser();

        int returnValue = fc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            //https://mkyong.com/swing/java-swing-jfilechooser-example/
            selectedFile.getAbsolutePath();
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
        }
    }

    //set panel with background image
    public static class Panel extends JPanel {
        //https://stackoverflow.com/questions/31526190/jframe-with-background-image-and-a-jpanel?noredirect=1&lq=1
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
            //https://stackoverflow.com/questions/31526190/jframe-with-background-image-and-a-jpanel?noredirect=1&lq=1
            return image == null ? super.getPreferredSize() : new Dimension(image.getWidth(), image.getHeight());
        }

        //draw background image
        @Override
        public void paintComponent(Graphics g) {
            //https://stackoverflow.com/questions/31526190/jframe-with-background-image-and-a-jpanel?noredirect=1&lq=1
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
}