package ui.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Panel for game frame
public class Panel extends JPanel {
    //https://stackoverflow.com/questions/31526190/jframe-with-background-image-and-a-jpanel?noredirect=1&lq=1
    BufferedImage image;

    // MODIFIES: this
    // EFFECTS: set panel with background image
    public Panel() {
        setLayout(new BorderLayout());
        try {
            image = ImageIO.read(new File("data/gagagacha.png")); //image made by me using paint
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: have the size fit the background image
    @Override
    public Dimension getPreferredSize() {
        //https://stackoverflow.com/questions/31526190/jframe-with-background-image-and-a-jpanel?noredirect=1&lq=1
        return image == null ? super.getPreferredSize() : new Dimension(image.getWidth(), image.getHeight());
    }

    // MODIFIES: this
    // EFFECTS: draw background image
    @Override
    public void paintComponent(Graphics g) {
        //https://stackoverflow.com/questions/31526190/jframe-with-background-image-and-a-jpanel?noredirect=1&lq=1
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}