package ui;

import model.*;

import java.util.Scanner;

/*
 * GachaApp is a class that initializes player and carries out the main overall commands and processes
 */
public class GachaApp {
    private Player player;
    private Scanner input;

    // EFFECTS: runs gacha application
    public GachaApp() {
        System.out.println("Welcome to GAGAGACHA!");
        System.out.println("\tYou start off with 10 coins, which you can use to play gacha machines and earn notes!"
                + "\n\tYou can play mini-games to earn more coins. Let's get playing!");
        runArcade();
    }

    // MODIFIES: this
    // EFFECTS: processes user input prompting the menu unless player quits game
    private void runArcade() {
        boolean keepGoing = true;
        String command;

        initialize();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                performCommand(command);
            }
        }

        System.out.println("\nThanks for playing! Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void performCommand(String command) {
        switch (command) {
            case "1":
                selectGacha();
                break;
            case "2":
                selectGame();
                break;
            case "3":
                System.out.println("You have " + player.getBalance() + " coins.");
                break;
            case "4":
                displayNotebook();
                break;
            default:
                System.out.println("Sorry! Selection not valid.");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes player and scanner
    private void initialize() {
        player = new Player(10);
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu options to user
    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("\t1 -> Play a gacha machine");
        System.out.println("\t2 -> Play a mini-game");
        System.out.println("\t3 -> Check coin balance");
        System.out.println("\t4 -> Check your gacha notebook");
        System.out.println("\tq -> Exit");
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select a gacha machine and plays it
    private void selectGacha() {
        String selection = "";  // force entry into loop

        while (!(selection.equals("1") || selection.equals("2") || selection.equals("3"))) {
            System.out.println("\nChoose your gacha machine!");
            System.out.println("\t1 -> Fortune");
            System.out.println("\t2 -> Message");
            System.out.println("\t3 -> Advice");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("1")) {
            new FortuneMachine(player);
        } else if (selection.equals("2")) {
            new MessageMachine(player);
        } else {
            new AdviceMachine(player);
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to select a mini-game and plays it
    private void selectGame() {
        String selection = "";  // force entry into loop

        while (!(selection.equals("1") || selection.equals("2"))) {
            System.out.println("\nWhich mini-game do you want to play?");
            System.out.println("\t1 -> Coin of truth");
            System.out.println("\t2 -> Mad Math");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("1")) {
            new CoinFlip(player);
        } else {
            new MathQuestion(player);
        }
    }

    // EFFECTS: gets player's notebook and displays all entries of notes in it, or message if player has no notes
    private void displayNotebook() {
        System.out.println("\nCheck out the notes in your gacha notebook!");

        if ((player.getNotebook().size()) == 0) {
            System.out.println("\tOops! You don't have any notes right now, play gacha machines to collect notes!");
        }

        for (int i = 0; i < player.getNotebook().size(); i++) {
            System.out.println("\t" + player.getNotebook().get(i));
        }
    }
}