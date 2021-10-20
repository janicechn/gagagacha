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

    private int number1;
    private int number2;
    private int mathAnswer;                    // Answer to the math question

    // MODIFIES: player
    // EFFECTS: prints math question then prompts user for their answer input and checks that answer
    public MathQuestion(Player player) {
        Scanner input = new Scanner(System.in);
        String playerAnswer; //https://github.students.cs.ubc.ca/CPSC210/TellerApp

        System.out.println("Let's play Mad Math! Enter your answer in numbers!");
        question();
        System.out.println("Enter your answer:");
        playerAnswer = input.nextLine(); //https://github.students.cs.ubc.ca/CPSC210/TellerApp
        int attempt = Integer.parseInt(playerAnswer); // https://www.javatpoint.com/java-string-to-int
        checkAnswer(attempt, player);
    }

    // EFFECTS: generates random operation and makes a math question based on the operation
    public void question() {
        Random rand = new Random();
        //https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
        String operation = list.get(rand.nextInt(list.size()));

        if (operation.equals("Addition")) {
            addition();
        } else if (operation.equals("Subtraction")) {
            subtraction();
        } else {
            multiplication();
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
    public void addition() {
        number1 = (int)(Math.random() * 100) + 1; //https://www.javatpoint.com/java-math-random-method
        number2 = (int)(Math.random() * 100) + 1; //https://www.javatpoint.com/java-math-random-method
        mathAnswer = number1 + number2;
        System.out.println("\tSolve: " + number1 + " + " + number2);
    }

    // EFFECTS: chooses two random numbers between 1 and 100, and the mathAnswer will be the difference.
    //          Returns the subtraction question of the numbers and operator as a string
    public void subtraction() {
        number1 = (int)(Math.random() * 100) + 1; //https://www.javatpoint.com/java-math-random-method
        number2 = (int)(Math.random() * 100) + 1; //https://www.javatpoint.com/java-math-random-method
        mathAnswer = number1 - number2;
        System.out.println("\tSolve: " + number1 + " - " + number2);
    }

    // EFFECTS: chooses two random numbers between 1 and 10, and the mathAnswer will be the product.
    //          Returns the multiplication question of the numbers and operator as a string
    public void multiplication() {
        number1 = (int)(Math.random() * 10) + 1; //https://www.javatpoint.com/java-math-random-method
        number2 = (int)(Math.random() * 10) + 1; //https://www.javatpoint.com/java-math-random-method
        mathAnswer = number1 * number2;
        System.out.println("\tSolve: " + number1 + " * " + number2);
    }
}
