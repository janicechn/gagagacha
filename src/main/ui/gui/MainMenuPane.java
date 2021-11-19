package ui.gui;

import persistence.JsonReader;
import persistence.JsonWriter;
import ui.AdviceMachine;
import ui.FortuneMachine;
import ui.MathQuestion;
import ui.MessageMachine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static ui.gui.StartScreen.player;

// Main menu components of each button/option the player has
public class MainMenuPane extends JPanel {
    DefaultListModel<String> model;

    // EFFECTS: Set layout and add buttons
    public MainMenuPane() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Create play button and play() on event of button
        JButton playButton = new JButton("Play gacha machine");
        add(playButton, gbc);
        playButton.setMnemonic(KeyEvent.VK_P);
        playButton.addActionListener(e -> play());
        // Create mini-game button and miniGame() on event of button
        JButton miniGameButton = new JButton("Play mini-game");
        add(miniGameButton, gbc);
        miniGameButton.setMnemonic(KeyEvent.VK_G);
        miniGameButton.addActionListener(e -> miniGame());
        // Create button to check coin balance
        JButton balanceButton = new JButton("Check coin balance");
        add(balanceButton, gbc);
        balanceButton.setMnemonic(KeyEvent.VK_B);
        balanceButton.addActionListener(e -> balance());
        notebook(gbc);
        save(gbc);
        load(gbc);
    }

    // EFFECTS: prompts player to choose mini-game to play and plays it
    private void miniGame() {
        Object[] options = { "Coin Toss", "Mad Math"};
        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose which mini-game you want to play!"));
        int result = JOptionPane.showOptionDialog(null, panel, "Mini-games!",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, null);
        if (result == JOptionPane.YES_OPTION) {
            coinToss();
        }
        if (result == JOptionPane.NO_OPTION) {
            madMath();
        }
    }

    // EFFECTS: shows math question and calls checkAnswer() to get player to answer
    private void madMath() {
        int number1;
        int number2;
        int mathAnswer;
        String question;
        Random rand = new Random();
        // https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
        String operation = MathQuestion.operations.get(rand.nextInt(MathQuestion.operations.size()));
        if (operation.equals("Addition") || operation.equals("Subtraction")) {
            number1 = (int)(Math.random() * 100) + 1; //https://www.javatpoint.com/java-math-random-method
            number2 = (int)(Math.random() * 100) + 1;
            if (operation.equals("Addition")) {
                mathAnswer = number1 + number2;
                question = "Solve: " + number1 + "+" + number2;
            } else {
                mathAnswer = number1 - number2;
                question = "Solve: " + number1 + "-" + number2;
            }
        } else {
            number1 = (int)(Math.random() * 10) + 1;
            number2 = (int)(Math.random() * 10) + 1;
            mathAnswer = number1 * number2;
            question = "Solve: " + number1 + "*" + number2;
        }

        checkAnswer(mathAnswer, question);
    }

    // MODIFIES: player
    // EFFECTS: prompts player to answer math question, showing message if incorrect/correct/invalid answer and adding
    //          4 coins to player's balance if answer is correct
    private void checkAnswer(int mathAnswer, String question) {
        String input = JOptionPane.showInputDialog(null,
                "Let's play Mad Math! Enter your answer in numbers:" + "\n\t" + question,
                "Mad Math!", JOptionPane.PLAIN_MESSAGE);
        try {
            if (input == null) {
                // does nothing (closes back to main menu) when cancel/exit is selected
            } else if ("".equals(input)) {
                JOptionPane.showMessageDialog(null,
                        "Sorry, your answer was invalid. Try again next time.");
            } else if (Integer.parseInt(input) == mathAnswer) {
                player.addBalance(4);
                JOptionPane.showMessageDialog(null,
                        "Congratulations! Your answer was correct! You won 4 coins!");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Sorry, your answer was wrong. Try again next time.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Sorry, your answer was invalid. Try again next time.");
        }
    }

    // EFFECTS: Prompts player to choose heads or tails, chooses heads or tails randomly, and calls win() or loss()
    //          based on coin toss result
    private void coinToss() {
        Object[] options = { "Heads", "Tails"};
        JPanel panel = new JPanel();
        panel.add(new JLabel("Let's play heads or tails! Choose:"));
        int result = JOptionPane.showOptionDialog(null, panel, "Coin Toss!",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

        Random rand = new Random();
        List<String> list = Arrays.asList("heads", "tails");
        // https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
        String tossResult = list.get(rand.nextInt(list.size()));

        if (result == JOptionPane.YES_OPTION) {
            if (tossResult.equals("heads")) {
                win();
            } else {
                lose();
            }
        }
        if (result == JOptionPane.NO_OPTION) {
            if (tossResult.equals("tails")) {
                win();
            } else {
                lose();
            }
        }
    }

    // EFFECTS: show message indicating player lost coin toss
    private void lose() {
        JOptionPane.showMessageDialog(null,
                "Sorry, your guess was incorrect; looks like luck isn't on your side this time. "
                        + "Try again next time.");
    }

    // MODIFIES: player
    // EFFECTS: add 3 coins to player's balance and show message indicating player won coin toss
    private void win() {
        player.addBalance(3);
        JOptionPane.showMessageDialog(null, "Coin is tossed!"
                + "\nCongratulations! You guessed correct! You won 3 coins!");
    }

    // EFFECTS: displays message of player's coin balance
    private void balance() {
        JOptionPane.showMessageDialog(null,
                "You have " + player.getBalance() + " coins."
                        + "\nPlay mini-games to earn more coins to play more gacha machines!");
    }

    // EFFECTS: create and add notebook button and viewNotebook() when acted on
    private void notebook(GridBagConstraints gbc) {
        JButton notebookButton = new JButton("View gacha notebook");
        add(notebookButton, gbc);
        notebookButton.setMnemonic(KeyEvent.VK_N);
        notebookButton.addActionListener(e -> viewNotebook());
    }

    // EFFECTS: saves current player data when acted on and shows dialog indicating player's data is saved; error
    //          dialog otherwise.
    private void save(GridBagConstraints gbc) {
        JButton saveButton = new JButton("Save current player data");
        add(saveButton, gbc);
        saveButton.setMnemonic(KeyEvent.VK_S);
        saveButton.addActionListener(e -> {
            JsonWriter jsonWriter = new JsonWriter("./data/player.json");
            try {
                jsonWriter.open();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Oops! Error in saving player data.",
                        "Save error", JOptionPane.ERROR_MESSAGE);
            }
            jsonWriter.write(player);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, player.getName() + " data saved!");
        });
    }

    // MODIFIES: player
    // EFFECTS: loads player data when acted on and shows dialog that player's data is loaded; error dialog otherwise
    private void load(GridBagConstraints gbc) {
        JButton loadButton = new JButton("Load player data");
        add(loadButton, gbc);
        loadButton.setMnemonic(KeyEvent.VK_S);
        loadButton.addActionListener(e -> {
            try {
                JsonReader reader = new JsonReader("./data/player.json");
                player = reader.read();
                JOptionPane.showMessageDialog(null, player.getName() + " data loaded!");
            } catch (IOException exception) { //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
                JOptionPane.showMessageDialog(null, "Oops! Error in loading player data.",
                        "Load error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // EFFECTS: prompts player to choose a machine to play
    private void play() {
        Object[] options = { "Advice (cost: " + AdviceMachine.COST_TO_PLAY + " coins)",
                "Fortune (cost: " + FortuneMachine.COST_TO_PLAY + " coins)",
                "Message (cost: " + MessageMachine.COST_TO_PLAY + " coins)"};
        JPanel panel = new JPanel();
        panel.add(new JLabel("Choose which gacha machine type you want to play!"));
        int result = JOptionPane.showOptionDialog(null, panel, "Gacha machines!",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, null);
        selectedMachine(result);
    }

    // EFFECTS: plays chosen machine if player has enough coins to play; shows error message if player has insufficient
    //          coins to play
    private void selectedMachine(int result) {
        if (result == JOptionPane.YES_OPTION) {
            if (player.getBalance() < AdviceMachine.COST_TO_PLAY) {
                insufficientCoins();
            } else {
                playMachine(AdviceMachine.advice, AdviceMachine.COST_TO_PLAY);
            }
        }
        if (result == JOptionPane.NO_OPTION) {
            if (player.getBalance() < FortuneMachine.COST_TO_PLAY) {
                insufficientCoins();
            } else {
                playMachine(FortuneMachine.fortune, FortuneMachine.COST_TO_PLAY);
            }
        }
        if (result == JOptionPane.CANCEL_OPTION) {
            if (player.getBalance() < AdviceMachine.COST_TO_PLAY) {
                insufficientCoins();
            } else {
                playMachine(MessageMachine.message, MessageMachine.COST_TO_PLAY);
            }
        }
    }

    // EFFECTS: displays message indicating player has insufficient coins to play a gacha machine
    private void insufficientCoins() {
        JOptionPane.showMessageDialog(null,
                "Oops! You don't have enough coin to play this gacha machine!"
                + "\nPlay mini-games to earn more coins to play!",
                "Insufficient coins.",
                JOptionPane.ERROR_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: gets random note based on machine and presents it, adding to player's notebook if it is not a repeated
    //          note from the message machine
    private void playMachine(List<String> notes, int cost) {
        player.removeBalance(cost);
        Random rand = new Random();
        String note = notes.get(rand.nextInt(notes.size()));
        ImageIcon icon = new ImageIcon("data/capsule.png"); //image made by me using Paint and Paint3D

        if (notes == MessageMachine.message && player.getNotebook().contains(note)) {
            JOptionPane.showMessageDialog(null,
                    "You won a message!\n\n\t" + "\"" + note + "\""
                            + "\n\nYou already collected this message, so it's not added to your notebook.",
                    "Gacha Capsule", JOptionPane.INFORMATION_MESSAGE, icon);
        } else {
            player.addNotebook(note);
            JOptionPane.showMessageDialog(null,
                    "You won a note!\n\n\t" + "\"" + note + "\""
                            + "\n\nYour note was added to your notebook.", "Gacha Capsule",
                    JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }

    // EFFECTS: if player's notebook is empty, error message shows; otherwise show notebook in a dialog pane with
    //          function buttons at the bottom to either delete a selected note, or empty notebook completely
    private void viewNotebook() {
        if (player.getNotebook().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No notes in your gacha notebook." + "\n\nStart playing to get notes!",
                    "Empty gacha notebook", JOptionPane.ERROR_MESSAGE);
        } else {
            model = new DefaultListModel<>();
            for (String s : player.getNotebook()) {
                model.addElement(s);
            }
            JList list = new JList(model);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setSelectedIndex(0);

            //JFrame frame = new JFrame("Gacha Notebook");
            NotebookDialog dialog = new NotebookDialog("Your gacha notebook!", list);
            dialog.setOnDelete(e -> deleteNote(list));
            dialog.show();
        }
    }

    // MODIFIES: player
    // EFFECTS: deletes selected note in player notebook and on dialog; error dialog shown if no note selected to delete
    //          and message dialog shown when no more notes left to delete
    private void deleteNote(JList list) {
        if (list.getSelectedIndex() != -1) {
            //remove note at the selected index in the list and player's data
            int index = list.getSelectedIndex();
            model.remove(index);
            player.removeNote(index);

            //removed item in last position if the index is at the last position
            if (index == model.getSize()) {
                index--;
            }

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        } else {
            JOptionPane.showMessageDialog(null, "Oops! No note selected to delete.",
                    "Delete note error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
