package ui;

import model.Player;

import java.util.*;

/*
 * MathQuestion is a class to play a mini-game where a random math question is prompted for the player to answer. If
 * the player guesses correctly, they earn coins.
 */
public class MathQuestion {
    private static final List<String> list;    // List of operations the math question can generate

    static {
        list = Collections.unmodifiableList(
                new ArrayList<String>() {{
                    add("Addition");
                    add("Subtraction");
                    add("Multiplication");
                }
                });
    }

    private int mathAnswer;                    // Answer to the math question

    // MODIFIES: player
    // EFFECTS: prints math question then prompts user for their answer input and checks that answer
    public MathQuestion(Player player) {
        Scanner input = new Scanner(System.in);
        String playerAnswer;  // force entry into loop

        System.out.println("Let's play Mad Math! Enter your answer in numbers!");
        System.out.println(question());
        System.out.println("Enter your answer:");
        playerAnswer = input.nextLine();
        int attempt = Integer.parseInt(playerAnswer);
        checkAnswer(attempt, player);
    }

    // EFFECTS: generates random operation and makes a math question based on the operation
    public String question() {
        Random rand = new Random();
        String operation = list.get(rand.nextInt(list.size()));

        if (operation.equals("Addition")) {
            return addition();
        } else if (operation.equals("Subtraction")) {
            return subtraction();
        } else {
            return multiplication();
        }
    }

    // MODIFIES: player
    // EFFECTS: if the player's input matches the answer, the player wins, otherwise lose
    public void checkAnswer(int attempt, Player player) {
        if (attempt == mathAnswer) {
            win(player);
        } else {
            lose();
        }
    }

    // MODIFIES: player
    // EFFECTS: adds winning coins to player's balance and prints winning message
    public void win(Player player) {
        player.addBalance(4);
        System.out.println("Congratulations! Your answer was correct! You won 4 coins!");
    }

    // EFFECTS: prints losing message
    public void lose() {
        System.out.println("Sorry, your answer was wrong. Try again next time.");
    }

    // EFFECTS: chooses two random numbers between 1 and 100, and the mathAnswer will be the sum.
    //          Returns the addition question of the numbers and operator as a string
    public String addition() {
        int number1 = (int)(Math.random() * 100) + 1;
        int number2 = (int)(Math.random() * 100) + 1;
        mathAnswer = number1 + number2;
        return "\tSolve: " + number1 + " + " + number2;
    }

    // EFFECTS: chooses two random numbers between 1 and 100, and the mathAnswer will be the difference.
    //          Returns the subtraction question of the numbers and operator as a string
    public String subtraction() {
        int number1 = (int)(Math.random() * 100) + 1;
        int number2 = (int)(Math.random() * 100) + 1;
        mathAnswer = number1 - number2;
        return "\tSolve: " + number1 + " - " + number2;
    }

    // EFFECTS: chooses two random numbers between 1 and 10, and the mathAnswer will be the product.
    //          Returns the multiplication question of the numbers and operator as a string
    public String multiplication() {
        int number1 = (int)(Math.random() * 10) + 1;
        int number2 = (int)(Math.random() * 10) + 1;
        mathAnswer = number1 * number2;
        return "\tSolve: " + number1 + " * " + number2;
    }
}