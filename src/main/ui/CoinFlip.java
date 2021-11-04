package ui;

import model.Player;

import java.util.*;

/*
 * CoinFlip is a class to play a coin flip mini-game which allows a player to input their prediction, toss a random
 * coin, and gain coins if they win.
 */
public class CoinFlip {

    // MODIFIES: player
    // EFFECTS: asks player to choose heads or tails until they choose one and based on choice,
    //          determines if coin tossed result will match; if matched, player wins 3 coins, otherwise no coins
    public CoinFlip(Player player) {
        Scanner input = new Scanner(System.in);
        String selection = "";  //https://github.students.cs.ubc.ca/CPSC210/TellerApp

        System.out.println("Let's play heads or tails! Choose:");
        //https://github.students.cs.ubc.ca/CPSC210/TellerApp
        while (!(selection.equals("h") || selection.equals("t"))) {
            System.out.println("\th -> heads");
            System.out.println("\tt -> tails");
            selection = input.nextLine();
            selection = selection.toLowerCase(); //https://github.students.cs.ubc.ca/CPSC210/TellerApp

            if (selection.equals("h")) { //https://github.students.cs.ubc.ca/CPSC210/TellerApp
                betHeads(player);
            } else if (selection.equals("t")) {
                betTails(player);
            } else {
                System.out.println("Oops! Invalid input, try again!");
            }
        }
    }

    // MODIFIES: player
    // EFFECT: when the result of the coin toss is heads, the player wins, otherwise the player loses
    public void betHeads(Player player) {
        String result = toss();
        if (result.equals("heads")) {
            System.out.println("The coin landed on heads!");
            win(player);
        } else {
            System.out.println("The coin landed on tails!");
            lose();
        }
        System.out.println("\nThank you for playing!");
    }

    // MODIFIES: player
    // EFFECT: when the result of the coin toss is tails, the player wins, otherwise the player loses
    public void betTails(Player player) {
        String result = toss();
        if (result.equals("tails")) {
            System.out.println("The coin landed on tails!");
            win(player);
        } else {
            System.out.println("The coin landed on heads!");
            lose();
        }
    }

    // MODIFIES: player
    // EFFECTS: adds winning coins to player's balance and prints message for winning
    public void win(Player player) {
        player.addBalance(3);
        System.out.println("Congratulations! You won 3 coins!");
    }

    // EFFECTS: prints message for losing mini-game
    public void lose() {
        System.out.println("Sorry, looks like luck isn't on your side this time. Try again next time.");
    }

    // EFFECTS: tosses coin by randomly choosing between the two and returns the result of what is chosen
    public String toss() {
        Random rand = new Random();
        List<String> list = Arrays.asList("heads", "tails");
        System.out.println("Coin is tossed!");
        return list.get(rand.nextInt(list.size()));
    }
}
