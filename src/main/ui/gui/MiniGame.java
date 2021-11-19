package ui.gui;

import ui.MathQuestion;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static ui.gui.StartScreen.player;

// Mini-game gui of all the mini-games the player can play and each game
public class MiniGame {

    // EFFECTS: prompts player to choose mini-game to play and plays it
    public MiniGame() {
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
}
