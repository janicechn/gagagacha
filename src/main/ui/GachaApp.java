package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//GachaApp is a class that initializes player and carries out the main overall commands and processes including menu
public class GachaApp {
    //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private static final String JSON_STORE = "./data/player.json";
    private Player player;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // MODIFIES: this
    // EFFECTS: prints welcome message and prompts user to create or load a player before running application
    public GachaApp() {
        jsonWriter = new JsonWriter(JSON_STORE); // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        jsonReader = new JsonReader(JSON_STORE); // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

        String entry;
        input = new Scanner(System.in); // https://www.w3schools.com/java/java_user_input.asp

        boolean keepGoing = true; // https://github.students.cs.ubc.ca/CPSC210/TellerApp

        while (keepGoing) { // https://github.students.cs.ubc.ca/CPSC210/TellerApp
            System.out.println("Welcome to GAGAGACHA!");
            System.out.println("\t1 -> Create new player");
            System.out.println("\t2 -> Load player data");
            entry = input.next();
            entry = entry.toLowerCase(); // https://github.students.cs.ubc.ca/CPSC210/TellerApp

            if (entry.equals("1")) {
                create();
                keepGoing = false;
            } else if (entry.equals("2")) {
                load();
                runArcade();
                keepGoing = false;
            } else {
                System.out.println("Oops! Must create/load a player to play!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prefaces game and tutorial then asks player to create player name to run application
    private void create() {
        String inputName;
        System.out.println("\tNew players start off with 10 coins which can use to play gacha machines and "
                + "earn notes!" + "\n\tYou can play mini-games to earn more coins. Let's get playing!");
        System.out.println("\nEnter your player name!");
        inputName = input.next();
        inputName = inputName.toLowerCase();
        player = new Player(inputName, 10);
        runArcade();
    }

    // MODIFIES: this
    // EFFECTS: initializes player and processes user input prompting the menu unless player quits game
    private void runArcade() {
        boolean keepGoing = true; // https://github.students.cs.ubc.ca/CPSC210/TellerApp
        String command;

        while (keepGoing) { // https://github.students.cs.ubc.ca/CPSC210/TellerApp
            displayMenu();
            command = input.next();
            command = command.toLowerCase(); // https://github.students.cs.ubc.ca/CPSC210/TellerApp

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                performCommand(command);
            }
        }

        System.out.println("\nThanks for playing! Goodbye!");
        player.playerEventLog(EventLog.getInstance());
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void performCommand(String command) {
        //https://github.students.cs.ubc.ca/CPSC210/TellerApp
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
            case "s":
                save();
                break;
            case "l":
                load();
                break;
            default:
                System.out.println("Sorry! Selection not valid. Try again.");
                break;
        }
    }

    // EFFECTS: displays menu options to user
    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("\t1 -> Play a gacha machine");
        System.out.println("\t2 -> Play a mini-game");
        System.out.println("\t3 -> Check coin balance");
        System.out.println("\t4 -> Check your gacha notebook");
        System.out.println("\ts -> Save your player data to file (saves current data)");
        System.out.println("\tl -> Load your player data from file (replaces current data)");
        System.out.println("\tq -> Exit");
    }

    // EFFECTS: prompts user to select a gacha machine and plays it
    private void selectGacha() {
        String selection = "";
        //https://github.students.cs.ubc.ca/CPSC210/TellerApp
        while (!(selection.equals("1") || selection.equals("2") || selection.equals("3"))) {
            System.out.println("\nChoose your gacha machine!");
            System.out.println("\t1 -> Fortune");
            System.out.println("\t2 -> Message");
            System.out.println("\t3 -> Advice");
            selection = input.next();
            selection = selection.toLowerCase(); //https://github.students.cs.ubc.ca/CPSC210/TellerApp
        }

        if (selection.equals("1")) {
            new FortuneMachine(player);
        } else if (selection.equals("2")) {
            new MessageMachine(player);
        } else {
            new AdviceMachine(player);
        }
    }

    // EFFECTS: prompts user to select a mini-game and plays it
    private void selectGame() {
        String selection = "";
        //https://github.students.cs.ubc.ca/CPSC210/TellerApp
        while (!(selection.equals("1") || selection.equals("2"))) {
            System.out.println("\nWhich mini-game do you want to play?");
            System.out.println("\t1 -> Coin of truth");
            System.out.println("\t2 -> Mad Math");
            selection = input.next();
            selection = selection.toLowerCase(); //https://github.students.cs.ubc.ca/CPSC210/TellerApp
        }

        if (selection.equals("1")) {
            new CoinFlip(player);
        } else {
            new MathQuestion(player);
        }
    }

    // MODIFIES: player
    // EFFECTS: gets player's notebook and displays all entries of notes in it, or a message if player has no notes;
    //          prompts user to choose to remove a note, remove all notes, or return to menu
    private void displayNotebook() {
        System.out.println("\nCheck out the notes in your gacha notebook! "
                + "\nNote: any replicated notes from the message machine are not repeated!");

        if ((player.getNotebook().size()) == 0) {
            System.out.println("\tOops! You don't have any notes right now, play gacha machines to collect notes!");
        }

        for (int i = 0; i < player.getNotebook().size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + player.getNotebook().get(i));
        }

        String selection = ""; //https://github.students.cs.ubc.ca/CPSC210/TellerApp
        while (!(selection.equals("1") || selection.equals("2") || selection.equals("q"))) {
            System.out.println("\nOptions:" + "\n\t1 -> remove a note" + "\n\t2 -> remove all notes"
                    + "\n\tq -> back to main menu");
            selection = input.next();
            selection = selection.toLowerCase(); //https://github.students.cs.ubc.ca/CPSC210/TellerApp
        }

        if (selection.equals("1")) {
            removeInNotebook();
        } else if (selection.equals("2")) {
            player.removeAllNotes();
            System.out.println("Gacha notebook cleared.");
        } // returns to main menu (else)
    }

    // MODIFIES: player
    // EFFECTS: prompts user to enter note that they want to remove and removes it from player notebook if it is there
    private void removeInNotebook() {
        int selection;
        System.out.println("Enter the entry number of the note that you want to delete");
        selection = Integer.parseInt(input.next());

        if (selection < player.getNotebook().size() && selection > 0) {
            player.removeNote(selection - 1);
            System.out.println("Note " + selection + " has been removed.");
        } else {
            System.out.println("Invalid entry number.");
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the player (data) to file; catching FileNotFoundException if unable to write file
    private void save() {
        try {
            jsonWriter.open(); //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            jsonWriter.write(player);
            jsonWriter.close();
            System.out.println("Congrats! Saved " + player.getName() + "'s data to " + JSON_STORE);
        } catch (FileNotFoundException e) { //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            System.out.println("Sorry, failed to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads player (data) from file; catching IOException if unable to read file
    private void load() {
        try {
            player = jsonReader.read();
            System.out.println("Yay! Loaded " + player.getName() + "'s data from " + JSON_STORE);
        } catch (IOException e) { //https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
            System.out.println("Oh no! Failed to read data from file: " + JSON_STORE);
        }
    }
}