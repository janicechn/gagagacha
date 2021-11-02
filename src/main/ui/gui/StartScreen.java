package ui.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class StartScreen {
    // https://stackoverflow.com/questions/31526190/jframe-with-background-image-and-a-jpanel?noredirect=1&lq=1
    //https://stackoverflow.com/questions/22162398/how-to-set-a-background-picture-in-jpanel

    public StartScreen() {
        Panel background = new Panel();

        JFrame frame = new JFrame("GAGAGACHA");
        frame.setContentPane(background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Gui());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
            add(new JButton("Play gacha machine"), gbc);
            add(new JButton("View gacha notebook"), gbc);
            add(new JButton("Save current gacha notebook data"), gbc);
            add(new JButton("Load gacha notebook data"), gbc);
        }
    }

}