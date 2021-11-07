package ui.gui;

import javax.swing.*;
import java.awt.*;

// Adds all main menu components to panel on the game frame
public class MainMenu extends JPanel {

    // EFFECTS: sets main menu panel adding the 4 button options on
    public MainMenu() {
        setOpaque(false);
        setVisible(true);
        CardLayout cardLayout = new CardLayout();
        setLayout(cardLayout);

        MainMenuPane mainMenuPane = new MainMenuPane();
        add(mainMenuPane, "MainMenu");
        cardLayout.show(this, "MainMenu");
    }
}