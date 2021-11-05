package ui.gui;

import model.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class StartScreen {
    //https://stackoverflow.com/questions/22162398/how-to-set-a-background-picture-in-jpanel
    protected static Player player;
    private JFrame frame;

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

        instantiatePlayer(frame);
    }

    private void instantiatePlayer(JFrame frame) {
        // https://stackoverflow.com/questions/13334198/java-custom-buttons-in-showinputdialog
        Object[] options = { "Create player", "Load player data"};
        JPanel panel = new JPanel();
        panel.add(new JLabel("Welcome to GAGAGACHA!"));
        int result = JOptionPane.showOptionDialog(frame, panel, "Set player data",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, null);
        if (result == JOptionPane.YES_OPTION) {
            createPlayerPrompt(frame);
        } else {
            //Create a file chooser
        }
    }

    private void createPlayerPrompt(JFrame frame) {
        //continue to prompt for name to create player until told to stop (when keepGoing is false)
        boolean keepGoing = true;
        while (keepGoing) {
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