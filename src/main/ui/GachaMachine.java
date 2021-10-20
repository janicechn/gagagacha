package ui;

import model.Player;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/*
 * GachaMachine is an abstract class that contains the general elements and messages of all gacha machines.
 * It prompts the user to play, indicating the price of the respective machine, and allows the player to play if
 * they have enough coins based on price while removing amount from the player's balance.
 * It generates a random note from the respective machine and adds the note into the player's notebook.
 */
public abstract class GachaMachine {

    // EFFECTS: prompts user to play a machine or go back to the main menu
    public GachaMachine(String gachaNote, Player player, int price, List<String> notes) {
        Scanner input = new Scanner(System.in);
        String command = "";

        welcomeMessage(gachaNote, price);
        while (!(command.equals("p") || command.equals("q"))) { //https://github.students.cs.ubc.ca/CPSC210/TellerApp
            System.out.println("\tTo play, press p");
            System.out.println("\tTo go back to the menu, press q");
            command = input.nextLine();
            command = command.toLowerCase(); //https://github.students.cs.ubc.ca/CPSC210/TellerApp

            if (command.equals("p")) { //https://github.students.cs.ubc.ca/CPSC210/TellerApp
                playMachine(player, price, notes);
                System.out.println("\nThank you for playing!");
            } else if (command.equals("q")) {
                System.out.println("Thank you, come back again!");
            } else {
                System.out.println("Oops! Invalid input, try again!");
            }
        }
    }

    // EFFECT: prints message of how much it costs to play the machine
    public void welcomeMessage(String message, int price) {
        System.out.println(message);
        System.out.println("To play this gacha machine, you need " + price + " coins.");
    }

    // MODIFIES: player
    /*
     * EFFECT: if player's balance has enough coins to pay, remove price to play machine from player's balance,
     *         then randomly prints note from the machine which is added into the player's notebook; otherwise print
     *         message indicating player has insufficient coins to play
     */
    private void playMachine(Player player, int price, List<String> list) {
        if (player.getBalance() >= price) {
            player.removeBalance(price);
            System.out.println("Spinning gacha machine...");
            System.out.println("A capsule came out! Read your note:");
            Random rand = new Random();
            // https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
            String note = list.get(rand.nextInt(list.size()));
            System.out.println("\n\t" + note);
            player.addNotebook(note);
        } else {
            System.out.println("Sorry, you don't have enough coins to play! Go earn some coins playing mini-games!");
        }
    }
}
